package com.binotify.model;


public class Subscription {
    private int creatorId;
    private int subscriberId;
    private String status;

    public Subscription() {
    }

    public int getCreatorId() {
        return creatorId;
    }

    public int getSubscriberId() {
        return subscriberId;
    }

    public String getStatus() {
        return status;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
