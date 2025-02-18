package com.qa.sound.test;

import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnect {
    String url = "jdbc:sqlserver://192.168.0.64:1433;instanceName=MSSQL2019;databaseName=SIB_G8_SA";
    String username = "crmnext";
    String password = "abc123";

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    @Test
    public void getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            String query = "select * from Az_User";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int userID = resultSet.getInt("UserID");
                String loginID = resultSet.getString("LoginID");
                System.out.println(userID + ":" + loginID);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Step 6: Close the resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
