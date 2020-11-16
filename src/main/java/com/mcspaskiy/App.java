package com.mcspaskiy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) {
        File file = new File("props/application.properties");
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

        StringBuilder sb = new StringBuilder();
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
    }
}
