package com.microwise.smartservices.moroesdb;

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

    public void classificationProcessing(MessageBean mb) {
        if("po_heartbeat".equals(mb.getStrategy())){
            poRecorder.recordPreprocessing(mb);
        }
    }
}