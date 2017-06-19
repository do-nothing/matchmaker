package com.microwise.smartservices;

import org.springframework.stereotype.Component;

/**
 * Created by lee on 6/19/2017.
 */
@Component("appInfoViewer")
public class AppInfoViewer {
    private long currentTime;
    private Object[] appInfo = new Object[3];

    public void setAppInfo(Object[] appInfo){
        this.appInfo = appInfo;
        this.currentTime = System.currentTimeMillis();
    }

    public Object[] getAppInfo(){
        if(System.currentTimeMillis() - currentTime > 6000){
            appInfo[0] = null;
            appInfo[1] = null;
            appInfo[2] = null;
        }
        return appInfo;
    }
}
