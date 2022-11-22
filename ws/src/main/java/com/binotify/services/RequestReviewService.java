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

import com.binotify.helpers.Logger;
import com.binotify.model.Subscription;

@WebService
public class RequestReviewService implements Logger{
    @Resource
    WebServiceContext wsContext;
    
    public static Connection conn;
    
    @WebMethod
    public Subscription[] getSubscriptionRequests() {
        Connection conn = RequestReviewService.conn;
        String query = "SELECT * FROM Subscription WHERE status = 'PENDING';";

        ArrayList<Subscription> result = new ArrayList<>();

        try(
            PreparedStatement pStatement = conn.prepareStatement(query)
        ) {
            ArrayList<Subscription> temp = new ArrayList<>();
            
            ResultSet resultSet = pStatement.executeQuery();

            while(resultSet.next()) {
                Subscription s = new Subscription();
                s.setCreatorId(resultSet.getInt("creator_id"));
                s.setSubscriberId(resultSet.getInt("subscriber_id"));
                s.setStatus(resultSet.getString("status"));
                
                temp.add(s);
            }

            result = temp;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        Log(wsContext, conn, "Admin requests PENDING subscription");

        return result.toArray(new Subscription[0]); 
    }

    @WebMethod
    public void approveSubscriptionRequest(
        @WebParam(name = "subscriberId") Integer subscriberId,
        @WebParam(name = "creatorId") Integer creatorId
    ) {
        Connection conn = RequestReviewService.conn;

        String query = "UPDATE Subscription SET status = 'ACCEPTED' WHERE creator_id = ? AND subscriber_id = ?;";

        try(
            PreparedStatement pStatement = conn.prepareStatement(query)
        ) {

            pStatement.setInt(1, creatorId);
            pStatement.setInt(2, subscriberId);

            pStatement.execute();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        Log(wsContext, conn, "Admin approves subscription request");

        return;
    }

    @WebMethod
    public void declineSubscriptionRequest(
        @WebParam(name = "subscriberId") Integer subscriberId,
        @WebParam(name = "creatorId") Integer creatorId
    ) {
        Connection conn = RequestReviewService.conn;

        String query = "UPDATE Subscription SET status = 'REJECTED' WHERE creator_id = ? AND subscriber_id = ?;";

        try(
            PreparedStatement pStatement = conn.prepareStatement(query)
        ) {

            pStatement.setInt(1, creatorId);
            pStatement.setInt(2, subscriberId);

            pStatement.execute();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        Log(wsContext, conn, "Admin declined subscription request");

        return;
    }
}