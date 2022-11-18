package com.binotify.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.time.Instant;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.ws.developer.JAXWSProperties;

import javax.annotation.Resource;

import com.binotify.helpers.Logging;

public class RequestReviewService {
    @Resource
    WebServiceContext wsContext;
    
    @WebMethod
    public ArrayList<HashMap<String, String>> getSubscriptionRequests() {
        MessageContext msgContext = wsContext.getMessageContext();
        HttpExchange req = (HttpExchange) msgContext.get(JAXWSProperties.HTTP_EXCHANGE);

        String ip = String.format("%s", req.getRemoteAddress());
        String endpoint = String.format("%s", req.getRequestURI());
        Instant timestamp = Instant.now();

        return new ArrayList<>();
    }

    @WebMethod
    public void approveSubscriptionRequest(
        @WebParam(name = "subscriberId") Integer subscriberId,
        @WebParam(name = "creatorId") Integer creatorId
    ) {
        return;
    }

    @WebMethod
    public void declineSubscriptionRequest(
        @WebParam(name = "subscriberId") Integer subscriberId,
        @WebParam(name = "creatorId") Integer creatorId
    ) {
        return;
    }
}