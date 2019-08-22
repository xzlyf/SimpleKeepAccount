package com.xz.ska.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xz.ska.R;
import com.xz.ska.constan.Local;
import com.xz.ska.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class ZhipeiAdapter extends RecyclerView.Adapter<ZhipeiAdapter.ViewHolder> {
    private final Context context;
    private List<Book> list;

    public ZhipeiAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void refresh(List<Book> newData) {
        list.clear();
        list.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_zhipei, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.idTypeImg.setImageResource(R.drawable.id_gouwu);
        viewHolder.idTypeName.setText(list.get(i).getType() + "（测试）");
        viewHolder.moneyText.setText(list.get(i).getMoney() + Local.moneySymbol);
        if (list.get(i).getRemarks().equals("")) {
            viewHolder.remark.setVisibility(View.GONE);
        } else {
            viewHolder.remark.setText(list.get(i).getRemarks());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {
        ImageView idTypeImg;
        TextView idTypeName;
        TextView remark;
        TextView moneyText;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idTypeImg = itemView.findViewById(R.id.id_type_img);
            idTypeName = itemView.findViewById(R.id.id_type_name);
            remark = itemView.findViewById(R.id.remark);
            moneyText = itemView.findViewById(R.id.money_text);

            itemView.setOnTouchListener(this);

        }


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
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
