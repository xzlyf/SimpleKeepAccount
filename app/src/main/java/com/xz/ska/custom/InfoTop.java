package com.xz.ska.custom;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xz.ska.R;
import com.xz.ska.constan.Local;
import com.xz.ska.utils.DatePickerUtil;

public class InfoTop extends FrameLayout {
    private TextView shouruText;
    private TextView zhichuText;
    private TextView dateChoose;
    private TextView todayMoneyText;

    private Context context;
    private View view;
    private DatePickerDialog.OnDateSetListener listener;

    public InfoTop(Context context) {
        super(context);
    }

    public InfoTop(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = View.inflate(context, R.layout.custom_info_top, this);
        this.context = context;
        init();
    }

    public InfoTop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = View.inflate(context, R.layout.custom_info_top, this);
        this.context = context;
        init();
    }


    private void init() {
        shouruText = view.findViewById(R.id.shouru_text);
        zhichuText = view.findViewById(R.id.zhichu_text);
        dateChoose = view.findViewById(R.id.date_choose);
        todayMoneyText = view.findViewById(R.id.today_money_text);
        dateChoose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerUtil.showDatePicker(context, listener);

            }
        });

    }

    public void setDateSetListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    public void setDateChoose(String m,String d) {
        dateChoose.setText(m+"月"+d+"日");
    }

    public void setZhiChu(String t) {
        zhichuText.setText(t + Local.moneySymbol);
    }

    public void setShouRu(String t) {
        shouruText.setText(t + Local.moneySymbol);
    }

    public void setTodayText(String t) {
        todayMoneyText.setText(t + Local.moneySymbol);
    }
}
