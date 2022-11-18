package com.binotify.helpers;

import java.time.Instant;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.PreparedStatement;

public class Logging {
    public static boolean log(Connection conn,Integer id, String description, 
                            String ip, String endpoint, Instant timestamp) {
        
        try(
            PreparedStatement stmtInsert = conn.prepareStatement("INSERT INTO Logging VALUES (?, ?, ?, ?, ?);");
        ) {
            stmtInsert.setInt(1, id);
            stmtInsert.setString(2, description);
            stmtInsert.setString(3, ip);
            stmtInsert.setString(4, endpoint);
            stmtInsert.setTimestamp(5, Timestamp.from(timestamp));
            
            stmtInsert.executeQuery();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}