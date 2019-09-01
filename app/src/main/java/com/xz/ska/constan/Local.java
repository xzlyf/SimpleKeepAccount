package com.xz.ska.constan;

public class Local {
    public static String moneySymbol;//货币符号
    public static int state = 0;//0支出  1收入
    public static final int IS_REFRESH = 0;//回调标识

    private static final String HERF = "http://";
    //    private static final String BASE_SERVER = "www.xzlyf.club";
    //    private static final String PORT = "";
    private static final String BASE_SERVER = "192.168.0.177";
    private static final String PORT = "8080";
    public static final String BASE_HERF = HERF + BASE_SERVER + ":" + PORT;
    public static final String UPDATE_SERVER = HERF + BASE_SERVER + ":" + PORT + "/SimpleKeepAccount/update.json";//默认更新地址


    public static boolean tipsSwitch;//自定义标语开关
    public static String tipsText;


    public static int level = -1;
    public static String name;
    public static int code;
    public static String msg;
    public static String link;
}
