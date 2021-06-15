package com.xz.ska.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.xz.ska.R;
import com.xz.ska.activity.home.DetailActivity;
import com.xz.ska.constan.Local;
import com.xz.ska.entity.Book;
import com.xz.ska.sql.LitePalUtil;
import com.xz.ska.utils.UpdateListener;

import java.util.ArrayList;
import java.util.List;

public class NewZhipeiAdapter extends RecyclerView.Adapter<NewZhipeiAdapter.SlideViewHolder> {
    private final Context context;
    private List<Book> list;
    private UpdateListener listener;

    public NewZhipeiAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void refresh(List<Book> newData) {
        list.clear();
        list.addAll(newData);
        notifyDataSetChanged();
    }

    public void setUpdateListener(UpdateListener listener) {
        this.listener = listener;

    }


    @NonNull
    @Override
    public NewZhipeiAdapter.SlideViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_zhipei_new, viewGroup, false);
        return new SlideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewZhipeiAdapter.SlideViewHolder viewHolder, int i) {

        final Book book = list.get(i);

        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                dialog.create();
                dialog.setTitle("是否删除");
                dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Logger.d("删除" + book.getMoney());
                        long time = book.getTimeStamp();
                        //在数据库删除
                        LitePalUtil.deleteBook(time);
                        //重新刷新本地数据
                        listener.update(time);
                        Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setPositiveButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                dialog.show();


            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, DetailActivity.class).putExtra("book", book));

            }
        });
        if (list.get(i).getState() == 0) {
            viewHolder.idTypeImg.setImageResource(book.getType());
            viewHolder.idTypeName.setText(book.getTitle());
            viewHolder.moneyText.setText("-" + book.getMoney() + Local.moneySymbol);

        } else {
            viewHolder.idTypeImg.setImageResource(book.getType());
            viewHolder.idTypeName.setText(book.getTitle());
            viewHolder.moneyText.setText(book.getMoney() + Local.moneySymbol);

        }

        if (list.get(i).getRemarks().equals("")) {
            viewHolder.remark.setVisibility(View.GONE);
        } else {
            viewHolder.remark.setText(list.get(i).getRemarks());
        }
    }

    public int getItemCount() {
        return list.size();
    }


    public class SlideViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {
        ImageView idTypeImg;
        TextView idTypeName;
        TextView remark;
        TextView moneyText;
        TextView tvDelete;

        private SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            idTypeImg = itemView.findViewById(R.id.id_type_img);
            idTypeName = itemView.findViewById(R.id.id_type_name);
            remark = itemView.findViewById(R.id.remark);
            moneyText = itemView.findViewById(R.id.money_text);
            tvDelete = itemView.findViewById(R.id.tv_delete);


        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //点击放大效果
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setScaleX(1.1f);
                    v.setScaleY(1.1f);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    v.setScaleX(1.0f);
                    v.setScaleY(1.0f);
                    break;
                case MotionEvent.ACTION_UP:
                    v.setScaleX(1.0f);
                    v.setScaleY(1.0f);
                    break;

            }
            return true;
        }

    }
}
