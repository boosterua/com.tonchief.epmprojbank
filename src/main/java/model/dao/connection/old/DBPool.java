package model.dao.connection;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PoolableObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool.Config;
import org.apache.commons.pool2.impl.GenericObjectPoolFactory;

//import org.apache.commons.pool.ObjectPool;
//import org.apache.commons.pool.PoolableObjectFactory;
//import org.apache.commons.pool.impl.GenericObjectPool;
//import org.apache.commons.pool.impl.GenericObjectPool.Config;
//import org.apache.commons.pool.impl.GenericObjectPoolFactory;

import java.util.ResourceBundle;

public class DBPool {
    public static final ObjectPool pool = initMySqlConnectionPool();

    private static ObjectPool initMySqlConnectionPool() {
        ResourceBundle r = ResourceBundle.getBundle("database.connection");
        String host = r.getString("mysql.host");
        String user = r.getString("mysql.user");
        String password = r.getString("mysql.password");
        String port = r.getString("mysql.port");
        String schema = r.getString("mysql.schema");

        PoolableObjectFactory mySqlPoolableObjectFactory =
                new MySqlPoolableObjectFactory(host, Integer.parseInt(port), schema, user, password);
        Config config = new GenericObjectPool2.Config();
        config.maxActive = 10; //Integer.parseInt(r.getString("pool.maxActive")); //10
        config.testOnBorrow = true;
        config.testOnReturn = true;
        config.testWhileIdle = true;
        config.timeBetweenEvictionRunsMillis = 10000;
        config.minEvictableIdleTimeMillis = 60000;

        GenericObjectPoolFactory genericObjectPoolFactory =
                new GenericObjectPoolFactory(mySqlPoolableObjectFactory, config);
        ObjectPool pool = genericObjectPoolFactory.createPool();
        return pool;
    }
    /*

    public static void main(String[] args) throws SQLException, MySqlPoolException {
        ObjectPool pool;
        pool = initMySqlConnectionPool();
        ExampleClassUsesMySQLConnectionPool exampleClassUsesMySQLConnectionPool
                = new ExampleClassUsesMySQLConnectionPool(pool);
        for(String s: exampleClassUsesMySQLConnectionPool.getRecords("SELECT * from accounts"))
            System.out.println(s);
        System.out.println("HI");

    }*/
}