package model.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class DBConnection {

    public Statement getStatement() throws SQLException {
        return getConnection().createStatement();
    }

    public Connection getConnection() throws SQLException {
        ResourceBundle r = ResourceBundle.getBundle("database.connection");
        String dbt = "mysql.";
        String url = r.getString(dbt + "url");
        //        String driver = r.getString(dbt + "driver");
        String user = r.getString(dbt + "user");
        String password = r.getString(dbt + "password");

        return DriverManager.getConnection(url, user, password);
    }

}
