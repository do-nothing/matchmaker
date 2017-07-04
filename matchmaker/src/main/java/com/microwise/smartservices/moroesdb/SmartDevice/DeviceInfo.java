package com.microwise.smartservices.moroesdb.SmartDevice;

import java.util.Arrays;

/**
 * Created by lee on 6/29/2017.
 */
public class DeviceInfo {
    private String id;
    private Object[] appInfo;
    private long timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object[] getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(Object[] appInfo) {
        this.appInfo = appInfo;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "id='" + id + '\'' +
                ", AppInfo=" + Arrays.toString(appInfo) +
                ", timestamp=" + timestamp +
                '}';
    }
}
