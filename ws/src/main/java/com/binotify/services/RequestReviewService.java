package com.binotify.services;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import javax.xml.ws.WebServiceContext;

import javax.annotation.Resource;

import com.binotify.helpers.DBConnector;
import com.binotify.helpers.Logger;
import com.binotify.model.Subscription;

@WebService
public class RequestReviewService {
    @Resource
    WebServiceContext wsContext;

    
    @WebMethod
    public Subscription[] getSubscriptionRequests() {
        String query = "SELECT * FROM Subscription WHERE status = 'PENDING';";
        try (
            Connection conn = DBConnector.getConnection();
            PreparedStatement pStatement = conn.prepareStatement(query)
        ) {
            Logger.log(wsContext, "Admin requests PENDING subscription");
            ArrayList<Subscription> subscriptionArrayList = new ArrayList<>();
            ResultSet resultSet = pStatement.executeQuery();

            while (resultSet.next()) {
                Subscription s = new Subscription();
                s.setCreatorId(resultSet.getInt("creator_id"));
                s.setSubscriberId(resultSet.getInt("subscriber_id"));
                s.setStatus(resultSet.getString("status"));
                
                subscriptionArrayList.add(s);
            }

            return subscriptionArrayList.toArray(new Subscription[0]);

        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public void approveSubscriptionRequest(
        @WebParam(name = "subscriberId") Integer subscriberId,
        @WebParam(name = "creatorId") Integer creatorId
    ) {
        String query = "UPDATE Subscription SET status = 'ACCEPTED' WHERE creator_id = ? AND subscriber_id = ?;";

        try (
            Connection conn = DBConnector.getConnection();
            PreparedStatement pStatement = conn.prepareStatement(query)
        ) {
            Logger.log(wsContext, "Admin approves subscription request");
            pStatement.setInt(1, creatorId);
            pStatement.setInt(2, subscriberId);
            pStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @WebMethod
    public void declineSubscriptionRequest(
        @WebParam(name = "subscriberId") Integer subscriberId,
        @WebParam(name = "creatorId") Integer creatorId
    ) {
        String query = "UPDATE Subscription SET status = 'REJECTED' WHERE creator_id = ? AND subscriber_id = ?;";
        try (
            Connection conn = DBConnector.getConnection();
            PreparedStatement pStatement = conn.prepareStatement(query)
        ) {
            Logger.log(wsContext, "Admin declined subscription request");
            pStatement.setInt(1, creatorId);
            pStatement.setInt(2, subscriberId);
            pStatement.execute();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}