package model.dao.connection;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by tonchief on 05/23/2017.
 */
public class DBPool3 {
    private DataSource dataSource = getDataSource();




    private DataSource getDataSource() {
        if (dataSource == null) {
            ResourceBundle r = ResourceBundle.getBundle("database.connection");
            String host = r.getString("mysql.host");
            String user = r.getString("mysql.user");
            String password = r.getString("mysql.password");
            String port = r.getString("mysql.port");
            String schema = r.getString("mysql.schema");

            // Load mySQL driver, custom setup
            String url = "jdbc:mysql://" + host + ":" + port + "/" + schema
                    + "?autoReconnectForPools=true&useSSL=false&tcpKeepAlive=true";
            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);
            props.setProperty("defaultTransactionIsolation", "NONE");

            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            poolConfig.setMinIdle(5);
            poolConfig.setMaxTotal(10);

            /*ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(url, props);
            PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
            GenericObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory, poolConfig);
            poolableConnectionFactory.setPool(connectionPool);

            dataSource = new PoolingDataSource<>(connectionPool);

            LOG.info("Starting wakeup datasource with maxTotal={}", connectionPool.getMaxTotal());*/
        }

        return dataSource;
    }
}
