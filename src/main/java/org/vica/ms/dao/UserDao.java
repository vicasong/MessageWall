package org.vica.ms.dao;

import org.vica.ms.po.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Data Access Object Of User
 * Created by Vica-tony on 2016/8/21.
 */
public class UserDao extends IBaseDao{

    /**
     * Assemble The User List From ResultSet
     *
     * @param set The ResultSet
     * @return The User List
     * @throws SQLException exceptions
     */
    private List<User> assemble(ResultSet set) throws SQLException {
        List<User> users = new ArrayList<User>();
        while (set.next()) {
            users.add(assembleSigle(set));
        }
        return users;
    }

    /**
     * Assemble The User From ResultSet
     *
     * @param set The ResultSet
     * @return The User, Maybe null
     * @throws SQLException exceptions
     */
    private User assembleSigle(ResultSet set) throws SQLException {
        User user = new User();
        user.setLevel(set.getLong("level"));
        user.setNick_name(set.getString("nick_name"));
        user.setPassword(set.getString("password"));
        user.setUser_id(set.getLong("user_id"));
        user.setUser_name(set.getString("user_name"));
        return user;
    }

    /**
     * Insert A User Data Into User Table
     *
     * @param userName User Name
     * @param nickName Nick Name
     * @param password Password
     * @return How many rows be inserted
     */
    public int insert(String userName, String nickName, String password) {
        if (connection == null)
            getConnection();
        String sql = "insert ignore into t_user(user_name,nick_name,password) values(?,?,?);";
        try {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, userName);
            statement.setObject(2, nickName);
            statement.setObject(3, UserDao.securityString(password));
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    /**
     * Select User By UserId Or UserName
     *
     * @param key UserId For long, Or UserName For String
     * @return User, Can be null
     */
    public User selectByIdOrName(Object key) {
        if (connection == null)
            getConnection();
        String sql = null;
        if (key instanceof String) {
            sql = "select * from t_user where user_name=?;";
        } else {
            sql = "select * from t_user where user_id=?;";
        }
        try {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, key);
            ResultSet set = statement.executeQuery();
            if(!set.next())
                return null;
            return assembleSigle(set);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Generate A Security String For Password
     * @param pass Raw Password
     * @return Mixed Strings
     */
    public static String securityString(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte aB : b) {
                i = aB;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }
}
