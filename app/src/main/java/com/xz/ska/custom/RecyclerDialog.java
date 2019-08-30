package com.xz.ska.custom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.xz.ska.R;
import com.xz.ska.utils.OnClickItemListener;
import com.xz.ska.utils.TimeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 列表对话框
 */
public class RecyclerDialog extends Dialog {
    private Context mContext;
    private ImageView tvRefresh;
    private ImageView emptyView;
    private RecyclerView recyceler;
    private Button closeBtn;
    private Animation xuanzhaun;
    private XzRecyclerAdapter adapter;
    private OnClickItemListener mlistener;


    public RecyclerDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater1 = LayoutInflater.from(mContext);
        View view1 = inflater1.inflate(R.layout.dialog_recycler_list, null);
        setContentView(view1);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8);
        lp.height = (int) (d.heightPixels * 0.6);
        lp.dimAmount = 0.5f;//背景不变暗
        dialogWindow.setAttributes(lp);

        initAnim();
        initView();
        initRecycler();
    }

    private void initRecycler() {
        adapter = new XzRecyclerAdapter(mContext);
        recyceler.setLayoutManager(new LinearLayoutManager(mContext));
        recyceler.setAdapter(adapter);
    }

    public void refresh(File[] list) {
        if (list.length == 0) {
            emptyView.setVisibility(View.VISIBLE);
            recyceler.setVisibility(View.GONE);
            return;
        } else {
            emptyView.setVisibility(View.GONE);
            recyceler.setVisibility(View.VISIBLE);

        }
//        List<String> mlist = new ArrayList<>();
//        for (File file :list){
//            mlist.add(file.getName());
//        }
//        adapter.refresh(mlist);
        adapter.refresh(list);

    }


    private void initAnim() {
        xuanzhaun = AnimationUtils.loadAnimation(mContext, R.anim.xuanzhuan_fan);
    }

    private void initView() {
        tvRefresh = findViewById(R.id.tv_refresh);
        recyceler = findViewById(R.id.recyceler);
        closeBtn = findViewById(R.id.close_btn);
        emptyView = findViewById(R.id.empty_view);

        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvRefresh.startAnimation(xuanzhaun);
            }

        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
    public void setOnItemClickListener(OnClickItemListener listener){
        mlistener = listener;
    }


    /**
     * 适配器
     */
    class XzRecyclerAdapter extends RecyclerView.Adapter<XzRecyclerAdapter.ViewHolder> {
        private Context mContext;
        private List<String> mlist = new ArrayList<>();
        private File[] files;


        public XzRecyclerAdapter(Context context) {
            mContext = context;

        }

        public void refresh(File[] list) {
            mlist.clear();
            addAll(list);
            files = list;

        }

        private void addAll(File[] files) {
            for (File file : files) {
                mlist.add(file.getName());
            }
        }

        @NonNull
        @Override
        public XzRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            return new XzRecyclerAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_currency, viewGroup, false));

        }

        @Override
        public void onBindViewHolder(@NonNull XzRecyclerAdapter.ViewHolder viewHolder, int i) {
            viewHolder.nameCurrency.setText("备份日期：" + TimeUtil.getSimDate("yyyy_MM_dd HH:mm:ss", Long.valueOf((mlist.get(i).split(".xls"))[0])));
            viewHolder.symbolCurrency.setText(mlist.get(i));

        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }



        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener
                                                                        ,View.OnLongClickListener
                                                                        ,View.OnClickListener {
            TextView nameCurrency;
            TextView symbolCurrency;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                nameCurrency = itemView.findViewById(R.id.name_currency);
                symbolCurrency = itemView.findViewById(R.id.symbol_currency);
                symbolCurrency.setTextSize(14f);
                itemView.setOnTouchListener(this);
                itemView.setOnLongClickListener(this);
                itemView.setOnClickListener(this);
            }


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundColor(mContext.getColor(R.color.colorPrimary));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        v.setBackgroundColor(mContext.getColor(R.color.white));
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setBackgroundColor(mContext.getColor(R.color.white));
                        break;
                }
                return false;
            }

            @Override
            public boolean onLongClick(View v) {
                PopupMenu menu = new PopupMenu(mContext, v);
                menu.setGravity(Gravity.CENTER);
                menu.getMenuInflater().inflate(R.menu.popu_menu_delete, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (!files[getLayoutPosition()].delete()){
                            Toast.makeText(mContext, "删除失败，请稍后重试", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        mlist.remove(getLayoutPosition());
                        notifyDataSetChanged();
                        if (mlist.size()==0){
                            emptyView.setVisibility(View.VISIBLE);
                            recyceler.setVisibility(View.GONE);
                        }
                        return false;
                    }
                });
                menu.show();


                return true;
            }

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.create();
                builder.setTitle("警告");
                builder.setMessage("是否恢复备份的账单（"+mlist.get(getLayoutPosition())
                        +"），将会删除原先的账单并替换，此操作不可逆，请慎重选择");
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mlistener.onClick(0,mlist.get(getLayoutPosition()));

                    }
                });
                builder.show();
            }
        }

    }
}

