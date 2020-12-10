package com.mcspaskiy;

public class Constants {
    public static final String APPLICATION_PROPERTIES = "application.properties";
    //ublic static final String SQL_INSERT_ROW = "INSERT INTO APP_KEYS(APP_KEY, CREATED_DATE) VALUES(?,?)";
    //public static final String SQL_INSERT_ROW = "INSERT INTO APP_KEYS(APP_KEY, CREATED_DATE) VALUES(?,?)";
    public static final String SQL_CHECK_CREDENTIALS = "SELECT * FROM USERS WHERE USER_NAME = ? AND USER_PASS = ?";
}