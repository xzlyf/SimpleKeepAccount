package com.xz.ska.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.xz.com.log.LogUtil;
import com.xz.ska.R;
import com.xz.ska.activity.home.AddActivity;
import com.xz.ska.activity.home.AnalyzeActivity;
import com.xz.ska.activity.setting.LockActivity;
import com.xz.ska.activity.setting.MySelfActivity;
import com.xz.ska.adapter.NewZhipeiAdapter;
import com.xz.ska.base.BaseActivity;
import com.xz.ska.constan.CurrencyData;
import com.xz.ska.constan.Local;
import com.xz.ska.constan.OldTypeShouru;
import com.xz.ska.constan.OldTypeZhichu;
import com.xz.ska.constan.TypeShouru;
import com.xz.ska.constan.TypeZhichu;
import com.xz.ska.custom.InfoTop;
import com.xz.ska.custom.SideRecyclerView;
import com.xz.ska.custom.TipsView;
import com.xz.ska.entity.CFAS;
import com.xz.ska.entity.TopInfo;
import com.xz.ska.entity.UpdateServer;
import com.xz.ska.sql.LitePalUtil;
import com.xz.ska.utils.DatePickerUtil;
import com.xz.ska.utils.SharedPreferencesUtil;
import com.xz.ska.utils.SpacesItemDecorationVertical;
import com.xz.ska.utils.TimeUtil;
import com.xz.ska.utils.UpdateListener;

import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private InfoTop topInfo;
    private SideRecyclerView recyclerMoney;
    private NewZhipeiAdapter adapter;
    private TipsView tipsView;
    private ImageView empty;
    private ImageButton addData;
    private Button myselfBtn;
    private Button cartogramBtn;
    private CFAS cfas;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void findID() {
        initView();
    }

    private void initView() {
        topInfo = findViewById(R.id.top_info);
        recyclerMoney = findViewById(R.id.recycler_money);
        tipsView = findViewById(R.id.tips_view);
        addData = findViewById(R.id.add_data);
        empty = findViewById(R.id.empty_view);
        myselfBtn = findViewById(R.id.myself_btn);
        cartogramBtn = findViewById(R.id.cartogram_btn);
        cartogramBtn.setOnClickListener(this);
        addData.setOnClickListener(this);
        myselfBtn.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Local.tipsSwitch) {
            tipsView.setVisibility(View.VISIBLE);
            tipsView.setTips(Local.tipsText);
        } else {
            tipsView.setVisibility(View.GONE);
        }
    }

    @Override
    public void init_Data() {
        if (!getIntent().getBooleanExtra("isSkip", false)) {
            //判断是否已设置过pass
            cfas = (CFAS) LitePalUtil.queryFirst(CFAS.class);
            if (cfas != null) {
                startActivity(new Intent(MainActivity.this, LockActivity.class).putExtra("isHome", true));
                finish();
            }

        }
        //初始化属性
        Local.tipsSwitch = SharedPreferencesUtil.getBoolean(this, "state", "tips_switch", false);
        Local.tipsText = SharedPreferencesUtil.getString(this, "state", "tips_text", "");
        //获取更新属性
        presenter.updateCheck();


        topInfo.setDateChoose(DatePickerUtil.getMonth() + 1 + "", +DatePickerUtil.getDay() + "");
        Local.moneySymbol = SharedPreferencesUtil.getString(this, "state", "money_symbol", "￥");
        TypeZhichu.initType(this);
        OldTypeZhichu.initType(this);
        OldTypeShouru.initType(this);
        TypeShouru.initType(this);
        CurrencyData.initType(this);

        init_Recycler();

        //日期选择器
        topInfo.setDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = year + "年" + (month + 1) + "月" + dayOfMonth + "日";

                LogUtil.w("已选择" + date);
                //获取指定日期的数据细节
                presenter.getDetailData(TimeUtil.getStringToDate(date, "yyyy年MM月dd日"));
                //跟新月份显示
                topInfo.setDateChoose(month + 1 + "", dayOfMonth + "");
                //设定日期选择器默认年月日为选定的年月日
//                DatePickerUtil.setmDay(dayOfMonth);
//                DatePickerUtil.setmMonth(month);
//                DatePickerUtil.setmYear(year);
            }
        });

        init_anim();
    }

    private Animation xuanzhaun;

    private void init_anim() {
        xuanzhaun = AnimationUtils.loadAnimation(this, R.anim.xuanzhuan);
    }

    private void init_Recycler() {
        recyclerMoney.setLayoutManager(new LinearLayoutManager(this));
        recyclerMoney.addItemDecoration(new SpacesItemDecorationVertical(10));
        adapter = new NewZhipeiAdapter(this);
        recyclerMoney.setAdapter(adapter);

        presenter.getDetailData(System.currentTimeMillis());//获取本地数据 今天的数据

        adapter.setUpdateListener(new UpdateListener() {
            @Override
            public void update(long time) {
                presenter.getDetailData(time);
            }
        });
    }

    @Override
    public void showData(Object object) {
        if (object instanceof List) {

            if (((List) object).size() == 0) {
                //数据空
                recyclerMoney.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            } else {
                empty.setVisibility(View.GONE);
                recyclerMoney.setVisibility(View.VISIBLE);
                adapter.refresh(((List) object));
            }
        } else if (object instanceof TopInfo) {
            topInfo.setTodayText(((TopInfo) object).getRi_zhipei() + "");
            topInfo.setZhiChu(((TopInfo) object).getYue_zhichu() + "");
            topInfo.setShouRu(((TopInfo) object).getYue_shouru() + "");
        } else if (object instanceof UpdateServer) {
            //填装更新数据
            Local.level = ((UpdateServer) object).getLevel();
            Local.name = ((UpdateServer) object).getName();
            Local.code = ((UpdateServer) object).getCode();
            Local.msg = ((UpdateServer) object).getMsg();
            Local.link = ((UpdateServer) object).getLink();

        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_data:
                addData.startAnimation(xuanzhaun);
                startActivity(new Intent(this, AddActivity.class));
                overridePendingTransition(R.anim.push_in_addactivity, R.anim.no_anim);
                break;
            case R.id.myself_btn:
                startActivity(new Intent(this, MySelfActivity.class));
                overridePendingTransition(R.anim.push_in_myselfacticity, R.anim.push_out_mainactivity);
                break;
            case R.id.cartogram_btn:
                startActivity(new Intent(this, AnalyzeActivity.class));
                break;

        }
    }
}
