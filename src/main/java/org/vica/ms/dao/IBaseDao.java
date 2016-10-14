package org.vica.ms.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * The Base Dao Of Any Dao
 * Created by Vica-tony on 2016/8/21.
 */
public class IBaseDao {

    protected Connection connection = null;
    protected PreparedStatement statement = null;
    protected static Logger logger = Logger.getLogger(UserDao.class);

    protected void getConnection() {
        connection = DBConnectionPool.getCurentPool().getConnection();
    }

    public void close(){
        try {
            if (connection != null) {
                connection.close();
            }
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        statement = null;
        logger = null;
    }

}
