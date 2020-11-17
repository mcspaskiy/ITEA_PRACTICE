package com.mcspaskiy.service;

import com.mcspaskiy.model.AppKey;
import com.mcspaskiy.model.DbConnectionParams;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.mcspaskiy.Constants.SQL_INSERT_ROW;
import static com.mcspaskiy.Constants.SQL_SELECT_ALL;

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

    public List<AppKey> receiveAppKeys(Connection conn) {
        List<AppKey> result = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                AppKey appKey = new AppKey();
                appKey.setId(resultSet.getInt("id"));
                appKey.setKey(resultSet.getString("app_key"));
                appKey.setCreatedDate(((Timestamp) resultSet.getObject("created_date")).toLocalDateTime());
                result.add(appKey);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void insertApiKeys(Connection conn, List<AppKey> appKeys) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT_ROW);
            for (AppKey appKey : appKeys) {
                preparedStatement.setString(1, appKey.getKey());
                preparedStatement.setObject(2, appKey.getCreatedDate());
                preparedStatement.execute();
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
