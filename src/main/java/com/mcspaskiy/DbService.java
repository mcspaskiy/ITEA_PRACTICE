package com.mcspaskiy;

import java.sql.*;

import static com.mcspaskiy.Constants.SQL_CHECK_CREDENTIALS;

public class DbService {
    private static DbService instance;

    private DbService() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Driver loaded");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DbService getInstance() {
        if (instance == null) {
            instance = new DbService();
        }
        return instance;
    }

    public Connection getConnection(DbConnectionParams dbConnectionParams) {
        Connection conn = null;
        try {
            StringBuilder connectionString = new StringBuilder();
            connectionString.append(dbConnectionParams.getUrl())
                    .append("?user=")
                    .append(dbConnectionParams.getUser())
                    .append("&password=")
                    .append(dbConnectionParams.getPassword());
            conn = DriverManager.getConnection(connectionString.toString());
            System.out.println("Connection completed");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return conn;
    }

    public boolean checkCredentials(Connection conn, String name, String pass) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean result = false;
        try {
            statement = conn.prepareStatement(SQL_CHECK_CREDENTIALS);
            statement.setString(1, name);
            statement.setString(2, pass);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = true;
            } else {
                result = false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                resultSet.close();
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
