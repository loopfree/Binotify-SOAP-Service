package com.binotify.helpers;

import java.time.Instant;

import java.sql.Connection;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.ws.developer.JAXWSProperties;


public interface Logger {
    default void Log(WebServiceContext wsContext, Connection conn, String description) {
        MessageContext msgContext = wsContext.getMessageContext();
        HttpExchange req = (HttpExchange) msgContext.get(JAXWSProperties.HTTP_EXCHANGE);

        String ip = String.format("%s", req.getRemoteAddress().getHostName());
        System.out.println(ip);
        String endpoint = String.format("%s", req.getRequestURI());
        Instant timestamp = Instant.now();

        Logging.log(conn, description, ip, endpoint, timestamp);
    }
}