package com.xz.ska.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.xz.ska.R;
import com.xz.ska.presenter.Presenter;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {
	protected final String TAG = this.getClass().getSimpleName();
	protected Context mContext;
	public Presenter presenter;
	//标识
	private final int dataCallback = 1002;
	private final int toast_bs = 1003;
	private final int show_d = 1004;
	private final int hide_d = 1005;
	private final int show_l = 1006;
	private final int hide_l = 1007;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				//showData()回调
				case dataCallback:
					showData(msg.obj);
					break;
				case toast_bs:
					mToast_handler(msg.obj.toString());
					break;
				case show_d:
					break;
				case hide_d:
					break;
				case show_l:
					show_load_handler();
					break;
				case hide_l:
					dismiss_l_handler();
					break;

			}
		}
	};


	/**
	 * 异步线程调用
	 * 把数据回到主线
	 * 回到主线程封装
	 */
	public void backToUi(Object object) {
		//通过handler.obtainMessage()可以减少内存的使用
		Message msg = handler.obtainMessage();
		msg.what = dataCallback;
		msg.obj = object;
		handler.sendMessage(msg);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(getLayoutResource());
		ButterKnife.bind(this);

		//代替toolbar
		ImageView back = findViewById(R.id.toolbar_back);
		if (back != null) {
			back.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
		TextView title = findViewById(R.id.toolbar_title);
		if (title != null) {
			title.setText(getTitle());
		}

		findID();
		init();

	}


	private void init() {
		presenter = new Presenter(this);
		init_Data();

		//创建一个HomeUp按钮
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	/**
	 * homeUp按钮
	 *
	 * @param item
	 * @return
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;
		}
		return true;
	}

	/**
	 * 权限结果回调
	 *
	 * @param requestCode
	 * @param permissions
	 * @param grantResults
	 */
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1) {
			for (int i = 0; i < permissions.length; i++) {
				if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
					Toast.makeText(this, "" + "权限" + permissions[i] + "申请成功", Toast.LENGTH_SHORT).show();
					init();
				} else {
					Toast.makeText(this, "" + "权限" + permissions[i] + "申请失败", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	public abstract int getLayoutResource();

	public abstract void findID();

	public abstract void init_Data();

	public abstract void showData(Object object);


	//常用方法
	private Toast mToast;
	private ProgressDialog progressDialog;

	/**
	 * 子类调用
	 * 常用方法
	 *
	 * @param text
	 */
	public void mToast(String text) {
		Message msg = handler.obtainMessage();
		msg.what = toast_bs;
		msg.obj = text;
		handler.sendMessage(msg);
	}

	/**
	 * 子类调用
	 * 显示加载框
	 */
	public void showLoading() {
		Message msg = handler.obtainMessage();
		msg.what = show_l;
		handler.sendMessage(msg);
	}

	/**
	 * 子类调用
	 * 销毁加载框
	 */
	public void dismissLoading() {
		Message msg = handler.obtainMessage();
		msg.what = hide_l;
		handler.sendMessage(msg);
	}

	/**
	 * Handler执行
	 * 回到主线
	 *
	 * @param text
	 */
	private void mToast_handler(String text) {
		if (mToast == null) {
			mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}


	/**
	 * Handle调用
	 * 显示加载框
	 */
	private void show_load_handler() {
		progressDialog = new ProgressDialog(mContext);
		progressDialog.setTitle("加载中");
		progressDialog.setMessage("稍等片刻!");
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	/**
	 * Handler调用
	 * 销毁加载框
	 */
	private void dismiss_l_handler() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

}
