package com.binotify.services;

import java.time.Instant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.ws.developer.JAXWSProperties;

import javax.annotation.Resource;

import com.binotify.helpers.Logging;

@WebService
public class SubscriptionRequestService {

    @Resource
    WebServiceContext wsContext;

    public static Connection conn;
    
    @WebMethod
    public boolean requestSubscription(
        @WebParam(name = "subscriberId") Integer subscriberId,
        @WebParam(name = "creatorId") Integer creatorId
    ) {
        MessageContext msgContext = wsContext.getMessageContext();
        HttpExchange req = (HttpExchange) msgContext.get(JAXWSProperties.HTTP_EXCHANGE);

        String ip = String.format("%s", req.getRemoteAddress());
        String endpoint = String.format("%s", req.getRequestURI());
        Instant timestamp = Instant.now();

        /**
         * TODO:
         * handle logging id
         */
        Logging.log(SubscriptionRequestService.conn, 1, "Receive subscribe request", ip, endpoint, timestamp);

        return addSubscriptionPending(subscriberId, creatorId);
    }

    public boolean addSubscriptionPending(Integer subscriberId, Integer creatorId) {
        Connection conn = SubscriptionRequestService.conn;

        try(
            PreparedStatement stmtInsert = conn.prepareStatement("INSERT INTO Subscription VALUES ( ?, ?, 'PENDING' );")
        ) {
            stmtInsert.setInt(1, subscriberId);
            stmtInsert.setInt(2, creatorId);

            stmtInsert.executeQuery();
        } catch(SQLException e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }
}