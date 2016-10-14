package org.vica.ms.dao;

import org.vica.ms.po.Message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Data Access Object Of Message
 * Created by Vica-tony on 2016/8/21.
 */
public class MessageDao extends IBaseDao {

    private UserDao userDao = null;

    /**
     * Assemble The Message List From ResultSet
     *
     * @param set The ResultSet
     * @return The Message List
     * @throws SQLException exceptions
     */
    private List<Message> assemble(ResultSet set) throws SQLException {
        List<Message> users = new ArrayList<Message>();
        while (set.next()) {
            users.add(assembleSigle(set));
        }
        return users;
    }

    /**
     * Assemble The Message From ResultSet
     *
     * @param set The ResultSet
     * @return The Message, Maybe null
     * @throws SQLException exceptions
     */
    private Message assembleSigle(ResultSet set) throws SQLException {
        Message msg = new Message();
        msg.setUser_id(set.getLong("user_id"));
        msg.setContent(set.getString("content"));
        msg.setCreate_time(set.getString("create_time"));
        msg.setMsg_id(set.getLong("msg_id"));
        msg.setTitle(set.getString("title"));
        msg.setTopped(set.getString("topped"));
        msg.setReplyCount(selectReplyCountByMsgId(msg.getMsg_id()));
        if (userDao == null)
            userDao = new UserDao();
        msg.setOwner(userDao.selectByIdOrName(msg.getUser_id()));
        return msg;
    }

    /**
     * Insert A Message Into Message Table
     *
     * @param title   Title
     * @param content Content
     * @param userId  UserId
     * @return How many rows inserted
     */
    public int insert(String title, String content, long userId) {
        if (connection == null) {
            getConnection();
        }
        String sql = "insert into t_message(title,content,user_id) values(?,?,?);";
        try {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, title);
            statement.setObject(2, content);
            statement.setObject(3, userId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    /**
     * Select Message By Id
     *
     * @param id Message Id
     * @return Message
     */
    public Message selectById(long id) {
        if (connection == null) {
            getConnection();
        }
        String sql = "select * from t_message where msg_id=?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
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
     * Query The Specified Msg's Reply Count
     * @param id Msg Id
     * @return The Reply Count
     */
    public int selectReplyCountByMsgId(long id){
        if (connection == null) {
            getConnection();
        }
        String sql = "select count(r_id) from t_reply where msg_id=?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            ResultSet set = statement.executeQuery();
            if(!set.next())
                return 0;
            return set.getInt(1);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    /**
     *
     * Query The Count Of All Msg
     * @return
     */
    public int selectAllCount(){
        if (connection == null) {
            getConnection();
        }
        String sql = "select count(msg_id) from t_message;";
        try {
            statement = connection.prepareStatement(sql);
            ResultSet set = statement.executeQuery();
            if(!set.next())
                return 0;
            return set.getInt(1);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    /**
     * Select Message List
     *
     * @param key By The Title For String, By The UserId For Long Or Null To Select All
     * @return The List, Can be null
     */
    public List<Message> select(Object key, int page, int pageSize) {
        if (connection == null) {
            getConnection();
        }
        if(page<1)
            page =1;
        String sql = "select * from t_message ";
        if (null != key) {//MATCH(content) AGAINST('keyword');
            if (key instanceof String) {
//                sql += "where match(title) against(?) ";
                sql += "where title like ? ";
            } else {
                sql += "where user_id=? ";
            }
        }
        sql += " order by create_time desc limit ?,? ;";
        try {
            statement = connection.prepareStatement(sql);
            if (null == key) {
                statement.setObject(1, (page - 1) * pageSize);
                statement.setObject(2, pageSize);
            } else {
                if(key instanceof String )
                    statement.setObject(1, "%"+key+"%");
                else
                    statement.setObject(1, key);
                statement.setObject(2, ((page - 1) * pageSize));
                statement.setObject(3, pageSize);
            }
            ResultSet set = statement.executeQuery();
            if(set.wasNull())
                return null;
            return assemble(set);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * Delete By Id
     * @param id
     * @return
     */
    public int delete(long id){
        if (connection == null) {
            getConnection();
        }
        String sql = "delete from t_message where msg_id=?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }
}
