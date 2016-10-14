package org.vica.ms.service;

import org.vica.ms.dao.UserDao;
import org.vica.ms.po.User;

/**
 * The Business Layer Of User
 * Created by Vica-tony on 8/29/2016.
 */
public class UserService {
    private UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    /**
     * Register A New User With UserName To Login And NickName To Show
     * @param userName The UserName(Must Unique)
     * @param nickName The NickName
     * @param password The Password
     * @return Whether The Operation Succeed
     */
    public boolean register(String userName, String nickName, String password) {
        return userDao.insert(userName, nickName, password) > 0;
    }

    /**
     * Login User With Specified UserName And Password
     * @param userName The UserName To Login
     * @param password The Password To Authentication
     * @return The User (Maybe Null For Error Login Info)
     */
    public User login(String userName, String password) {
        if (userName != null && userName.trim().length() > 0) {
            User find = userDao.selectByIdOrName(userName);
            if (find != null && find.getPassword().equals(UserDao.securityString(password))) {
                find.setPassword(null);
                return find;
            }
        }
        return null;
    }

    /**
     * ReLogin User Only By UserId
     * @param userId The UserId
     * @return The User (Maybe null)
     */
    public User relogin(long userId) {
        if (userId > 0) {
            User find = userDao.selectByIdOrName(userId);
            if (find != null) {
                find.setPassword(null);
                return find;
            }
        }
        return null;
    }
    @Override
    protected void finalize() throws Throwable {
        if(userDao!=null)
            userDao.close();
        super.finalize();
    }

}
