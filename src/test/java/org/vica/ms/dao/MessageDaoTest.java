package org.vica.ms.dao;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.vica.ms.po.Message;

import java.util.List;

/**
 * Unit Test For MessageDao
 * Created by Vica-tony on 2016/8/21.
 */
public class MessageDaoTest {

    private MessageDao messageDao = new MessageDao();
    private Logger logger = Logger.getLogger(this.getClass());

    @Test
    public void insert() throws Exception {
//        int insert = messageDao.insert("新人签到","今天初次见面，多多关照",100000);
//        logger.info("insert : "+insert);
    }

    @Test
    public void selectById() throws Exception {
//        Message msg = messageDao.selectById(1000L,false);
//        logger.info(msg);
    }

    @Test
    public void selectByTitle() throws Exception {
//        List<Message> list = messageDao.select(null,0,12,true);
//        logger.info(list);
    }

}