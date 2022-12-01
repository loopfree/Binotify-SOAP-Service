package com.binotify.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.jws.HandlerChain;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import javax.xml.ws.WebServiceContext;

import javax.annotation.Resource;

import com.binotify.helpers.DBConnector;
import com.binotify.helpers.Logger;

@WebService
@HandlerChain(file="handler-chain.xml")
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
        Logger.log(wsContext, "User requests for subscription");

        return addSubscriptionPending(subscriberId, creatorId);
    }

    public boolean addSubscriptionPending(Integer subscriberId, Integer creatorId) {
        boolean colExist = checkColumnExist(subscriberId, creatorId);

        if(colExist) {
            String query = "UPDATE subscription SET status = 'PENDING', is_polled = 0 WHERE creator_id = ? AND subscriber_id = ?;";
            try (
                Connection conn = DBConnector.getConnection();
                PreparedStatement stmtInsert = conn.prepareStatement(query)
            ) {
                stmtInsert.setInt(1, creatorId);
                stmtInsert.setInt(2, subscriberId);
                stmtInsert.execute();

                return true;

            } catch(SQLException e) {
                e.printStackTrace();

                return false;
            }
        } else {
            String query = "INSERT INTO subscription VALUES (?, ?, 'PENDING', 0);";
            try (
                Connection conn = DBConnector.getConnection();
                PreparedStatement stmtInsert = conn.prepareStatement(query)
            ) {
                stmtInsert.setInt(1, creatorId);
                stmtInsert.setInt(2, subscriberId);
                stmtInsert.execute();

                return true;

            } catch(SQLException e) {
                e.printStackTrace();

                return false;
            }
        }

    }

    public boolean checkColumnExist(Integer subscriberId, Integer creatorId) {
        boolean exists = false;
        String query = "SELECT status FROM subscription WHERE creator_id = ? AND subscriber_id = ?;";
        try(
            Connection conn = DBConnector.getConnection();
            PreparedStatement pStatement = conn.prepareStatement(query)
        ) {
            pStatement.setInt(1, creatorId);
            pStatement.setInt(2, subscriberId);
            ResultSet rs = pStatement.executeQuery();

            if(rs.next()) {
                exists = true;
            }
        } catch(SQLException e) {
            e.printStackTrace();

            exists =  false;
        }

        return exists;
    }
}