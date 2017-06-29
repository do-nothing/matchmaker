package com.microwise.smartservices.netconn.form;

/**
 * Created by John on 2017/5/14.
 */
public class MessageBean {
    private String id;
    private String target;
    private String monitorId;
    private String logType;
    private String strategy;
    private int quality;
    private long timestamp;
    private ContentBean contentBean = new ContentBean();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ContentBean getContentBean() {
        return contentBean;
    }

    public void setContentBean(ContentBean contentBean) {
        this.contentBean = contentBean;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "id='" + id + '\'' +
                ", target='" + target + '\'' +
                ", monitorId='" + monitorId + '\'' +
                ", logType='" + logType + '\'' +
                ", strategy='" + strategy + '\'' +
                ", quality=" + quality +
                ", timestamp=" + timestamp +
                ", contentBean=" + contentBean +
                '}';
    }
}
