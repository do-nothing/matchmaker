package com.microwise.smartservices.pomanager;

/**
 * Created by lee on 6/26/2017.
 */
public class CommandDict {
    public static final String ASK_ID = "FE 04 03 EE 00 08 85 B2";
    public static final String ASK_STATUS = "FE 01 00 00 00 04 29 C6";

    public static final String OPEN_1 = "FE 05 00 00 FF 00 98 35";
    public static final String CLOSE_1 = "FE 05 00 00 00 00 D9 C5";
    public static final String OPEN_2 = "FE 05 00 01 FF 00 C9 F5";
    public static final String CLOSE_2 = "FE 05 00 01 00 00 88 05";
    public static final String OPEN_3 = "FE 05 00 02 FF 00 39 F5";
    public static final String CLOSE_3 = "FE 05 00 02 00 00 78 05";
    public static final String OPEN_4 = "FE 05 00 03 FF 00 68 35";
    public static final String CLOSE_4 = "FE 05 00 03 00 00 29 C5";

    public static String[] turnOnArray = new String[]{OPEN_1, OPEN_2, OPEN_3, OPEN_4};
    public static String[] turnOffArray = new String[]{CLOSE_1, CLOSE_2, CLOSE_3, CLOSE_4};
}
