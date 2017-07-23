package com.microwise.smartservices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by lee on 6/19/2017.
 */
@PropertySource("application.properties")
@Component("appInfoViewer")
public class AppInfoViewer {
    @Value("${defaultApp}")
    private String defaultApp;
    private long currentTime = System.currentTimeMillis();
    private Object[] appInfo = new Object[4];

    public void setAppInfo(Object[] appInfo){
        this.appInfo = appInfo;
        this.currentTime = System.currentTimeMillis();
    }

    public Object[] getAppInfo(){
        if(System.currentTimeMillis() - currentTime > 6000){
            appInfo[0] = null;
            appInfo[1] = null;
            appInfo[2] = null;
            appInfo[3] = null;
        }

        if(!defaultApp.isEmpty() && System.currentTimeMillis() - currentTime > 30000){
            try {
                Runtime.getRuntime().exec("cmd /c start restart " + defaultApp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.currentTime = System.currentTimeMillis();
        }

        return appInfo;
    }
}