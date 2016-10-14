package org.vica.ms.dao;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vica.ms.po.User;

import static org.junit.Assert.*;

/**
 * The Unit Tes Of UserDao
 * Created by Vica-tony on 2016/8/21.
 */
public class UserDaoTest {

    private UserDao userDao;
    private Logger logger = Logger.getLogger(this.getClass());

    @Before
    public void init(){
        userDao = new UserDao();
    }

    @Test
    public void insert() throws Exception {
//        int rows = userDao.insert("vica","海蓝vica","123");
//        logger.info("Insert Rows : "+rows);
    }

    @Test
    public void selectByIdOrName() throws Exception {
        //User user = userDao.selectByIdOrName("vica");
//        User user = userDao.selectByIdOrName(100000);
//        logger.info("Result : "+user);
        /*
        User user = userDao.selectByIdOrName("vica");
        User{user_id=100000, user_name='vica', nick_name='海蓝vica', password='080ecf96c80e2d01a3004105e4f6b386', level=0}
         */
    }

    @After
    public void destory(){
        userDao = null;
    }

}