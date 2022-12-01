package com.binotify.handler;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class APIKeyHandler implements SOAPHandler<SOAPMessageContext> {
    @Override
    public boolean handleMessage(SOAPMessageContext context) throws RuntimeException {
        System.out.println("Server: handleMessage()......");
        Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        //for response message only, true for outbound messages, false for inbound
        if(!isRequest) {	
            try {
                SOAPMessage soapMsg = context.getMessage();
                SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
                SOAPHeader soapHeader = soapEnv.getHeader();
                        
                //if no header, add one
                if (soapHeader == null){
                    soapHeader = soapEnv.addHeader();
                    //throw exception
                    throw new RuntimeException("No SOAP header.");
                }

                //Get client API key from SOAP header
                String apiKey = soapHeader.getAttribute("APIKey");
                //if no header block for next actor found? throw exception
                if (apiKey == null){
                    throw new RuntimeException("No header block for API key.");
                }

                System.out.println("APIKey: " + apiKey);
                
                //if client API key is not valid? throw exception
                if (apiKey.equals("APInyaREST")) {
                    System.out.println("Valid Catify-REST API key.");
                } else if (apiKey.equals("APInyaPlain")) {
                    System.out.println("Valid Catify-App API key.");
                } else {
                    throw new RuntimeException("Invalid API key.");
                }

                return true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }

    @Override
    public void close(MessageContext context) {

    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }
}
