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

    public static final String FLASHON_1 = "FE 10 00 03 00 02 04 00 02 00 0A A1 6A";
    public static final String FLASHON_2 = "FE 10 00 08 00 02 04 00 02 00 0A E0 D9";
    public static final String FLASHON_3 = "FE 10 00 0D 00 02 04 00 02 00 0A 20 E6";
    public static final String FLASHON_4 = "FE 10 00 12 00 02 04 00 02 00 0A 61 AA";
    public static final String FLASHOFF_1 = "FE 10 00 03 00 02 04 00 04 00 0A 41 6B";
    public static final String FLASHOFF_2 = "FE 10 00 08 00 02 04 00 04 00 0A 00 D8";
    public static final String FLASHOFF_3 = "FE 10 00 0D 00 02 04 00 04 00 0A C0 E7";
    public static final String FLASHOFF_4 = "FE 10 00 12 00 02 04 00 04 00 0A 81 AB";

    public static String[] turnOnArray = new String[]{OPEN_1, OPEN_2, OPEN_3, OPEN_4};
    public static String[] turnOffArray = new String[]{CLOSE_1, CLOSE_2, CLOSE_3, CLOSE_4};
    public static String[] flashOnArray = new String[]{FLASHON_1, FLASHON_2, FLASHON_3, FLASHON_4};
    public static String[] flashOffArray = new String[]{FLASHOFF_1, FLASHOFF_2, FLASHOFF_3, FLASHOFF_4};
}
