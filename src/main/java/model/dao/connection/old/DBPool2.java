package model.dao.connection.old;

import org.apache.log4j.Logger;
import snaq.db.ConnectionPool;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBPool2 {
    private static final Logger logger = Logger.getLogger(DBPool2.class);
    public static final ConnectionPool pool = staticPool();

    private static synchronized ConnectionPool initMySqlConnectionPool() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        try {
            ResourceBundle r = ResourceBundle.getBundle("database.connection");

            String host = r.getString("mysql.host");
            String user = r.getString("mysql.user");
            String password = r.getString("mysql.password");
            String port = r.getString("mysql.port");
            String schema = r.getString("mysql.schema");

            // Load mySQL driver, custom setup
            Class c = Class.forName("com.mysql.jdbc.Driver");
            Driver driver = (Driver) c.newInstance();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://" + host + ":" + port + "/" + schema
                    + "?autoReconnectForPools=true&useSSL=false";

            // (poolname,minpool,maxpool,maxsize,idleTimeout,url,properties);
            ConnectionPool pool = new ConnectionPool("local", 5, 10, 30,
                    180, url, user, password);
        } catch(Exception e){
            logger.error(e);
        }
        return pool;
    }

    private static ConnectionPool staticPool(){
        ConnectionPool pool = null;
        try{
            pool = initMySqlConnectionPool();
        } catch (Exception e) {
            logger.error(e);
        }
        return pool;
    }

    public void returnObject(){

    }
}