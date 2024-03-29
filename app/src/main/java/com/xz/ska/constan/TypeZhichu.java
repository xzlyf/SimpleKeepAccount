package com.xz.ska.constan;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.orhanobut.logger.Logger;
import com.xz.ska.R;
import com.xz.ska.entity.Category;
import com.xz.ska.sql.LitePalUtil;

import java.util.ArrayList;
import java.util.List;

public class TypeZhichu {
    private Context context;
    private static List<Integer> iconArry = new ArrayList<>();
    private static List<String> name = new ArrayList<>();
    private static String[] t_name;
    private static TypedArray t_iconArry;

    private static TypeZhichu typeUtil;

    private TypeZhichu(Context context) {
        this.context = context;
        Resources res = context.getResources();
        t_name = res.getStringArray(R.array.zhichu_name);
        //图片资源文件数组
        t_iconArry = res.obtainTypedArray(R.array.zhichu_icon);

        refresh();
    }


    /**
     * 刷新数据
     */
    public static void refresh() {


        name.clear();//回到首页时候这回再执行一遍，就会存在两次数据，所以要清空
        iconArry.clear();
        for (int i = 0; i < t_name.length; i++) {
            name.add(t_name[i]);
        }

        for (int i = 0; i < t_iconArry.length(); i++) {
            iconArry.add(t_iconArry.getResourceId(i, 0));
        }
        addUserCustom();
    }

    /**
     * 加入用户自定义的type
     */
    private static void addUserCustom() {
        List<Category> list = (List<Category>) LitePalUtil.queryAll(Category.class);
        Logger.d("用户自定义类型:" + list.size());
        for (Category category : list) {
            if (category.getState() == 0) {
                name.add(category.getName());
                iconArry.add(category.getIcon());
            }
        }
    }

    //程序启动时初始化
    public static TypeZhichu initType(Context context) {
        if (typeUtil == null) {
            return new TypeZhichu(context);
        }
        return typeUtil;
    }

    public static int getIcon(int i) {
        try {
            return iconArry.get(i);
        } catch (Exception e) {
            //不知为何有一定概率会出错
            return 0;
        }

    }

    public static String getName(int i) {
        try {
            return name.get(i);
        } catch (Exception e) {
            return "未定义";
        }
    }


    public static int getLength() {
        return iconArry.size();
    }
}
