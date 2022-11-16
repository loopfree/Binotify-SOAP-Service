package com.binotify.models;

public class SubscriptionData {
    private String subscriberId;
    private String creatorId;

    public SubscriptionData(String subscriberId, String creatorId) {
        this.subscriberId = subscriberId;
        this.creatorId = creatorId;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public String getCreatorId() {
        return creatorId;
    }
}