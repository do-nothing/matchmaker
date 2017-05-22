package com.microwise.matchmaker.strategy;

import com.microwise.matchmaker.form.MessageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lee on 5/22/2017.
 */
@Component("messageProcesser")
public class MessageProcesser {

    @Resource
    private Map<String, IStrategy> commandMap;

    public MessageBean precessMassage(MessageBean oldMb) {
        System.out.println(commandMap.get("updateUserInfo"));
        MessageBean newMb = null;
        String commandStr = oldMb.getContentBean().getCommand();
        IStrategy commandStrategy = commandMap.get(commandStr);
        if(null != commandStrategy) {
            newMb = commandStrategy.processMessage(oldMb);
        }
        return newMb;
    }
}
