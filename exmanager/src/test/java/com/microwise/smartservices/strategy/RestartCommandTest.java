package com.microwise.smartservices.strategy;

import com.microwise.smartservices.netconn.form.MessageBean;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lee on 5/25/2017.
 */
public class RestartCommandTest {
    private RestartCommand restartCommand = new RestartCommand();

    //@Test
    public void processMessage() throws Exception {
        MessageBean mb = new MessageBean();
        mb.getContentBean().setArgs(new String[]{"guanniao", "v1.0"});
        restartCommand.processMessage(mb);
    }

}