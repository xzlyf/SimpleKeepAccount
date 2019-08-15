package com.xz.ska.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import static com.xz.ska.utils.CheckPackage.checkPackage;

public class TaobaoUtil {
    private static final String packageName = "com.taobao.taobao";

    public static void jump2TaobaoQuan(Context context, String QuanUrl) {

        Uri uri = Uri.parse(QuanUrl);

        if (checkPackage(context, packageName)) {
            //跳转到淘宝客户端优惠券界面
            Intent intent = new Intent();
            intent.setAction("Android.intent.action.VIEW");
            intent.setData(uri);
            intent.setClassName(packageName, "com.taobao.browser.BrowserActivity");
            context.startActivity(intent);
        } else {

            //跳转到浏览器优惠券界面
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            Toast.makeText(context, "推荐使用淘宝App进行领券", Toast.LENGTH_LONG).show();
        }
    }
}
