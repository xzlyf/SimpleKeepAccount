package com.xz.ska.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xz.ska.R;

public class MulKeyBoard extends LinearLayout implements View.OnClickListener {


    private final View view;
    private final Context context;
    private EditText remarks;
    private EditText money;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btnDate;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btnMinus;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnAdd;
    private Button btnDec;
    private Button btn0;
    private Button btnClean;
    private Button btnSubmit;

    public MulKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = View.inflate(context, R.layout.custom_input_view, this);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        initView();

    }


    private void initView() {
        remarks = findViewById(R.id.remarks);
        money = findViewById(R.id.money);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btnDate = findViewById(R.id.btn_date);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btnMinus = findViewById(R.id.btn_minus);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnAdd = findViewById(R.id.btn_add);
        btnDec = findViewById(R.id.btn_dec);
        btn0 = findViewById(R.id.btn0);
        btnClean = findViewById(R.id.btn_clean);
        btnSubmit = findViewById(R.id.btn_submit);
    }

    @Override
    public void onClick(View view) {

    }
}
