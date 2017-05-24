package com.microwise.smartservices.strategy;

import com.microwise.smartservices.netconn.form.MessageBean;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lee on 5/24/2017.
 */
public class ShutdownCommandTest {
    private ShutdownCommand shutdownCommand = new ShutdownCommand();
    //@Test
    public void processMessage() throws Exception {
        MessageBean mb = new MessageBean();
        mb.getContentBean().setArgs(new String[]{"元智展厅集中管理平台已发出关机指令，本机将在1分钟后关闭。"});
        shutdownCommand.processMessage(mb);
    }
}