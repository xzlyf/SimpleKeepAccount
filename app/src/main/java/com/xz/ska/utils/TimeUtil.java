package com.xz.ska.utils;

import com.xz.com.log.LogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class TimeUtil {
    /**
     * @return true 今天-数据不用更新  false 昨天-数据要更新
     */
    private static boolean isToday(long serverTime, long localTime) {
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

        if (today > lt) {
            //防止换年shift最后一天与第一的时间大小的问题
            //保证当前时间一定大于本地时间才返回false
            if (serverTime > localTime) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }

    }

    /**
     * 时间戳→自定义格式时间
     *
     * @return
     */
    public static String getSimDate(String pattern, Long time) {
        return new SimpleDateFormat(pattern).format(new Date(time));
    }

    /**
     * 日期 转 时间戳
     *
     * @param dateString
     * @param pattern
     * @return
     */
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 获取startdate和enddate
     *
     * [0] startdate
     * [1] enddate
     * @param s
     * @return
     */
    public static String[] getStartAndEndDate(long s) {
        String startdate = getSimDate("yyyy-MM-dd", s) + " 00:00:00";
        String enddate = getSimDate("yyy-MM-dd", s) + " 23:59:59";

        startdate = getStringToDate(startdate,"yyyy-MM-dd HH:mm:ss")+"";
        enddate = getStringToDate(enddate,"yyyy-MM-dd HH:mm:ss")+"";


//        LogUtil.e(startdate+"::::"+enddate);

        return new String[]{startdate,enddate};
    }
}
