package com.microwise.smartservices.moroesst.PowerLogic;

import com.microwise.smartservices.netconn.form.MessageBean;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Created by lee on 7/26/2017.
 */
@Component("commandConverter")
public class CommandConverter {
    private ObjectMapper mapper = new ObjectMapper();

    public List<CommandBean> getCommandList(String jsonArray){
        List<CommandBean> list = null;
        try {
            list = mapper.readValue(jsonArray, new TypeReference<List<CommandBean>>() { });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
