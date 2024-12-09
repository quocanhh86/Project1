/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Repository.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Admin
 */
public class DatabaseUtils {
    private static final Connection connection = DbConnection.getConnection();
    private static final Logger LOGGER = Logger.getLogger("SQL Query Logger :");

    /**
     * @param tableName
     * @return
     * @throws SQLException
     */
    public static List<String> getColumns(String tableName) throws SQLException {
        int columnCount = 0;
        List<String> columns = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT COLUMN_NAME\n");
        queryBuilder.append("FROM INFORMATION_SCHEMA.COLUMNS\n");
        queryBuilder.append("WHERE table_name = ? AND ORDINAL_POSITION <> 1\n");

        PreparedStatement pstm = connection.prepareStatement(queryBuilder.toString());
        pstm.setString(1, tableName);
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            columnCount++;
            columns.add(rs.getString("COLUMN_NAME"));
        }
        if (columnCount != 0)
            return columns;
        throw new RuntimeException("Table not exist");
    }

    public static String getColumnString(String tableName) throws SQLException {
        List<String> columns = getColumns(tableName);
        StringBuilder columnString = new StringBuilder();
        columnString.append(" (");
        for (String column : columns) {
            columnString.append(column).append(", ");
        }
        columnString.deleteCharAt(columnString.lastIndexOf(","));
        columnString.append(")");
        return columnString.toString();
    }

    public static void printQueryLog(String query, String... params) {
        String log = "SQL Query : " + query + "\n";
        if (params.length > 0) {
            log += "Parameters : ";
            for (Object param : params) {
                log += param + ", ";
            }
            log = log.substring(0, log.lastIndexOf(","));
        }
        LOGGER.info(log);
    }
}
