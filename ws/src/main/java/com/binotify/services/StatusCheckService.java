package com.binotify.services;

import com.binotify.helpers.DBConnector;
import com.binotify.model.Subscription;

// import javax.jws.HandlerChain;

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
// @HandlerChain(file="handler-chain.xml")
public class StatusCheckService {
    @Resource
    WebServiceContext wsContext;

    @WebMethod
    public String checkStatus(
        @WebParam(name = "apiKey") String apiKey,
        @WebParam(name = "subscriberId") int subscriberId,
        @WebParam(name = "creatorId") int creatorId
    ) {
        if (apiKey.equals(System.getenv("API_KEY_PLAIN"))) {
            System.out.println("Binotify App API Key is valid");
        } else if (apiKey.equals(System.getenv("API_KEY_REST"))) {
            System.out.println("Binotify REST API Key is valid");
        } else {
            System.out.println("API Key is invalid");
            return null;
        }

        String query = "SELECT status FROM subscription WHERE creator_id = ? AND subscriber_id = ?;";
        try (
            Connection conn = DBConnector.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)
        ) {
            Logger.log(wsContext, "User requests for subscription status");
            preparedStatement.setInt(1, creatorId);
            preparedStatement.setInt(2, subscriberId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Ran");
                return resultSet.getString("status");
            }
            else {
                return "NONE";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @WebMethod
    public Subscription[] retrieveAllStatus() {
        String query = "SELECT * FROM subscription WHERE is_polled = 0;";
        String updateQuery = "UPDATE subscription SET is_polled = 1;";

        try (
            Connection conn = DBConnector.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery)
        ) {
            Logger.log(wsContext, "Admin requests for all subscriptions");
            ArrayList<Subscription> subscriptionArrayList = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Subscription s = new Subscription();
                s.setCreatorId(resultSet.getInt("creator_id"));
                s.setSubscriberId(resultSet.getInt("subscriber_id"));
                s.setStatus(resultSet.getString("status"));

                subscriptionArrayList.add(s);
            }


            return subscriptionArrayList.toArray(new Subscription[0]);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

}
