package com.microwise.smartservices.netconn;

import com.microwise.smartservices.netconn.form.MessageBean;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lee on 7/3/2017.
 */
public abstract class AbstractMessenger implements Messenger {
    @Resource(name = "jsonConverter")
    private JsonConverter jsonConverter;

    @Override
    public String calcTokenByMessage(MessageBean messageBean) {
        messageBean.setToken(null);
        String jsonString = "microwiseMessage:" + jsonConverter.getJsonString(messageBean);
        try {
            MessageDigest md5=MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            String summary = base64en.encode(md5.digest(jsonString.getBytes("utf-8")));
            summary = summary.substring(0, 22);
            return summary;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
