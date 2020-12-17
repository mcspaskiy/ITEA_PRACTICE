package com.mcspaskiy.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * @author Mikhail Spaskiy
 */
public class DbService {
    public static final String SQL_INSERT_ROW = "INSERT INTO MOVEMENT_HISTORY(MOVEMENT, CREATED_DATE) VALUES(?,?)";

    private static DbService instance;

    private DbService() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
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

    public void saveHistoryMovement(Connection conn, String movement) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT_ROW);
                preparedStatement.setString(1, movement);
                preparedStatement.setObject(2, LocalDateTime.now());
                preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
