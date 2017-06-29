package com.microwise.smartservices.moroesdb.powerswitch;

import com.microwise.smartservices.persistency.mapper.Power_controllerMapper;
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

    @Resource
    private Power_controllerMapper power_controllerMapper;

    public void saveIfOnline(String id, int isOnline) {
        logger.debug("set " + id + " isOnline " + isOnline);
        power_controllerMapper.updateStatusByPid(id, isOnline);
    }

    public void saveAllPorts(String id, String status) {
        for (int i = 0; i < 4; i++) {
            String flag = status.substring(i, i + 1);
            savePort(id, i + 1, flag);
        }
    }

    public void savePort(String id, int port, String flag) {

    }
}
