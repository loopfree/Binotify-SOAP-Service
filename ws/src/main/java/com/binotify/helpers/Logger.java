package com.binotify.helpers;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import java.sql.Connection;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.ws.developer.JAXWSProperties;


public class Logger {
    public static void log(WebServiceContext wsContext, String description) {
        MessageContext msgContext = wsContext.getMessageContext();
        HttpExchange req = (HttpExchange) msgContext.get(JAXWSProperties.HTTP_EXCHANGE);

        String ip = String.format("%s", req.getRemoteAddress());
        System.out.println(ip);
        String endpoint = String.format("%s", req.getRequestURI());
        Instant timestamp = Instant.now();

        String query = "INSERT INTO Logging (description, ip, endpoint, requested_at) VALUES (?, ?, ?, ?);";
        try (
            Connection conn = DBConnector.getConnection();
            PreparedStatement stmtInsert = conn.prepareStatement(query);
        ) {
            stmtInsert.setString(1, description);
            stmtInsert.setString(2, ip);
            stmtInsert.setString(3, endpoint);
            stmtInsert.setTimestamp(4, Timestamp.from(timestamp));
            stmtInsert.execute();

        } catch(SQLException e) {
            e.printStackTrace();
        }

    }
}