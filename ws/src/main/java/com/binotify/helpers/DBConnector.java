package com.binotify.helpers;

import java.time.Instant;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector {
    private String url;
    private String user;
    private String password;
    private Connection conn;

    public DBConnector(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public boolean startConnection() {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean closeConnection() {
        try {
            conn.close();
        } catch(SQLException e) {
            return false;
        }

        return true;
    }
}