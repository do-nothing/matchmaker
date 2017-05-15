package com.microwise.matchmaker.netconn.udp;

import java.io.IOException;

import com.microwise.matchmaker.netconn.MessageBean;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

@Component("jsonConverter")
public class JsonConverter {

    private ObjectMapper mapper = new ObjectMapper();

    public MessageBean getMessageBean(String json){
        MessageBean messageBean = null;
        try {
            messageBean = mapper.readValue(json, MessageBean.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messageBean;
    }

    public String getJsonString(MessageBean messageBean){
        String json = null;
        try {
            json = mapper.writeValueAsString(messageBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}