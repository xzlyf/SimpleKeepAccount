package com.xz.ska.custom;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xz.ska.R;

public class MulKeyBoard extends LinearLayout {


    private Context mContext;
    private KeyboardView mNumberView;   //数字键盘View
    private Keyboard mNumberKeyboard;   // 数字键盘
    private EditText money;// 金额

    private final int DATE_SELECT = -1;
    private final int ADD = -2;
    private final int SUB = -3;
    private final int SUBMIT = -4;
    private final int BACKSPACE = -5;

    private EditText et;

    public void setEditable(EditText editable) {
        this.et = editable;
    }

    public MulKeyBoard(Context context) {
        super(context);
        init(context);
    }

    public MulKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public MulKeyBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.custom_input_view, this);

        mNumberKeyboard = new Keyboard(mContext, R.xml.custom_keyboard);
        mNumberView = findViewById(R.id.custom_keyboard);
        money = findViewById(R.id.money);
        setEditable(money);

        mNumberView.setKeyboard(mNumberKeyboard);
        mNumberView.setEnabled(true);
        mNumberView.setPreviewEnabled(false);
        mNumberView.setOnKeyboardActionListener(listener);
    }

    int start;
    Editable editable;
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
            }else{
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


}
