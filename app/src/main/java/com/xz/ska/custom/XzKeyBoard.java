package com.xz.ska.custom;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.widget.EditText;

import com.xz.ska.R;

public class XzKeyBoard {
    private Context context;
    private View parentView;
    private KeyboardView numberView;//数字键盘view
    private Keyboard numberKeyboard;//数字键盘
    private EditText mEditText;
    private View headerView;

    public void setEditText(EditText text) {
        mEditText = text;
    }
    public XzKeyBoard(Activity context,View view){
        this.context = context;
        parentView = view;

        numberKeyboard = new Keyboard(context, R.xml.custom_keyboard);

    }
}
