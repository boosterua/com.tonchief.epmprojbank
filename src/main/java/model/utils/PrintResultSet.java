package model.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by tonchief on 05/22/2017.
 */
public class PrintResultSet {
    public static void printDump(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        System.out.println("Resulset dump:");
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                System.out.print(i + ") " + rsmd.getColumnName(i) + "=" + columnValue + "");
            }
            System.out.println("");
        }
        rs.beforeFirst();
    }
}
