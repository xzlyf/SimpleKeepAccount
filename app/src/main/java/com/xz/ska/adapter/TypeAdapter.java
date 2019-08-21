package com.xz.ska.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xz.com.log.LogUtil;
import com.xz.ska.R;
import com.xz.ska.utils.OnClickItemListener;
import com.xz.ska.utils.TypeUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {
    private Context context;
    private List<Integer> type;
    private OnClickItemListener listener;
    public TypeAdapter(Context context){
        this.context = context;
        type = new ArrayList<>();
    }
    public void refresh(List<Integer> type){
        this.type.addAll(type);
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(OnClickItemListener listener){
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_type ,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onClick(i);
                }
            }
        });
        viewHolder.icon.setImageResource(TypeUtil.getIcon(i));
        viewHolder.name.setText(TypeUtil.getName(i));
    }

    @Override
    public int getItemCount() {
        return type.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener{
        ImageView icon;
        TextView name;
        View view;
        public ViewHolder( View itemView) {
            super(itemView);
            view = itemView;
            icon = itemView.findViewById(R.id.id_type);
            name = itemView.findViewById(R.id.id_name);
            itemView.setOnTouchListener(this);
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    view.setScaleX(1.1f);
                    view.setScaleY(1.1f);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    view.setScaleX(1.0f);
                    view.setScaleY(1.0f);
                    break;
                case MotionEvent.ACTION_UP:
                    view.setScaleX(1.0f);
                    view.setScaleY(1.0f);
                    break;

            }
            return false;
        }
    }
}
