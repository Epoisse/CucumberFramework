package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {

    /**
     * This method will connect to DB, execute query and retrieve data in a form of List<Map<String, String>>
     *
     * @param query
     */

    public static List<Map<String, String>> getDataFromDB(String query) {

        String dbUrl = ConfigReader.getPropertyValue("dbUrl");
        String dbUserName = ConfigReader.getPropertyValue("dbUserName");
        String dbPassword = ConfigReader.getPropertyValue("dbPassword");


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;

        List<Map<String, String>> tableData = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            resultSetMetaData = resultSet.getMetaData();

            while (resultSet.next()) {
                Map<String, String> row = new HashMap<>();
                for (int i = 1; i < resultSetMetaData.getColumnCount(); i++) {
                    String columnName = resultSetMetaData.getColumnName(i);
                    String columnValue = resultSet.getString(columnName);
                    row.put(columnName, columnValue);
                }
                tableData.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return tableData;
    }

    /**
     * This method will close the connection with DB
     *
     * @param connection
     * @param statement
     * @param resultSet
     */

    public static void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
