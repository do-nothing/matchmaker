package com.microwise.smartservices.moroesst;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by John on 2017/7/24.
 */
public class DataToolsTest {
    @Test
    public void getIntByBytes() throws Exception {
        System.out.println(DataTools.getIntByBytes((byte)1, (byte)1));
    }

}