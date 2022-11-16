package com.binotify.services;

import java.util.ArrayList;
import java.time.Instant;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.ws.developer.JAXWSProperties;

import javax.annotation.Resource;

import com.binotify.models.RequestData;
import com.binotify.models.SubscriptionData;

public class RequestReviewService {
    @Resource
    WebServiceContext wsContext;
    
    @WebMethod
    public ArrayList<SubscriptionData> getSubscriptionRequests() {
        MessageContext msgContext = wsContext.getMessageContext();
        HttpExchange req = (HttpExchange) msgContext.get(JAXWSProperties.HTTP_EXCHANGE);

        String ip = String.format("%s", req.getRemoteAddress());
        String endpoint = String.format("%s", req.getRequestURI());
        Instant timestamp = Instant.now();

        RequestData reqData = new RequestData(ip, endpoint, timestamp, "Admin Requests to view subcription requests");

        return new ArrayList<SubscriptionData>();
    }

    @WebMethod
    public void approveSubscriptionRequest(
        @WebParam(name = "subscriptionData") SubscriptionData suscriptionData
    ) {
        return;
    }

    @WebMethod
    public void declineSubscriptionRequest(
        @WebParam(name = "subscriptionData") SubscriptionData suscriptionData
    ) {
        return;
    }
}