package com.xz.ska.custom;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.xz.com.log.LogUtil;
import com.xz.ska.MainActivity;
import com.xz.ska.R;
import com.xz.ska.constan.Local;
import com.xz.ska.constan.TypeShouru;
import com.xz.ska.sql.LitePalUtil;
import com.xz.ska.utils.DatePickerUtil;
import com.xz.ska.utils.TimeUtil;
import com.xz.ska.constan.TypeZhichu;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class MulKeyBoardDialog extends Dialog {


    private Context mContext;
    private KeyboardView mNumberView;   //数字键盘View
    private Keyboard mNumberKeyboard;   // 数字键盘
    private EditText money;// 金额
    private EditText remarks;//备注
    private TextView wordCount;//字数
    private TextView time;//时间
    private TextView date;//日期
    private ImageView selectType;//选中的图标
    private ImageView back;
    private long timeStamp;//时间戳

    private final int DATE_SELECT = -1;
    private final int ADD = 43;
    private final int SUB = 45;
    private final int SUBMIT = -4;
    private final int BACKSPACE = -5;

    private EditText et;

    private boolean isSubmit = false;//是否提交
    private int type;

    public void setEditable(EditText editable) {
        this.et = editable;
    }


    public MulKeyBoardDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;


    }

    public void setIcon(int type) {
        this.type = type;
        if (Local.state == 0) {
            selectType.setImageResource(TypeZhichu.getIcon(type));
        } else {
            selectType.setImageResource(TypeShouru.getIcon(type));
        }

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
        time = findViewById(R.id.time_text);
        date = findViewById(R.id.date_text);
        back = findViewById(R.id.back);
        setEditable(money);


        mNumberView.setKeyboard(mNumberKeyboard);
        mNumberView.setEnabled(true);
        mNumberView.setPreviewEnabled(false);
        mNumberView.setOnKeyboardActionListener(listener);

        remarks.addTextChangedListener(textWatcher);

        timeStamp = System.currentTimeMillis();//获取当前系统时间
        date.setText(TimeUtil.getSimDate("yyyy年MM月dd日", timeStamp));
        time.setText(TimeUtil.getSimDate("HH:mm", timeStamp));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


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

                submit();

            } else if (i == DATE_SELECT) {
                selectDate();
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
            if (wordCount.getVisibility() == View.GONE) {
                wordCount.setVisibility(View.VISIBLE);
            }
            if (remarks.length() == 0) {
                wordCount.setVisibility(View.GONE);
            }
            wordCount.setText(remarks.length() + "/100");
        }
    };

    /**
     * 提交
     */
    private void submit() {
        double qm;
        String remark;
        try {
            qm = Double.valueOf(et.getText().toString());
            BigDecimal b = new BigDecimal(qm);
            qm = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            remark = remarks.getText().toString();
        } catch (Exception e) {
            Toast.makeText(mContext, "数值异常，请输入正确的数值", Toast.LENGTH_SHORT).show();
            et.setText("");
            return;
        }
        if (qm == 0) {
            Toast.makeText(mContext, "该金额记不了帐哦", Toast.LENGTH_SHORT).show();
            return;
        }

        //保存到数据库
        LitePalUtil.saveBook(timeStamp, qm, remark, type, Local.state);
        Toast.makeText(mContext, "记账成功", Toast.LENGTH_SHORT).show();
        //回到首页
        mContext.startActivity(new Intent(mContext, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    /**
     * 日期选择器
     */
    private void selectDate() {
        DatePickerUtil.showDatePicker(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final String d = year + "年" + (month + 1) + "月" + dayOfMonth + "日";
                timeStamp = TimeUtil.getStringToDate(d, "yyyy年MM月dd日");
                date.setText(d);
                date.setTextColor(mContext.getResources().getColor(R.color.colorAccent));

                DatePickerUtil.showTimePicker(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String da = hourOfDay + ":" + minute ;
                        String target = d+da;
                        timeStamp=TimeUtil.getStringToDate(target,"yyyy年MM月dd日HH:mm");
                        time.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                        time.setText(da);

                    }
                });
            }
        });
    }


    /**
     * 是否整数
     *
     * @param str
     * @return
     */
    private boolean isInteger(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\\\+]?[\\\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 是否浮点数
     *
     * @param str
     * @return
     */
    private boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$"); // 之前这里正则表达式错误，现更正
        return pattern.matcher(str).matches();
    }

}
