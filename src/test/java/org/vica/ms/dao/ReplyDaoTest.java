package org.vica.ms.dao;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.vica.ms.po.Reply;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit Test For ReplyDao
 * Created by Vica-tony on 2016/8/21.
 */
public class ReplyDaoTest {

    private ReplyDao replyDao = new ReplyDao();
    private Logger logger = Logger.getLogger(this.getClass());

    @Test
    public void insert() throws Exception {
//        int insert = replyDao.insert("打扰了",100000,1000);
//        logger.info("Insert = "+insert);
    }

    @Test
    public void selectById() throws Exception {
//        Reply reply = replyDao.selectById(101,true);
//        logger.info(reply);
    }

    @Test
    public void selectByMsg() throws Exception {
//        List<Reply> list = replyDao.selectByMsg(1000,1,6,false);
//        logger.info(list);
    }

    @Test
    public void selectByUser() throws Exception {
//        List<Reply> list = replyDao.selectByUser(100000,1,6,true);
//        logger.info(list);
    }

}