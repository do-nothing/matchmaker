package com.microwise.smartservices.moroesst.PowerLogic;

/**
 * Created by lee on 7/27/2017.
 */
public class AppInfoBean {
    private int app_id;
    private String name;
    private String version;

    @Override
    public String toString() {
        return "AppInfoBean{" +
                "app_id=" + app_id +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
