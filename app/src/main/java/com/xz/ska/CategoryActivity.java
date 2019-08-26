package com.xz.ska;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.xz.ska.base.BaseActivity;
import com.xz.ska.constan.Local;
import com.xz.ska.constan.OldTypeShouru;
import com.xz.ska.constan.OldTypeZhichu;
import com.xz.ska.constan.TypeShouru;
import com.xz.ska.constan.TypeZhichu;
import com.xz.ska.entity.Category;
import com.xz.ska.sql.LitePalUtil;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends BaseActivity implements View.OnClickListener {


    private ImageView back;
    private ImageView add;
    private RecyclerView systemIconRecycler;
    private AddTypeAdapter sys_adapter;
    private RecyclerView userIconRecycler;
    private UserAddTypeAdapter user_adapter;
    private TextView tips;
    private TextView zhichuRecycler;
    private TextView shouruRecycler;

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

        Local.state = 0;
        init_recycler();
        setSelect(zhichuRecycler, true);
        setSelect(shouruRecycler, false);
    }

    private void init_recycler() {
        sys_adapter = new AddTypeAdapter(this);
        systemIconRecycler.setLayoutManager(new LinearLayoutManager(this));
        systemIconRecycler.setAdapter(sys_adapter);

        user_adapter = new UserAddTypeAdapter(this);
        userIconRecycler.setLayoutManager(new LinearLayoutManager(this));
        userIconRecycler.setItemAnimator(new DefaultItemAnimator());
        userIconRecycler.setAdapter(user_adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Local.state==0){
            getZhichuList();
        }else{
            getShouruList();
        }
    }

    @Override
    public void showData(Object object) {

    }

    private void initView() {
        back = findViewById(R.id.back);
        add = findViewById(R.id.add_type);
        back.setOnClickListener(this);
        add.setOnClickListener(this);
        systemIconRecycler = findViewById(R.id.system_icon_recycler);
        userIconRecycler = findViewById(R.id.user_icon_recycler);
        tips = findViewById(R.id.tips);
        zhichuRecycler = findViewById(R.id.zhichu_recycler);
        shouruRecycler = findViewById(R.id.shouru_recycler);
        zhichuRecycler.setOnClickListener(this);
        shouruRecycler.setOnClickListener(this);
    }

    private void setSelect(TextView view, boolean b) {
        if (b) {
            view.setScaleX(1.3f);
            view.setScaleY(1.3f);
        } else {
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
        }
    }

    /**
     * 获取支出数据列表
     */
    private void getZhichuList() {
        //系统自带图标=========================
        List<Integer> sys_data = new ArrayList<>();
        for (int i = 0; i < OldTypeZhichu.getLength(); i++) {
            sys_data.add(i);
        }
        sys_adapter.refresh(sys_data);

        //用户添加=============================
        List<Category> user_data = (List<Category>) LitePalUtil.queryAll(Category.class);
        user_adapter.refresh(user_data);

    }

    /**
     * 获取收入列表
     */
    private void getShouruList() {
        //系统自带图标============================
        List<Integer> sys_data = new ArrayList<>();
        for (int i = 0; i < OldTypeShouru.getLength(); i++) {
            sys_data.add(i);
        }
        sys_adapter.refresh(sys_data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.add_type:
                startActivity(new Intent(CategoryActivity.this,CategoryAddActivity.class));
                break;
            case R.id.zhichu_recycler:
                if (Local.state != 0) {
                    Local.state = 0;
                    setSelect(zhichuRecycler, true);
                    setSelect(shouruRecycler, false);
                    getZhichuList();
                }
                break;
            case R.id.shouru_recycler:
                if (Local.state != 1) {
                    Local.state = 1;
                    setSelect(shouruRecycler, true);
                    setSelect(zhichuRecycler, false);
                    getShouruList();
                }
                break;
        }
    }


    /**
     * 系统自带
     * 暂时不可删除
     */
    class AddTypeAdapter extends RecyclerView.Adapter<AddTypeAdapter.ViewHolder> {


        private Context mContext;
        private List<Integer> mlist = new ArrayList<>();


        public AddTypeAdapter(Context context) {
            mContext = context;

        }

        private void refresh(List<Integer> list) {
            mlist.clear();
            mlist.addAll(list);
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(CategoryActivity.this).inflate(R.layout.item_add_type, viewGroup, false));

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            if (Local.state==0){
                viewHolder.addIcon.setImageResource(TypeZhichu.getIcon(i));
                viewHolder.addName.setText(TypeZhichu.getName(i));
            }else{
                viewHolder.addIcon.setImageResource(TypeShouru.getIcon(i));
                viewHolder.addName.setText(TypeShouru.getName(i));
            }

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

    /**
     * 用户自设
     * 可直接删除
     * 删除本地数据库即可
     */
    class UserAddTypeAdapter extends RecyclerView.Adapter<UserAddTypeAdapter.ViewHolder> {

        private Context mContext;
        private List<Category> mlist = new ArrayList<>();


        public UserAddTypeAdapter(Context context) {
            mContext = context;
        }

        public void refresh(List<Category> list) {
            mlist.clear();
            mlist.addAll(list);
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(CategoryActivity.this)
                    .inflate(R.layout.item_add_type, viewGroup, false));

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            /**
             * 为社么要加上22
             * 因为有22个type是默认的系统自带的
             * 而用户自设的type也是从0开始计算
             * 如果根据0开始计算的值来找图标找到的是系统自带的图标
             * 所以要加上22个默认图标
             * TypeZhichu初始化时会加上用户自设的值到尾部
             */
            viewHolder.addIcon.setImageResource(TypeZhichu.getIcon(i + 22));
            viewHolder.addName.setText(TypeZhichu.getName(i + 22));
        }

        @Override
        public int getItemCount() {
            if (mlist.size() == 0) {
                tips.setVisibility(View.GONE);
            } else {
                tips.setVisibility(View.VISIBLE);
            }
            return mlist.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
            ImageView addIcon;
            TextView addName;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                addIcon = itemView.findViewById(R.id.add_icon);
                addName = itemView.findViewById(R.id.add_name);
                itemView.setOnLongClickListener(this);
            }

            @Override
            public boolean onLongClick(View v) {
                PopupMenu menu = new PopupMenu(mContext, v);
                menu.setGravity(Gravity.CENTER);
                menu.getMenuInflater().inflate(R.menu.popu_menu_delete, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(mContext, "使用该类型的账单会显示“未定义”，请及时删除或修改", Toast.LENGTH_LONG).show();
                        int icon = mlist.get(getLayoutPosition()).getIcon();
                        String name = mlist.get(getLayoutPosition()).getName();

                        LitePalUtil.delete(Category.class, "icon = ? and name = ?", icon + "", name);
                        mlist.remove(getLayoutPosition());
                        notifyDataSetChanged();
                        TypeZhichu.refresh();//刷新数据
                        return false;
                    }
                });
                menu.show();


                return false;
            }
        }
    }
}
