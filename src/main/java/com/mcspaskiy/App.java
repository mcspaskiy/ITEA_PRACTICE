package com.mcspaskiy;

import com.mcspaskiy.model.AppKey;
import com.mcspaskiy.model.DbConnectionParams;
import com.mcspaskiy.service.DbService;
import com.mcspaskiy.service.IOService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        //Read new keys from program arguments
        List<AppKey> appKeys = readNewKeys(args);

        //Read connection params
        DbConnectionParams dbConnectionParams = IOService.getInstance().readDbConnectionParams();

        //Create DB connection with params
        Connection conn = DbService.getInstance().getConnection(dbConnectionParams);

        //Insert new keys into the DB
        DbService.getInstance().insertApiKeys(conn, appKeys);

        //Read all stored in DB keys
        appKeys = DbService.getInstance().receiveAppKeys(conn);

        //Show all records
        System.out.println("-----------------------------------------------------------");
        for (AppKey appKey : appKeys) {
            System.out.println("id=" + appKey.getId()
                    + " app_key=" + appKey.getKey()
                    + " created_date=" + appKey.getCreatedDate());
        }

        //Close DB connection
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static List<AppKey> readNewKeys(String[] args) {
        List<AppKey> appKeys = new ArrayList<>();
        for (String arg : args) {
            AppKey appKey = new AppKey(arg, LocalDateTime.now());
            appKeys.add(appKey);
        }
        return appKeys;
    }
}
