package com.binotify.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import javax.xml.ws.WebServiceContext;

import javax.annotation.Resource;

import com.binotify.helpers.Logger;

@WebService
public class SubscriptionRequestService implements Logger{

    @Resource
    WebServiceContext wsContext;

    public static Connection conn;
    
    @WebMethod
    public boolean requestSubscription(
        @WebParam(name = "subscriberId") Integer subscriberId,
        @WebParam(name = "creatorId") Integer creatorId
    ) {

        Log(wsContext, conn, "User requests for subscription");

        if(subscriberId == null || creatorId == null) {
            return false;
        }

        return addSubscriptionPending(subscriberId, creatorId);
    }

    public boolean addSubscriptionPending(Integer subscriberId, Integer creatorId) {
        Connection conn = SubscriptionRequestService.conn;

        boolean result;

        try(
            PreparedStatement stmtInsert = conn.prepareStatement("INSERT INTO Subscription VALUES ( ?, ?, 'PENDING' );")
        ) {
            stmtInsert.setInt(1, subscriberId);
            stmtInsert.setInt(2, creatorId);

            stmtInsert.execute();
        } catch(SQLException e) {
            e.printStackTrace();

            result = false;
        }

        result = true;

        return result;
    }
}