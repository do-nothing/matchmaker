package com.microwise.smartservices.moroesdb;

import com.microwise.smartservices.moroesdb.SmartDevice.DeviceRecorder;
import com.microwise.smartservices.moroesdb.powerswitch.PoRecorder;
import com.microwise.smartservices.netconn.form.MessageBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by lee on 5/22/2017.
 */
@Component("recordProcesser")
public class RecordProcesser {

    @Resource
    private PoRecorder poRecorder;
    @Resource
    private DeviceRecorder deviceRecorder;

    public void classificationProcessing(MessageBean mb) {
        if ("heartbeat".equals(mb.getStrategy())) {
            deviceRecorder.recordPreprocessing(mb);
        } else if ("po_heartbeat".equals(mb.getStrategy())) {
            poRecorder.recordPreprocessing(mb);
        }
    }
}