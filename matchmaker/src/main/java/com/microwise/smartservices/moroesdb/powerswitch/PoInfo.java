package com.microwise.smartservices.moroesdb.powerswitch;

/**
 * Created by lee on 6/29/2017.
 */
public class PoInfo {
    private String id;
    private String status;
    private long timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
