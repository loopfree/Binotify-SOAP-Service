package com.binotify.services;

import com.binotify.helpers.DBConnector;
import jdk.internal.loader.AbstractClassLoaderValue;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.ws.WebServiceContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class StatusCheckService {
    @Resource
    WebServiceContext wsContext;

    @WebMethod
    public String checkStatus(
            @WebParam(name = "subscriberId") Integer subscriberId,
            @WebParam(name = "creatorId") Integer creatorId
    ) {
        Connection conn;
        try {
            DBConnector db = new DBConnector();
            conn = db.getConnection();
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

}
