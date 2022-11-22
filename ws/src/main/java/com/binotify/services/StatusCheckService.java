package com.binotify.services;

import com.binotify.model.Subscription;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.binotify.helpers.Logger;

@WebService
public class StatusCheckService implements Logger{
    @Resource
    WebServiceContext wsContext;

    public static Connection conn;    

    @WebMethod
    public String checkStatus(
            @WebParam(name = "subscriberId") int subscriberId,
            @WebParam(name = "creatorId") int creatorId
    ) {
        Connection conn = StatusCheckService.conn;
        String status = null;
        String query = "SELECT status FROM Subscription WHERE creator_id = ? AND subscriber_id = ?";

        try (
            PreparedStatement pStatement = conn.prepareStatement(query)
        ){
            pStatement.setInt(1, subscriberId);
            pStatement.setInt(2, creatorId);
            ResultSet resultSet = pStatement.executeQuery();

            if (resultSet.first()) {
                status = resultSet.getString("status");
            }
            else {
                status = "NONE";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Log(wsContext, conn, "User requests for subscription status");

        return status;
    }

    @WebMethod
    public Subscription[] retrieveAllStatus() {
        Subscription[] result = null;
        Connection conn = StatusCheckService.conn;
        
        try (
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM Subscription")
        ){
            ArrayList<Subscription> subscriptionArrayList = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Subscription s = new Subscription();
                s.setCreatorId(resultSet.getInt("creator_id"));
                s.setSubscriberId(resultSet.getInt("subscriber_id"));
                s.setStatus(resultSet.getString("status"));
                subscriptionArrayList.add(s);
            }

            result = subscriptionArrayList.toArray(new Subscription[0]);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Log(wsContext, conn, "Admin requests for all subscriptions");

        return result;
    }
}
