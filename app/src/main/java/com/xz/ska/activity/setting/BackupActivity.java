package com.xz.ska.activity.setting;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xz.ska.R;
import com.xz.ska.base.BaseActivity;
import com.xz.ska.custom.RecyclerDialog;
import com.xz.ska.entity.Book;
import com.xz.ska.sql.LitePalUtil;
import com.xz.ska.utils.TimeUtil;

import java.util.List;

public class BackupActivity extends BaseActivity implements View.OnClickListener {


    private ImageView back;
    private TextView totalNumber;
    private TextView logText;
    private Button exportBtn;
    private Button importBtn;
    private List<Book> allDate;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_backup;
    }

    @Override
    public void findID() {
        initView();
    }

    private void initView() {
        back = findViewById(R.id.back);
        totalNumber = findViewById(R.id.total_number);
        logText = findViewById(R.id.log_text);
        exportBtn = findViewById(R.id.export_btn);
        importBtn = findViewById(R.id.import_btn);
        back.setOnClickListener(this);
        exportBtn.setOnClickListener(this);
        importBtn.setOnClickListener(this);
    }

    @Override
    public void init_Data() {
        allDate = LitePalUtil.queryBookAll();
        totalNumber.setText("到目前为止已经记录了" + allDate.size() + "条账目");

    }

    @Override
    public void showData(Object object) {
        if (object instanceof Boolean){
            if ((boolean)object){
                logTool("导出成功");
            }else{
                logTool("导出失败");
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.export_btn:
                presenter.export2Excel(allDate);
                break;
            case R.id.import_btn:
                showSelectDialog();
                break;
        }
    }

    private void showSelectDialog() {
//        List<Book> mlist = ExcelUtil.ReadExcel(getExternalFilesDir("backups").toString()+"/1566974377072.xls");


        RecyclerDialog dialog = new RecyclerDialog(this,R.style.base_dialog);
        dialog.create();
        dialog.show();
    }

    private void logTool(String msg){
        logText.append(TimeUtil.getSimDate("yyyy-MM-dd HH:mm:ss",System.currentTimeMillis()) +msg+'\n');
    }

}
