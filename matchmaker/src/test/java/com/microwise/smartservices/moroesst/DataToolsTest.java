package com.microwise.smartservices.moroesst;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by John on 2017/7/24.
 */
public class DataToolsTest {
    @Test
    public void getIntByBytes() throws Exception {
        int len = DataTools.getIntByBytes(new byte[]{(byte)1, (byte)1});
        assertEquals(len, 257);
        len = DataTools.getIntByBytes(new byte[]{(byte)0, (byte)128});
        assertEquals(len, 128);
    }

    @Test
    public void getBytesByInt() throws Exception {
        byte[] data = DataTools.getBytesByInt(127, 2);
        assertEquals("00 7F", DataTools.bytesToHexString(data));
        data = DataTools.getBytesByInt(128, 2);
        assertEquals("00 80", DataTools.bytesToHexString(data));
    }

}