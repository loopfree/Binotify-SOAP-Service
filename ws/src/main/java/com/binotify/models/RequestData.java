package com.binotify.models;

import java.time.Instant;

public class RequestData {
    private String ip;
    private String endpoint;
    private Instant timestamp;
    private String description;

    public RequestData(String ip, String endpoint, Instant timestamp, String description) {
        this.ip = ip;
        this.endpoint = endpoint;
        this.timestamp = timestamp;
        this.description = description;
    }

    public String getIp() {
        return ip;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }
}