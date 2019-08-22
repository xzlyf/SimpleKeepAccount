package com.xz.ska;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xz.com.log.LogUtil;
import com.xz.ska.constan.Local;
import com.xz.ska.constan.TypeShouru;
import com.xz.ska.constan.TypeZhichu;
import com.xz.ska.entity.Book;
import com.xz.ska.sql.LitePalUtil;
import com.xz.ska.utils.TimeUtil;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private ImageView selectType;
    private TextView nameType;
    private TextView moneyText;
    private TextView dateText;
    private TextView remarksText;
    private Button delete;
    private Button edit;

    private Book book;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        book = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        book = (Book) getIntent().getSerializableExtra("book");

        initView();
    }

    private void initView() {

        back = findViewById(R.id.back);
        selectType = findViewById(R.id.select_type);
        nameType = findViewById(R.id.name_type);
        moneyText = findViewById(R.id.money_text);
        dateText = findViewById(R.id.date_text);
        remarksText = findViewById(R.id.remarks_text);
        delete = findViewById(R.id.delete);
        edit = findViewById(R.id.edit);
        delete.setOnClickListener(this);
        edit.setOnClickListener(this);
        back.setOnClickListener(this);

        if (book.getState()==0){
            selectType.setImageResource(TypeZhichu.getIcon(book.getType()));
            nameType.setText(TypeZhichu.getName(book.getType()));
            moneyText.setText("-"+book.getMoney()+ Local.moneySymbol);
        }else{
            selectType.setImageResource(TypeShouru.getIcon(book.getType()));
            nameType.setText(TypeShouru.getName(book.getType()));
            moneyText.setText(book.getMoney()+ Local.moneySymbol);
        }
        remarksText.setText(book.getRemarks());
        dateText.setText(TimeUtil.getSimDate("yyyy年MM月dd日 HH:mm",book.getTimeStamp()));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.delete:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                dialog.create();
                dialog.setTitle("是否删除");
                dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogUtil.w("删除" + book.getMoney());
                        long time = book.getTimeStamp();
                        //在数据库删除
                        LitePalUtil.deleteBook(time);
                        //回到首页 -重新刷新本地数据
                        startActivity(new Intent(DetailActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                });
                dialog.setPositiveButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                dialog.show();
                break;
            case R.id.edit:
                break;
        }
    }
}
