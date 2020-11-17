package com.mcspaskiy.service;

import com.mcspaskiy.model.DbConnectionParams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mcspaskiy.Constants.APPLICATION_PROPERTIES;

public class IOService {
    private static IOService instance;

    public static IOService getInstance() {
        if (instance == null) {
            instance = new IOService();
        }
        return instance;
    }

    public DbConnectionParams readDbConnectionParams() {
        Path path = Paths.get(APPLICATION_PROPERTIES);
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
            System.out.println("Connection params: " + "dbUrl = " + dbUrl + " dbUser = " + dbUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DbConnectionParams dbConnectionParams = new DbConnectionParams(dbUrl, dbUser, dbPass);
        return dbConnectionParams;
    }
}
