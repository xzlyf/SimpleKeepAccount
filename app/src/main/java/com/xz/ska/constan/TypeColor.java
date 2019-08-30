package com.xz.ska.constan;

import android.content.Context;
import android.content.res.Resources;

import com.xz.ska.R;

import java.util.Random;

public class TypeColor {
    private Context mContext;
    private static TypeColor typeColor;
    private static Resources res;
    private static int[] colorArray;//图片资源文件数组

    private TypeColor(Context context) {
        mContext = context;
        res = context.getResources();
        colorArray = res.getIntArray(R.array.color);

    }

    //程序启动时初始化
    public static TypeColor initType(Context context) {
        if (typeColor == null) {
            return new TypeColor(context);
        }
        return typeColor;
    }

    private static Random random = new Random();

    public static int getRandomColor() {
        return colorArray[random.nextInt(getLength())];
    }

    public static int getLength() {
        return colorArray.length;
    }

}
