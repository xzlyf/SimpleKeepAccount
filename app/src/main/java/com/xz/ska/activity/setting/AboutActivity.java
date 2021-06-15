package com.xz.ska.activity.setting;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xz.ska.R;
import com.xz.ska.base.BaseActivity;
import com.xz.ska.constan.Local;
import com.xz.ska.utils.AppInfoUtils;
import com.xz.ska.utils.CopyUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {
    @BindView(R.id.image_launch)
    ImageView imageLaunch;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    private CopyUtil copyUtil;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_about;
    }

    @Override
    public void findID() {
    }

    @Override
    public void init_Data() {
        setTitle("关于");
    }

    @Override
    public void showData(Object object) {
        copyUtil = new CopyUtil(mContext);
        //获取app图标
        //Drawable appIcon = AppInfoUtils.getAppIcon(mContext, Local.PACKAGE_NAME);
        //if (appIcon != null) {
        //	imageLaunch.setImageDrawable(appIcon);
        //}
        imageLaunch.setImageResource(R.drawable.logo_max);
        //获取版本
        String appVersion = AppInfoUtils.getAppVersion(mContext, Local.PACKAGE_NAME);
        if (appVersion != null) {
            tvVersion.setText(String.format("v%s", appVersion));
        }
    }

    @OnClick(R.id.tv_jump)
    public void onViewClick(View v) {
        toWeChat();
    }

    public void toWeChat() {
        try {
            copyUtil.copyToClicp(Local.WeChat);
            mToast(Local.WeChat + "已复制到粘贴板");
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            startActivity(intent);
        } catch (Exception e) {
            if (e instanceof ActivityNotFoundException) {
                mToast("未找到微信，请手动前往搜索");
            } else {
                e.printStackTrace();
            }
        }

    }

}
