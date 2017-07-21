package com.microwise.smartservices.moroesdb.powerswitch;

import com.microwise.smartservices.moroesdb.DbWriter;
import com.microwise.smartservices.netconn.form.MessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lee on 6/29/2017.
 */
@Component("poRecorder")
public class PoRecorder {
    @Resource
    private DbWriter dbWriter;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    Map<String, PoInfo> poMap = new HashMap<>();

    public PoRecorder() {
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                        rmExpired();
                    } catch (Exception e) {
                        logger.warn("remove expired poInfo Exception.");
                    }
                }
            }
        }.start();
    }

    public void recordPreprocessing(MessageBean mb) {
        String id = mb.getId();
        try{
            Long.parseLong(id);
        }catch(Exception e){
            return;
        }

        Object[] args = mb.getContentBean().getArgs();
        if (args == null) {
            return;
        }
        String status = args[0].toString();

        PoInfo poInfo = new PoInfo();
        poInfo.setId(id);
        poInfo.setStatus(status);
        poInfo.setTimestamp(System.currentTimeMillis());

        PoInfo prePoInfo = poMap.put(id, poInfo);
        if (prePoInfo == null) {
            dbWriter.saveIfPoOnline(id, 1);
            dbWriter.saveAllPorts(id, status);
        } else {
            recordIfDifference(id, status, prePoInfo.getStatus());
        }
    }

    private void recordIfDifference(String id, String newStatus, String oldStatus) {
        if (newStatus.equals(oldStatus)) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            String newFlag = newStatus.substring(i, i + 1);
            String oldFlag = oldStatus.substring(i, i + 1);
            if (!newFlag.equals(oldFlag)) {
                dbWriter.savePort(id, i + 1, newFlag);
            }
        }
    }

    private void rmExpired() {
        long currentTime = System.currentTimeMillis();
        Iterator<Map.Entry<String, PoInfo>> it = poMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, PoInfo> entry = it.next();
            long timestamp = entry.getValue().getTimestamp();
            if (currentTime - timestamp > 10000) {
                it.remove();
                dbWriter.saveIfPoOnline(entry.getKey(), 0);
            }
        }
    }
}
