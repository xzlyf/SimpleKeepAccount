package com.xz.ska.custom;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.xz.ska.R;

public class NumberkeyBoard extends KeyboardView implements KeyboardView.OnKeyboardActionListener {
    public static final int KEY_EMPTY = -10;
    private Context context;
    private KeyboardView mNumberView;//数字键盘view
    private Keyboard mNumberKeyboard;//数字键盘

    public NumberkeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }


    public NumberkeyBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        mNumberKeyboard = new Keyboard(context,R.xml.custom_keyboard);
//        mNumberView = findViewById()
        mNumberView.setKeyboard(mNumberKeyboard);
        mNumberView.setEnabled(true);
        mNumberView.setPreviewEnabled(false);
        mNumberView.setOnKeyboardActionListener(this);
    }


    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {

    }

    @Override
    public void onText(CharSequence charSequence) {

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
}
