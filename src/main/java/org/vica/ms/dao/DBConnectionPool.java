package org.vica.ms.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The Connection Pool
 * Created by Vica-tony on 2016/8/21.
 */
public class DBConnectionPool {

    private static ComboPooledDataSource dataSource = null;
    private static Logger logger = Logger.getLogger(DBConnectionPool.class);
    private static DBConnectionPool connectionPool = null;

    /**
     * Create DBConnectionPool, Synchronized
     * @param driver The Driver Class
     * @param url The Jdbc Url
     * @param user The Connection UserName
     * @param pass The Connection UserPassword
     */
    private DBConnectionPool(String driver, String url, String user, String pass) {
        if (dataSource == null)
            synchronized (DBConnectionPool.class) {
                if (dataSource == null) {
                    dataSource = new ComboPooledDataSource();
                    try {
                        dataSource.setDriverClass(driver);
                    } catch (PropertyVetoException e) {
                        logger.error(e.getMessage(), e);
                        logger.error("Unable to load DriverClass.");
                    }
                    // Set The Url, UserName And Password
                    dataSource.setJdbcUrl(url);
                    dataSource.setUser(user);
                    dataSource.setPassword(pass);

                    // Set Some Settings Of Connection Pool
                    dataSource.setMinPoolSize(5);
                    dataSource.setAcquireIncrement(10);
                    dataSource.setMaxPoolSize(120);
                    dataSource.setCheckoutTimeout(1200);
                    dataSource.setAcquireRetryAttempts(2);
                    dataSource.setAutoCommitOnClose(false);
                    dataSource.setMaxStatements(0);
                }
            }
    }

    /**
     * Get The Current DBConnectionPool Object
     * @return Required Object
     */
    public static DBConnectionPool getCurentPool() {
        if (dataSource == null) {
            Properties properties = new Properties();
            try {
                // load properties in classpath
                properties.load(DBConnectionPool.class.getResourceAsStream("/jdbc.properties"));
                // create connectionPool
                connectionPool = new DBConnectionPool(
                        properties.getProperty("jdbcDriver"),
                        properties.getProperty("jdbcUrl"),
                        properties.getProperty("connectionUser"),
                        properties.getProperty("userPassword"));
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                logger.error("Unable to load jdbc.properties, unable to load properties of jdbc.");
            }
        }
        return connectionPool;
    }

    /**
     * Get A Connection That Available
     * @return Connection Available, Maybe null when a exception arise
     */
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Close All Connections And Clear All Objects
     */
    public void closeAll(){
        if(dataSource!=null){
            dataSource.close();
            dataSource = null;
        }
        connectionPool = null;
        logger = null;
    }

}
