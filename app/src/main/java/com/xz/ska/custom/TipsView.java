package com.xz.ska.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xz.ska.R;

/**
 * 跑马灯提示提示控件
 * 完整的自定义控件
 */
public class TipsView extends LinearLayout {
    // 命名空间，在引用这个自定义组件的时候，需要用到
    private String namespace = "http://schemas.android.com/apk/res/com.xz.TipsView";
    //Attrs属性
    private String msg;
    private int icon;
    private int backgroundRes;
    private boolean status;


    private Context context;
    private View view;
    private ImageView icTips;
    private TextView tips;
    private LinearLayout mainLayout;
    private TextView tipsText;

    public TipsView(Context context) {
        super(context);
    }

    public TipsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = View.inflate(context, R.layout.custom_tips, this);
        this.context = context;
        init(attrs);
    }

    public TipsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = View.inflate(context, R.layout.custom_tips, this);
        this.context = context;
        init(attrs);

    }


    private void init(AttributeSet attrs) {
        initView();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TipsView);

        msg = a.getString(R.styleable.TipsView_textTips);
        icon = a.getResourceId(R.styleable.TipsView_iconTips, R.drawable.ic_tips);
        status = a.getBoolean(R.styleable.TipsView_hideIcon, false);
        backgroundRes = a.getResourceId(R.styleable.TipsView_background, 0);
        setTips(msg);
        setIcon(icon);
        setIconStatus(status);
        setBackground(backgroundRes);
    }

    private void initView() {
        icTips = view.findViewById(R.id.ic_tips);
        tips = view.findViewById(R.id.tips_text);
        //跑马灯效果
        tips.setSelected(true);
        mainLayout = findViewById(R.id.main_layout);
        tipsText = findViewById(R.id.tips_text);
    }

    public void setBackground(int res) {
        mainLayout.setBackgroundResource(res);
    }

    /**
     * 设置内容，不可超过255长度，超出内容不保留
     *
     * @param text
     */
    public void setTips(String text) {
        if (text == null) {
            tips.setText(" ");
            return;
        }
        if (text.length() > 255)
            text.substring(0, 255);

        tips.setText(text);

    }

    /**
     * 获取内容
     *
     * @return
     */
    public String getTips() {
        return tips.getText().toString();
    }

    /**
     * 设置icon
     *
     * @param drawable
     */
    public void setIcon(int drawable) {
        icTips.setImageResource(drawable);
    }

    /**
     * 设置icon的状态
     * 显示/隐藏
     *
     * @param status
     */
    public void setIconStatus(boolean status) {
        if (status) {
            icTips.setVisibility(VISIBLE);
        } else {
            icTips.setVisibility(GONE);
        }
    }
}
