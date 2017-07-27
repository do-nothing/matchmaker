package com.microwise.smartservices.moroesdb;

import com.microwise.smartservices.moroesst.StatusCenter;
import com.microwise.smartservices.persistency.mapper.DeviceMapper;
import com.microwise.smartservices.persistency.mapper.Power_controllerMapper;
import com.microwise.smartservices.persistency.mapper.Power_controller_portMapper;
import com.microwise.smartservices.persistency.mapper.Power_eventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by lee on 6/29/2017.
 */
@Component("dbWriter")
public class DbWriter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "statusCenter")
    private StatusCenter statusCenter;

    @Resource
    private Power_controllerMapper power_controllerMapper;
    @Resource
    private Power_controller_portMapper power_controller_portMapper;
    @Resource
    private Power_eventMapper power_eventMapper;
    @Resource
    private DeviceMapper deviceMapper;

    public void saveIfPoOnline(String id, int isOnline) {
        logger.debug("set power switch '" + id + "' isOnline " + isOnline);
        power_controllerMapper.updateStatusByPid(id, isOnline);
        power_eventMapper.insertPowerEvent(id, null, String.valueOf(isOnline));
    }

    public void saveAllPorts(String id, String status) {
        for (int i = 0; i < 4; i++) {
            String flag = status.substring(i, i + 1);
            savePort(id, i + 1, flag);
        }
    }

    public void savePort(String id, int port, String flag) {
        logger.debug("set " + id + " port " + port + " --> " + flag);
        power_controller_portMapper.updateStatusByPidAndIndex(id, port - 1, flag);
        power_eventMapper.insertPowerEvent(id, port, flag);

        statusCenter.setPortStatus(id + "_" + port, "1".equals(flag) ? true : false);
    }

    public void saveIfDeviceOnline(String id, boolean isOnline) {
        logger.debug("set smart device '" + id + "' isOnline --> " + isOnline);
        int eventId;
        String note;
        if (isOnline) {
            eventId = 2;
            note = "The device is online.";
        } else {
            eventId = 1;
            note = "The device is offline.";
        }
        deviceMapper.updateDeviceInfo(id, eventId, note);
        deviceMapper.insertDeviceInfo(id, eventId);
        deviceMapper.insertAppInfoDebug(id, null, null, eventId);
    }

    public void saveAppInfo(String id, String appName, String appVersion, String isBusy) {
        logger.debug("set device(" + id + ") APP [" + appName + " (" + appVersion + ")] isBusy --> " + isBusy);
        int eventId = 0;
        String note = "";
        if ("1".equals(isBusy)) {
            eventId = 6;
            note = "APP [" + appName + " (" + appVersion + ")] is busy.";
        } else if ("0".equals(isBusy)) {
            eventId = 7;
            note = "APP [" + appName + " (" + appVersion + ")] is free.";
        }
        deviceMapper.updateAppInfo(id, appName, appVersion, eventId, note);
        deviceMapper.insertAppInfo(id, appName, appVersion, eventId);
        deviceMapper.insertAppInfoDebug(id, appName, appVersion, eventId);
    }
}
