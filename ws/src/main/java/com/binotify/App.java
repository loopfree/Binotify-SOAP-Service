package com.binotify;

import javax.xml.ws.Endpoint;
import com.binotify.services.*;

public class App 
{
    static final String host = "0.0.0.0";
    static final int port = 8042;

    public static void main(String[] args) {
        String baseUrl = String.format("http://%s:%d/", App.host, App.port);

        Endpoint.publish(baseUrl + "admin", new RequestReviewService());
        System.out.println("Endpoint for admin started");

        Endpoint.publish(baseUrl + "check", new StatusCheckService());
        System.out.println("Endpoint for check started");

        Endpoint.publish(baseUrl + "request", new SubscriptionRequestService());
        System.out.println("Endpoint for request started");

        System.out.println("Server started on " + baseUrl);

        while(true);

    }
}
