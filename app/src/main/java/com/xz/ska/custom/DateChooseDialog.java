package com.xz.ska.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.xz.ska.R;

public class DateChooseDialog extends Dialog {
    private Context context;
    private DatePicker datePchoose;

    public DateChooseDialog(Context context) {
        super(context);
        this.context = context;
        init();
    }


    public DateChooseDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        init();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater1 = LayoutInflater.from(context);
        View view1 = inflater1.inflate(R.layout.custom_date_choose, null);
        setContentView(view1);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        lp.dimAmount = 0.4f;//背景不变暗
        dialogWindow.setAttributes(lp);


        datePchoose = findViewById(R.id.date_choose);
    }

}
