package com.microwise.matchmaker.netconn.udp;

import java.io.IOException;

import com.microwise.matchmaker.netconn.MessageBean;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
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
    public static void main(String[] args) throws IOException {
        String s = "{\"id\":\"123\"}";
        JsonConverter jc = new JsonConverter();

        MessageBean m = jc.getMessageBean(s);
        String str = jc.getJsonString(m);

        System.out.println(str);
    }

}