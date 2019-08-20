package com.xz.ska.custom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xz.com.log.LogUtil;
import com.xz.ska.MyApplication;
import com.xz.ska.R;
import com.xz.ska.constan.Local;

import java.math.BigDecimal;

public class MulKeyBoardDialog extends Dialog {


    private Context mContext;
    private KeyboardView mNumberView;   //数字键盘View
    private Keyboard mNumberKeyboard;   // 数字键盘
    private EditText money;// 金额
    private EditText remarks;//备注
    private TextView wordCount;//字数
    private ImageView selectType;//选中的图标

    private final int DATE_SELECT = -1;
    private final int ADD = 43;
    private final int SUB = 45;
    private final int SUBMIT = -4;
    private final int BACKSPACE = -5;

    private EditText et;

    //父类活动id，用于关闭父类
    private int parenTaskId;

    public void setEditable(EditText editable) {
        this.et = editable;
    }



    public MulKeyBoardDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);

        mContext = context;

    }

    public void setIcon(int resId){
        switch (resId){
            case 0:
                selectType.setImageResource(R.drawable.id_gouwu);
//                viewHolder.name.setText("购物");
                break;
            case 1:
                selectType.setImageResource(R.drawable.id_jiaotong);
//                viewHolder.name.setText("交通");
                break;
            case 2:
                selectType.setImageResource(R.drawable.id_yinshi);
//                viewHolder.name.setText("饮食");
                break;
            case 3:
                selectType.setImageResource(R.drawable.id_tongxun);
//                viewHolder.name.setText("通讯");
                break;
            case 4:
                selectType.setImageResource(R.drawable.id_yule);
//                viewHolder.name.setText("娱乐");
                break;
        }
    }

    public void setTaskId(int id){
        parenTaskId = id;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater1 = LayoutInflater.from(mContext);
        View view1 = inflater1.inflate(R.layout.custom_input_view, null);
        setContentView(view1);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        lp.dimAmount = 0.4f;//背景不变暗
        dialogWindow.setAttributes(lp);

        mNumberKeyboard = new Keyboard(mContext, R.xml.custom_keyboard);
        mNumberView = findViewById(R.id.custom_keyboard);
        money = findViewById(R.id.money);
        remarks = findViewById(R.id.remarks);
        wordCount = findViewById(R.id.text_count);
        selectType = findViewById(R.id.select_type);

        setEditable(money);

        mNumberView.setKeyboard(mNumberKeyboard);
        mNumberView.setEnabled(true);
        mNumberView.setPreviewEnabled(false);
        mNumberView.setOnKeyboardActionListener(listener);

        remarks.addTextChangedListener(textWatcher);


    }

//    private void init(Context context) {
//        mContext = context;
//        LayoutInflater.from(context).inflate(R.layout.custom_input_view, this);
//
//        mNumberKeyboard = new Keyboard(mContext, R.xml.custom_keyboard);
//        mNumberView = findViewById(R.id.custom_keyboard);
//        money = findViewById(R.id.money);
//        setEditable(money);
//
//        mNumberView.setKeyboard(mNumberKeyboard);
//        mNumberView.setEnabled(true);
//        mNumberView.setPreviewEnabled(false);
//        mNumberView.setOnKeyboardActionListener(listener);
//    }

    private int start;
    private Editable editable;
    /**
     * 键盘监听
     */
    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        /**
         * 按下，在onKey之前，可以在这里做一些操作，这里让有的没有按下的悬浮提示
         * @param i
         */
        @Override
        public void onPress(int i) {
        }

        /**
         * 松开
         * @param primaryCode
         */
        @Override
        public void onRelease(int primaryCode) {
        }

        /**
         * 按下
         * @param i
         * @param keyCodes
         */
        @Override
        public void onKey(int i, int[] keyCodes) {

            editable = et.getText();
            start = et.getSelectionStart();
            //删除键操作
            if (i == BACKSPACE) {
                // 回退键,删除字符
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else if (i == SUBMIT) {


            } else {
                //都不是自定义的值就插入
                editable.insert(start, Character.toString((char) i));
            }


        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };


    /**
     * 备注框监听
     */
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (wordCount.getVisibility()==View.GONE){
                wordCount.setVisibility(View.VISIBLE);
            }
            if (remarks.length()==0){
                wordCount.setVisibility(View.GONE);
            }
            wordCount.setText(remarks.length()+"/100");
        }
    };


}
