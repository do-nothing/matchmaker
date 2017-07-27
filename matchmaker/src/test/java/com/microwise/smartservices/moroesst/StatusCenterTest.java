package com.microwise.smartservices.moroesst;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lee on 7/27/2017.
 */
public class StatusCenterTest {
    @Test
    public void getPortStatus() throws Exception {
        StatusCenter sc = new StatusCenter();
        sc.setPortStatus("abc_1", null);
        assertFalse(sc.getPortStatus("abc_1"));
        assertFalse(sc.getPortStatus("abc_2"));
        sc.setPortStatus("abc_1", false);
        sc.setPortStatus("abc_1", true);
        assertTrue(sc.getPortStatus("abc_1"));
        sc.rmPort("abc_5");

        //assertFalse(getNull() == false);
    }

    private Boolean getNull(){
        return null;
    }
}