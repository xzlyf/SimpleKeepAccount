package com.xz.ska.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class TimeUtil {
    /**
     *
     * @return true 今天-数据不用更新  false 昨天-数据要更新
     */
    private static boolean isToday(long serverTime,long localTime) {
        //服务器时间
        Calendar cal = Calendar.getInstance();
        Date cal_date = new Date(serverTime);
        cal.setTime(cal_date);
        int today = cal.get(Calendar.DAY_OF_YEAR);
        System.out.println(today);

        //本地储存的时间
        Calendar local = Calendar.getInstance();
        Date local_date = new Date(localTime);
        local.setTime(local_date);
        int lt = local.get(Calendar.DAY_OF_YEAR);
        System.out.println(lt);

        if (today>lt) {
            //防止换年shift最后一天与第一的时间大小的问题
            //保证当前时间一定大于本地时间才返回false
            if(serverTime>localTime) {
                return false;
            }else {
                return true;
            }
        }else {
            return true;
        }

    }
    /**
     *  时间戳→自定义格式时间
     * @return
     */
    public static String getSimDate(Long time){
        return new SimpleDateFormat("yyyyMMdd").format(new Date(time));
    }
}
