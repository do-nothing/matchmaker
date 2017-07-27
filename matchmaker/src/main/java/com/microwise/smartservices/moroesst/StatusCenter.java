package com.microwise.smartservices.moroesst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by lee on 7/25/2017.
 */
@Component("statusCenter")
public class StatusCenter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Set<Integer> performingTask = new HashSet<Integer>();

    public void startTask(Integer deviceId) {
        performingTask.add(deviceId);
    }

    public void finishTask(Integer deviceId) {
        performingTask.remove(deviceId);
    }

    public boolean commandsIsLoak(Integer deviceId) {
        return performingTask.contains(deviceId);
    }

    private Map<String, Boolean> portsStatus = new HashMap<String, Boolean>();

    public boolean getPortStatus(String key) {
        //logger.debug("get port status --> " + key + " " + portsStatus.get(key));
        if (portsStatus.get(key) != null && portsStatus.get(key))
            return true;
        else
            return false;
    }

    public Boolean setPortStatus(String key, Boolean status) {
        //logger.debug("set port status --> " + key + " " + status);
        return portsStatus.put(key, status);
    }

    public void rmPort(String key) {
        //logger.debug("re port --> " + key);
        portsStatus.remove(key);
    }
}
