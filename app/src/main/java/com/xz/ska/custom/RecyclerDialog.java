package com.xz.ska.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xz.ska.R;
import com.xz.ska.entity.Currency;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表对话框
 */
public class RecyclerDialog extends Dialog {
    private Context mContext;
    private ImageView tvRefresh;
    private RecyclerView recyceler;
    private Button closeBtn;
    private Animation xuanzhaun;
    private XzRecyclerAdapter adapter;


    public RecyclerDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater1 = LayoutInflater.from(mContext);
        View view1 = inflater1.inflate(R.layout.dialog_recycler_list, null);
        setContentView(view1);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8);
        lp.height = (int) (d.heightPixels * 0.6);
        lp.dimAmount = 0.5f;//背景不变暗
        dialogWindow.setAttributes(lp);

        initAnim();
        initView();
        initRecycler();
    }

    private void initRecycler() {
        adapter=new XzRecyclerAdapter(mContext);
        recyceler.setLayoutManager(new LinearLayoutManager(mContext));
        recyceler.setAdapter(adapter);
    }


    private void initAnim() {
        xuanzhaun = AnimationUtils.loadAnimation(mContext, R.anim.xuanzhuan_fan);
    }

    private void initView() {
        tvRefresh = findViewById(R.id.tv_refresh);
        recyceler = findViewById(R.id.recyceler);
        closeBtn = findViewById(R.id.close_btn);

        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvRefresh.startAnimation(xuanzhaun);
            }
        });
    }

    /**
     * 适配器
     */
    class XzRecyclerAdapter extends RecyclerView.Adapter<XzRecyclerAdapter.ViewHolder> {
        private Context mContext;
        private List<Currency> mlist = new ArrayList<>();


        public XzRecyclerAdapter(Context context) {
            mContext = context;

        }
        public void refresh(List<Currency> list){
            mlist.addAll(list);

        }

        @NonNull
        @Override
        public XzRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            return new XzRecyclerAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_currency, viewGroup, false));

        }

        @Override
        public void onBindViewHolder(@NonNull XzRecyclerAdapter.ViewHolder viewHolder, int i) {
            viewHolder.nameCurrency.setText(mlist.get(i).getName());
            viewHolder.symbolCurrency.setText(mlist.get(i).getSymbol());

        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView nameCurrency;
            TextView symbolCurrency;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                nameCurrency = itemView.findViewById(R.id.name_currency);
                symbolCurrency = itemView.findViewById(R.id.symbol_currency);
            }


        }

    }
}

