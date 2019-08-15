package com.xz.ska.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xz.ska.R;
import com.xz.ska.constan.Local;

import java.util.logging.Logger;

public class InfoTop extends FrameLayout {
    private TextView shouruText;
    private TextView zhichuText;
    private TextView dateChoose;
    private TextView todayMoneyText;

    private Context context;




    public InfoTop(Context context,  AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_info_top, this);
        this.context = context;
        init();

    }

    private void init() {
        shouruText = findViewById(R.id.shouru_text);
        zhichuText = findViewById(R.id.zhichu_text);
        dateChoose = findViewById(R.id.date_choose);
        todayMoneyText = findViewById(R.id.today_money_text);
    }

    public void setZhiChu(String t){
        zhichuText.setText(t+ Local.moneySymbol);
    }
    public void setShouRu(String t){
        shouruText.setText(t+Local.moneySymbol);
    }
    public void setTodayText(String t){
        todayMoneyText.setText(t+Local.moneySymbol);
    }
}
