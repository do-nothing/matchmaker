package com.microwise.smartservices.moroesst;

import java.util.List;

/**
 * Created by John on 2017/7/24.
 */
public class DataTools {
    public static int getIntByBytes(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < bytes.length; i++) {
            value <<= 8;
            value |= (bytes[i] & 0xff);
        }
        return value;
    }

    public static byte[] getBytesByInt(int data, int len) {
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            int digit = (len - 1 - i) * 8;
            bytes[i] = (byte) ((data >> digit) & 0xff);
        }
        return bytes;
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.replaceAll(" ", "");
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String bytesToHexString(List<Byte> lbyte) {
        byte[] bytes = getBytesFromList(lbyte);
        return bytesToHexString(bytes);
    }

    public static byte[] getBytesFromList(List<Byte> lbyte) {
        byte[] bytes = new byte[lbyte.size()];
        int i = 0;
        for (Byte b : lbyte) {
            bytes[i++] = b;
        }
        return bytes;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
            stringBuilder.append(" ");
        }
        String rt = stringBuilder.toString();
        rt = rt.trim();
        rt = rt.toUpperCase();
        return rt;
    }
}
