package com.microwise.smartservices.pomanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lee on 6/26/2017.
 */
public class PoInfo {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String id;
    public String status;
    public boolean isAlive = false;
    public boolean needToChangeStatus = true;
    public int[] flashTimes = new int[]{0, 0, 0, 0};

    private String targetStatus;

    public String getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(String targetStatus) {
        if (targetStatus != null && "0000".equals(targetStatus.replaceAll("1", "0"))) {
            this.targetStatus = targetStatus;
        }else {
            logger.debug("targetStatus " + targetStatus + " is illegal.");
        }
    }
}
