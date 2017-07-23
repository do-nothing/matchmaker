package com.microwise.smartservices.moroesdb.SmartDevice;

import com.microwise.smartservices.moroesdb.DbWriter;
import com.microwise.smartservices.netconn.form.MessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lee on 7/4/2017.
 */
@Component("deviceRecorder")
public class DeviceRecorder {
    @Resource
    private DbWriter dbWriter;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    Map<String, DeviceInfo> deviceMap = new HashMap<>();

    public DeviceRecorder() {
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                        rmExpired();
                    } catch (Exception e) {
                        logger.warn("remove expired DeviceInfo Exception.");
                    }
                }
            }
        }.start();
    }

    public void recordPreprocessing(MessageBean mb) {
        String id = mb.getId();
        try {
            Long.parseLong(id);
        } catch (Exception e) {
            return;
        }

        Object[] appInfo = mb.getContentBean().getArgs();

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setId(id);
        deviceInfo.setAppInfo(appInfo);
        deviceInfo.setTimestamp(System.currentTimeMillis());

        DeviceInfo preDeviceInfo = deviceMap.put(id, deviceInfo);
        if (preDeviceInfo == null) {
            if (appInfo[0] == null)
                dbWriter.saveIfDeviceOnline(id, true);
            else
                recordAppInfo(id, appInfo);
        } else {
            recordIfDifference(id, appInfo, preDeviceInfo.getAppInfo());
        }
    }

    private void recordIfDifference(String id, Object[] appInfo, Object[] oldAppInfo) {
        if (!Arrays.toString(appInfo).equals(Arrays.toString(oldAppInfo))) {
            recordAppInfo(id, appInfo);
        }
    }

    private void recordAppInfo(String id, Object[] appInfo) {
        if (appInfo[0] == null) {
            dbWriter.saveIfDeviceOnline(id, true);
            return;
        }

        if (appInfo[1] == null) {
            appInfo[1] = "";
        }

        if (appInfo[3] == null) {
            appInfo[3] = "";
        }
        dbWriter.saveAppInfo(id, appInfo[0].toString(), appInfo[1].toString(), appInfo[3].toString());
    }


    private void rmExpired() {
        long currentTime = System.currentTimeMillis();
        Iterator<Map.Entry<String, DeviceInfo>> it = deviceMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, DeviceInfo> entry = it.next();
            long timestamp = entry.getValue().getTimestamp();
            if (currentTime - timestamp > 10000) {
                it.remove();
                dbWriter.saveIfDeviceOnline(entry.getKey(), false);
            }
        }
    }
}
