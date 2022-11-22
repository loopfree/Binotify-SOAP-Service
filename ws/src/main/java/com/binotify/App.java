package com.binotify;

import java.sql.Connection;
import java.sql.SQLException;

import javax.xml.ws.Endpoint;

import com.binotify.helpers.DBConnector;
import com.binotify.services.*;

/**
 * Hello world!
 *
 */
public class App 
{
    static final String host = "0.0.0.0";
    static final int port = 8042;

    public static void main( String[] args )
    {
        try(
            Connection conn = DBConnector.getConnection()
        ) {
            RequestReviewService.conn = conn;
            StatusCheckService.conn = conn;
            SubscriptionRequestService.conn = conn;

            String baseUrl = String.format("http://%s:%d/", App.host, App.port);

            Endpoint.publish(baseUrl + "admin", new RequestReviewService());
            System.out.println("Endpoint for admin started");

            Endpoint.publish(baseUrl + "check", new StatusCheckService());
            System.out.println("Endpoint for check started");

            Endpoint.publish(baseUrl + "request", new SubscriptionRequestService());
            System.out.println("Endpoint for request started");
            
            while(true) {
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
