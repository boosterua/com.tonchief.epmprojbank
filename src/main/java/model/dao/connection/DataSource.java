package model.dao.connection;

import org.apache.commons.dbcp2.BasicDataSource;
import java.util.ResourceBundle;

/**
 * @author ashraf
 * got it here: https://examples.javacodegeeks.com/core-java/apache/commons/org-apache-commons-dbcp2-basicdatasource-example/
 */
public class DataSource {

    private static final int CONN_POOL_SIZE = 10;
    public static final DataSource Inst = getInstance();

    private BasicDataSource bds = new BasicDataSource();

    private DataSource() {
        ResourceBundle r = ResourceBundle.getBundle("database.connection");
        String user = r.getString("mysql.user");
        String password = r.getString("mysql.password");
        String url = r.getString("mysql.url");
        String driver = r.getString("mysql.driver4commons");

        bds.setDriverClassName(driver);
        bds.setUrl(url);
        bds.setUsername(user);
        bds.setPassword(password);
        bds.setInitialSize(CONN_POOL_SIZE);
    }

    private static class DataSourceHolder {
        private static final DataSource INSTANCE = new DataSource();
    }

    public static DataSource getInstance() {
        return DataSourceHolder.INSTANCE;
    }


    public BasicDataSource getBds() {
        return bds;
    }

    public void setBds(BasicDataSource bds) {
        this.bds = bds;
    }
}