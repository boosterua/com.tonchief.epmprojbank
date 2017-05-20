package model.connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import jdbc.core.Query;


/**
 * Created by tonchief on 05/20/2017.
 */
public class Rs {
    public static void main(String[] args) throws SQLException {
        Statement st = new Statements().getStatement();
        ResultSet rs = st.executeQuery("SELECT * from accounts");
        if (rs != null) {
            while (rs.next()) {
                System.out.println("id_Account=" + rs.getString(1) +
                        "; number=" + rs.getString(2) + ";");
            }
        }
        rs.close();
        st.close();
    }
}
