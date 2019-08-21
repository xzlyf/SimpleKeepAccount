package com.xz.ska.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.xz.ska.R;

import java.util.Random;

public class TypeUtil {
    private Context context;
    private static TypedArray iconArry;//图片资源文件数组
    private static TypedArray colorArry;//颜色数组
    private static String[] name;
    private static Resources res;
    private static Random random;

    private static TypeUtil typeUtil;

    private TypeUtil(Context context) {
        this.context = context;
        res = context.getResources();
        name = res.getStringArray(R.array.name);
        iconArry = res.obtainTypedArray(R.array.icon);
        colorArry = res.obtainTypedArray(R.array.color);
        random = new Random();
    }


    //程序启动时初始化
    public static TypeUtil initType(Context context) {
        if (typeUtil == null) {
            return new TypeUtil(context);
        }
        return typeUtil;
    }

    public static int getIcon(int i) {
        try {
            return iconArry.getResourceId(i, R.drawable.empty_tips_2);
        } catch (Exception e) {
            //不知为何有一定概率会出错
            return 0;
        }

    }

    public static String getName(int i) {
        try {
            return name[i];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "未定义";
        }
    }

    public static int getRandomColor() {

        return 0;
    }

    public static int getLength() {
        return iconArry.length();
    }
}
