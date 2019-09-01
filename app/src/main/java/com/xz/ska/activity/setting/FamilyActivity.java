package com.xz.ska.activity.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xz.com.log.LogUtil;
import com.xz.ska.R;
import com.xz.ska.constan.Local;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FamilyActivity extends AppCompatActivity {
    //    private static final String FAMILY_URL ="http://192.168.0.177:8080//Family/data.json";
    private static final String FAMILY_URL = Local.BASE_HERF + "/Family/data.json";
    private NetModel mModel;
    private String TAG = "xz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        init();

        mModel.getNetData(FAMILY_URL, new NetModel.OnLoadCompleteListener() {
            @Override
            public void success(String data) {
                Log.d(TAG, "返回: " + data);
                /*
                待解析

                {
            "total": 3,
            "msg": "正常",
            "code": 1,
            "DATA": [
            {
                "icon": "http://192.168.0.177:8080/Family/icon/jlw.png",
                "name": "捡漏王",
                "download": null
            },
            {
                "icon": "http://192.168.0.177:8080/Family/icon/jlw.png",
                "name": "轻巧记账",
                "download": null
            },
            {
                "icon": "http://192.168.0.177:8080/Family/icon/jlw.png",
                "name": "每日壁纸",
                "download": null
            },
            {
                "icon": "http://192.168.0.177:8080/Family/icon/jlw.png",
                "name": "轻巧系列",
                "download": null
            },
            {
                "icon": "http://192.168.0.177:8080/Family/icon/jlw.png",
                "name": "极简天气",
                "download": null
            }
        ]
    }
                 */
            }

            @Override
            public void failed(Exception e) {

            }
        });
    }

    private void init() {
        mModel = new NetModel();

    }


    /**
     * 网络请求模块
     */
    static class NetModel {

        public void getNetData(final String url, final OnLoadCompleteListener listener) {
            new Thread(new Runnable() {
                @Override
                public void run() {


                    try {
                        //                    OkHttpClient client = new OkHttpClient();
                        OkHttpClient client = new OkHttpClient.Builder()
                                .connectTimeout(10, TimeUnit.SECONDS)
                                .readTimeout(10, TimeUnit.SECONDS)
                                .writeTimeout(10, TimeUnit.SECONDS)
                                .build();
                        Request request = new Request.Builder()
                                .url(url)
                                .build();
                        Response response = null;
                        response = client.newCall(request).execute();
                        String responseData = response.body().string();
                        listener.success(responseData);
                    } catch (IOException e) {
                        e.printStackTrace();
                        LogUtil.w("请求失败链接：" + url);
                        listener.failed(e);
                    }
                }
            }).start();
        }


        //回调接口
        interface OnLoadCompleteListener {
            void success(String data);

            void failed(Exception e);
        }
    }
}
