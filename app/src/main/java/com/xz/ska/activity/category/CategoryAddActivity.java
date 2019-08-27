package com.xz.ska.activity.category;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xz.ska.R;
import com.xz.ska.base.BaseActivity;
import com.xz.ska.constan.Local;
import com.xz.ska.constan.TypeShouru;
import com.xz.ska.constan.TypeZhichu;
import com.xz.ska.sql.LitePalUtil;

import java.util.ArrayList;
import java.util.List;

public class CategoryAddActivity extends BaseActivity implements View.OnClickListener {


    private ImageView back;
    private ImageView confirm;
    private TextView title;
    private EditText typeName;
    private RecyclerView recyclerNewType;
    private int selectType = -1;//上一次选中的item
    private List<Integer> iconList = new ArrayList<>();

    @Override
    public int getLayoutResource() {
        return R.layout.activity_category_add;
    }

    @Override
    public void findID() {
        initView();
    }

    private void initView() {
        back = findViewById(R.id.back);
        confirm = findViewById(R.id.confirm);
        title = findViewById(R.id.tv_title);
        back.setOnClickListener(this);
        confirm.setOnClickListener(this);
        typeName = findViewById(R.id.type_name);
        recyclerNewType = findViewById(R.id.recycler_new_type);
    }

    @Override
    public void init_Data() {
        if (Local.state == 0) {
            title.setText("新增支出类型");
        } else {
            title.setText("新增收入类型");
        }
        init_Recycler();
    }

    private void init_Recycler() {
        Resources res = getResources();
        TypedArray iconArry = res.obtainTypedArray(R.array.custom_icon);

        for (int i = 0; i < iconArry.length(); i++) {
            iconList.add(iconArry.getResourceId(i, 0));
        }

        TypeAdapter adapter = new TypeAdapter(this);
        adapter.refresh(iconList);
        recyclerNewType.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerNewType.setAdapter(adapter);


    }

    @Override
    public void showData(Object object) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm:
                submit();
                break;
        }
    }

    /**
     * 提交事件
     */
    private void submit() {
        if (selectType == -1) {
            mToast("请选择一个图标");
            return;
        }
        if (typeName.getText().toString().trim().equals("")) {
            mToast("请给输入分类名称");
            return;
        }
        LitePalUtil.saveCategory(typeName.getText().toString().trim(),iconList.get(selectType),Local.state);
        TypeZhichu.refresh();//刷新图标库
        TypeShouru.refresh();
        mToast("正确");
        setResult(RESULT_OK,new Intent().putExtra("isRefresh",true));//返回数据给上一层
        finish();

    }

    class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {
        List<ImageView> lastView = new ArrayList<>();

        private Context mContext;
        private List<Integer> mlist = new ArrayList<>();


        public TypeAdapter(Context context) {
            mContext = context;

        }

        private void refresh(List<Integer> list) {
            mlist.clear();
            mlist.addAll(list);
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public TypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new TypeAdapter.ViewHolder(LayoutInflater.from(CategoryAddActivity.this).inflate(R.layout.item_image, viewGroup, false));

        }

        @Override
        public void onBindViewHolder(@NonNull final TypeAdapter.ViewHolder viewHolder, int i) {
            viewHolder.img.setImageResource(mlist.get(i));
            lastView.add(viewHolder.img);
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView img;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                img = itemView.findViewById(R.id.iamge_view);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //循环清理选中状态
                        for (ImageView view : lastView) {
                            view.setBackground(getDrawable(R.drawable.selector_btn_4));
                        }
                        //如果上一次选中的item等于本次选中的item那就清理本次选中的item的background
                        if (selectType == getLayoutPosition()) {
                            //清理选中状态
                            img.setBackground(getDrawable(R.drawable.selector_btn_4));
                            selectType = -1;//复原上一次选中的状态
                        } else {
                            //设置选中状态
                            img.setBackground(getDrawable(R.drawable.selector_btn_4_f));
                            selectType = getLayoutPosition();
                        }
                    }
                });
            }
        }
    }
}
