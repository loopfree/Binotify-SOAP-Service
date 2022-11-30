package com.binotify.helpers;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector {
    private static final BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:mysql://db-catify-soap:3306/catifysoap");
        ds.setUsername("root");
        ds.setPassword("admin");
    }

    public DBConnector() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}