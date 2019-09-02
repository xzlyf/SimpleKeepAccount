package com.xz.ska.activity.setting;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.xz.com.log.LogUtil;
import com.xz.ska.R;
import com.xz.ska.constan.Local;
import com.xz.ska.entity.Framily;
import com.xz.ska.utils.SpacesItemDecorationVertical;

import org.json.JSONException;
import org.json.JSONObject;

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
    private XAdapter adapter;
    private RecyclerView recycler;
    private TextView loadView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 111:
                    showData(msg.obj);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        init();
        init_recycler();


        mModel.getNetData(FAMILY_URL, new NetModel.OnLoadCompleteListener() {
            @Override
            public void success(String data) {
                Log.d("xz", "成员列表数据: " + data);

                try {
                    JSONObject obj = new JSONObject(data);
                    if (obj.getInt("code") == 1) {


                        Gson gson = new Gson();
                        Framily framily = gson.fromJson(data, Framily.class);

                        Message message = handler.obtainMessage();
                        message.what = 111;
                        message.obj = framily;
                        handler.sendMessage(message);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failed(Exception e) {

            }
        });
    }


    private void init_recycler() {
        recycler = findViewById(R.id.recycler_app);
        adapter = new XAdapter(this);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new SpacesItemDecorationVertical(30));
        recycler.setAdapter(adapter);

    }

    private void init() {
        mModel = new NetModel();
        loadView = findViewById(R.id.load_view);
        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void showData(Object obj) {

        if (obj instanceof Framily) {

            adapter.refresh((Framily) obj);
        }
    }


    /**
     * =============================================================================================
     * 适配器
     */
    static class XAdapter extends RecyclerView.Adapter<XAdapter.ViewHolder> {
        private Framily mlist;
        private final Context mContext;

        XAdapter(Context context) {
            mContext = context;
            mlist = new Framily();
        }

        public void refresh(Framily list) {
            mlist.getDATA().addAll(list.getDATA());
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public XAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_family, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull XAdapter.ViewHolder viewHolder, int i) {
            viewHolder.tvContext.setText("测试");
            viewHolder.tvName.setText(mlist.getDATA().get(i).getName());
            Glide.with(mContext).load(mlist.getDATA().get(i).getIcon()).into(viewHolder.tvIcon);

        }

        @Override
        public int getItemCount() {
            return mlist.getDATA().size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            private CardView cardView;
            private ImageView tvIcon;
            private TextView tvName;
            private TextView tvContext;


            ViewHolder(@NonNull View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.cardView);
                tvIcon = itemView.findViewById(R.id.tv_icon);
                tvName = itemView.findViewById(R.id.tv_name);
                tvContext = itemView.findViewById(R.id.tv_context);
            }
        }
    }

    /**
     * =============================================================================================
     * 网络请求模块
     */
    static class NetModel {

        void getNetData(final String url, final OnLoadCompleteListener listener) {
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
