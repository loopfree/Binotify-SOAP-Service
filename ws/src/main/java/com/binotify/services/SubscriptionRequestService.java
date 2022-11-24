package com.binotify.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import javax.xml.ws.WebServiceContext;

import javax.annotation.Resource;

import com.binotify.helpers.DBConnector;
import com.binotify.helpers.Logger;

@WebService
public class SubscriptionRequestService {
    @Resource
    WebServiceContext wsContext;
    
    @WebMethod
    public boolean requestSubscription(
        @WebParam(name = "subscriberId") Integer subscriberId,
        @WebParam(name = "creatorId") Integer creatorId
    ) {
        if (subscriberId == null || creatorId == null) {
            return false;
        }

        return addSubscriptionPending(subscriberId, creatorId);
    }

    public boolean addSubscriptionPending(Integer subscriberId, Integer creatorId) {
        String query = "INSERT INTO Subscription VALUES (?, ?, 'PENDING');";
        try (
            Connection conn = DBConnector.getConnection();
            PreparedStatement stmtInsert = conn.prepareStatement(query)
        ) {
            Logger.log(wsContext, "User requests for subscription");
            stmtInsert.setInt(1, subscriberId);
            stmtInsert.setInt(2, creatorId);
            stmtInsert.execute();

            return true;

        } catch(SQLException e) {
            e.printStackTrace();

            return false;
        }
    }
}