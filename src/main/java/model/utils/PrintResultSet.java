package model.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by tonchief on 05/22/2017.
 */
public class PrintResultSet {
    @Deprecated
    public static void printDump(ResultSet rs) throws SQLException {
        System.out.println(getDump(rs));
    }

    public static String getDump(ResultSet rs) throws SQLException {
        StringBuffer d = new StringBuffer();
        ResultSetMetaData rsmd = rs.getMetaData();
       d.append("Resulset dump:");
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                d.append(i + ") " + rsmd.getColumnName(i) + "=" + columnValue + "");
            }
            d.append("\n");
        }
        rs.beforeFirst();
        return d.toString();
    }
}
