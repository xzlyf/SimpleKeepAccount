package com.xz.ska.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xz.ska.R;

public class InfoTop extends FrameLayout {
    private TextView shouruText;
    private TextView zhichuText;
    private TextView dateChoose;
    private TextView todayMoneyText;



    public InfoTop(Context context) {
        super(context);
        init();
    }

    public InfoTop(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InfoTop(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        shouruText = findViewById(R.id.shouru_text);
        zhichuText = findViewById(R.id.zhichu_text);
        dateChoose = findViewById(R.id.date_choose);
        todayMoneyText = findViewById(R.id.today_money_text);
    }

    public void setZhiChu(String t){
        zhichuText.setText(t);
    }
    public void setShouRu(String t){
        shouruText.setText(t);
    }
    public void setTodayText(String t){
        todayMoneyText.setText(t);
    }
}
