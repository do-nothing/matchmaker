package com.microwise.smartservices.pomanager;

/**
 * Created by lee on 6/23/2017.
 */
public class ByteTools {

    public static String bytesToHexString(byte[] bytes, int len) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        for (int i = 0; i < len; i++) {
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

    public static String getIdByString(String str) {
        byte[] bytes = hexStringToBytes(str);
        if (bytes.length == 21)
            return getIdByBytes(bytes);
        else return null;
    }

    private static String getIdByBytes(byte[] bytes) {
        byte[] idValue = new byte[16];
        for (int i = 0; i < 16; i++) {
            idValue[i] = bytes[i + 3];
        }
        return new String(idValue);
    }

    public static String getStatusByString(String str) {
        byte[] bytes = hexStringToBytes(str);
        return getStatusByBytes(bytes);
    }

    private static String getStatusByBytes(byte[] bytes) {
        String rt = "" + bytes[0];
        if (bytes[0] == -2 && bytes[1] == 1 && bytes[2] == 1) {
            rt = get01ByByte(bytes[3]);
        }
        return rt;
    }

    private static String get01ByByte(byte bt){
        String str = "";
        str = Integer.toBinaryString(bt + 16);
        str = str.substring(1,5);
        StringBuilder sb = new StringBuilder(str).reverse();
        return sb.toString();
    }
}