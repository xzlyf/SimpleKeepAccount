package com.xz.ska;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xz.com.log.LogUtil;
import com.xz.ska.base.BaseActivity;
import com.xz.ska.constan.OldTypeZhichu;
import com.xz.ska.constan.TypeZhichu;
import com.xz.ska.entity.Category;
import com.xz.ska.sql.LitePalUtil;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends BaseActivity {


    private android.widget.ImageView back;
    private RecyclerView systemIconRecycler;
    private RecyclerView userIconRecycler;
    private TextView tips;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_category;
    }

    @Override
    public void findID() {
        initView();
    }

    @Override
    public void init_Data() {
//        增加数据
//        Category category = new Category();
//        category.setIcon(R.drawable.id_gouwu);
//        category.setName("购物");
//        category.save();

        init_recycler();
    }

    private void init_recycler() {
        //系统自带图标=========================
        List<Integer> sys_data = new ArrayList<>();
        for (int i = 0; i < OldTypeZhichu.getLength(); i++) {
            sys_data.add(i);
        }
        AddTypeAdapter sys_adapter = new AddTypeAdapter(this,sys_data);
        systemIconRecycler.setLayoutManager(new LinearLayoutManager(this));
        systemIconRecycler.setAdapter(sys_adapter);
        //用户添加
//        List<Category> user_data = (List<Category>) LitePalUtil.queryAll(Category.class);
//        AddTypeAdapter user_adapter = new AddTypeAdapter(this,user_data);
//        userIconRecycler.setLayoutManager(new LinearLayoutManager(this));
//        userIconRecycler.setAdapter(user_adapter);

    }

    @Override
    public void showData(Object object) {

    }

    private void initView() {
        back = findViewById(R.id.back);
        systemIconRecycler = findViewById(R.id.system_icon_recycler);
        userIconRecycler = findViewById(R.id.user_icon_recycler);
        tips = findViewById(R.id.tips);
    }


    class AddTypeAdapter extends RecyclerView.Adapter<AddTypeAdapter.ViewHolder> {

        private Context mContext;
        private List<Integer> mlist = new ArrayList<>();


        public AddTypeAdapter(Context context, List<Integer> list) {
            mContext = context;
            mlist.addAll(list);
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(CategoryActivity.this).inflate(R.layout.item_add_type, viewGroup, false));

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.addIcon.setImageResource(TypeZhichu.getIcon(i));
            viewHolder.addName.setText(TypeZhichu.getName(i));
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView addIcon;
            TextView addName;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                addIcon = itemView.findViewById(R.id.add_icon);
                addName = itemView.findViewById(R.id.add_name);
            }
        }
    }
}
