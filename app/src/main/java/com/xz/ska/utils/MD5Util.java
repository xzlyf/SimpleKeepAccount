package com.xz.ska.utils;

import java.security.MessageDigest;

public class MD5Util {

    public static String getMD5(String st) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(st.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();

            for (byte item : array) {

                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));

            }
            return sb.toString().toUpperCase();

        }catch (Exception e){
            return null;
        }
    }
}
