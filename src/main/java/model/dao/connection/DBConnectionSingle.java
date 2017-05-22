package model.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class DBConnectionSingle {


    public Statement getStatement() throws SQLException {
        return getConnection().createStatement();
    }

    public Connection getConnection() throws SQLException {
        ResourceBundle r = ResourceBundle.getBundle("database.connection");

        String url = r.getString("mysql.url");
        String user = r.getString("mysql.user");
        String password = r.getString("mysql.password");
        String driver = r.getString("mysql.driver");

        return DriverManager.getConnection(url, user, password);
    }

}
