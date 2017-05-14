package com.microwise.matchmaker.netconn.udp;

import com.microwise.matchmaker.netconn.Messenger;
import org.springframework.stereotype.Component;

/**
 * Created by John on 2017/5/14.
 */

@Component("udpMessenger")
public class UdpMessenger implements Messenger {
    @Override
    public void sendMessage(Object o) {
        System.out.println("o has been send.");
    }

    @Override
    public Object getMessage() {
        return null;
    }
}
