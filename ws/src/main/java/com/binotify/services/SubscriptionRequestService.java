package com.binotify.services;

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

@WebService
public class SubscriptionRequestService {

    @Resource
    WebServiceContext wsContext;
    
    @WebMethod
    public boolean requestSubscription(
        @WebParam(name = "subscriberId") String subscriberId,
        @WebParam(name = "creatorId") String creatorId
    ) {
        MessageContext msgContext = wsContext.getMessageContext();
        HttpExchange req = (HttpExchange) msgContext.get(JAXWSProperties.HTTP_EXCHANGE);

        String ip = String.format("%s", req.getRemoteAddress());
        String endpoint = String.format("%s", req.getRequestURI());
        Instant timestamp = Instant.now();

        RequestData reqData = new RequestData(ip, endpoint, timestamp, "User Requests Subscription");

        return true;
    }
}