package com.mcspaskiy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    public static final String SQL_INSERT_ROW = "INSERT INTO APP_KEYS(APP_KEY, CREATED_DATE) VALUES(?,?)";
    public static final String SQL_SELECT_ALL = "SELECT * FROM APP_KEYS";


    public static void main(String[] args) {
        for (String arg : args) {
            System.out.println("Key=" + arg);
        }
        //  File file = new File("props/application.properties");
        Path path = Paths.get("props/application.properties");
        String dbUrl = null;
        String dbUser = null;
        String dbPass = null;

        try {
            Stream<String> lines = Files.lines(path);
            String appProperties = lines.collect(Collectors.joining("\n"));
            lines.close();
            String[] values = appProperties.split("\n");
            dbUrl = values[0].replace("db.url=", "");
            dbUser = values[1].replace("db.user=", "");
            dbPass = values[2].replace("db.password=", "");
            System.out.println("dbUrl = " + dbUrl + " dbUser = " + dbUser);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //   StringBuilder sb = new StringBuilder();
        /*  try (InputStream inputStream = new FileInputStream(file);) {

         *//* int i;
            while ((i = inputStream.read()) != -1 ) {
                sb.append((char)i);
            }*//*
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Driver loaded");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Connection conn = null;
        try {
            StringBuilder connectionString = new StringBuilder();
            connectionString.append(dbUrl)
                    .append("?user=")
                    .append(dbUser)
                    .append("&password=")
                    .append(dbPass);
            conn = DriverManager.getConnection(connectionString.toString());
          /*  conn =
                    DriverManager.getConnection("jdbc:mysql://localhost/itea?" +
                            "user=m.spaskiy&password=obivan");*/
            System.out.println("Connection completed");
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        try {
            Statement statement = conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT_ROW);
            preparedStatement.setString(1, "aaaaaa");
            preparedStatement.setObject(2, LocalDateTime.now());
            preparedStatement.execute();
            preparedStatement.close();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                System.out.println("id=" + resultSet.getInt("id") + " app_key=" + resultSet.getString("app_key") + " created_date=" + resultSet.getObject("created_date"));
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void insertKey(String key, LocalDateTime dateTime) {

    }
}
