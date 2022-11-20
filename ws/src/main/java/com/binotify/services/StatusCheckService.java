package com.binotify.services;

import com.binotify.helpers.DBConnector;
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

@WebService
public class StatusCheckService {
    @Resource
    WebServiceContext wsContext;

    @WebMethod
    public String checkStatus(
            @WebParam(name = "subscriberId") int subscriberId,
            @WebParam(name = "creatorId") int creatorId
    ) {
        try {
            Connection conn = DBConnector.getConnection();
            String query = "SELECT status FROM Subscription WHERE creator_id = ? AND subscriber_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, subscriberId);
            preparedStatement.setInt(2, creatorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.first()) {
                return resultSet.getString("status");
            }
            else {
                return "NONE";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @WebMethod
    public Subscription[] retrieveAllStatus() {
        try {
            ArrayList<Subscription> subscriptionArrayList = new ArrayList<>();
            Connection conn = DBConnector.getConnection();
            String query = "SELECT * FROM Subscription";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
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
        }
        return null;
    }

}
