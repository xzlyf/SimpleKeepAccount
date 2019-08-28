package com.xz.ska.utils;

import java.util.Random;

/**
 * 生成随机字符串
 */
public class RandomString {
    public static String getRandomString(int length){
        String st = "ABCDEF1234657980";
        Random random = new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(16);
            sb.append(st.charAt(number));
        }
        return sb.toString();
    }
}
