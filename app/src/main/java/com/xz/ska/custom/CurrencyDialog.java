package com.xz.ska.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.xz.ska.R;
import com.xz.ska.constan.CurrencyData;
import com.xz.ska.constan.Local;
import com.xz.ska.entity.Currency;
import com.xz.ska.utils.SharedPreferencesUtil;
import com.xz.ska.utils.SpacesItemDecorationVH;

import java.util.ArrayList;
import java.util.List;

public class CurrencyDialog extends Dialog {

    private RecyclerView recyclerCurrency;
    private CurrencyAdapter adapter;


    private Context mContext;

    public CurrencyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater1 = LayoutInflater.from(mContext);
        View view1 = inflater1.inflate(R.layout.dialog_currency_choose, null);
        setContentView(view1);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8);
        lp.height = (int) (d.heightPixels * 0.6);
        lp.dimAmount = 0.4f;//背景不变暗
        dialogWindow.setAttributes(lp);

        initView();
        init_recycler();
    }

    private void init_recycler() {

        adapter = new CurrencyAdapter(mContext, CurrencyData.getCurrency());
        recyclerCurrency.setLayoutManager(new GridLayoutManager(mContext, 4));
        recyclerCurrency.addItemDecoration(new SpacesItemDecorationVH(5));
        recyclerCurrency.setAdapter(adapter);
    }

    private void initView() {
        recyclerCurrency = findViewById(R.id.recycler_currency);
    }


    class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {
        private Context mContext;
        private List<Currency> mlist = new ArrayList<>();


        public CurrencyAdapter(Context context, List<Currency> list) {
            mContext = context;
            mlist.addAll(list);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_currency, viewGroup, false));

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder,  int i) {
            viewHolder.nameCurrency.setText(mlist.get(i).getName());
            viewHolder.symbolCurrency.setText(mlist.get(i).getSymbol());

        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {
            TextView nameCurrency;
            TextView symbolCurrency;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                nameCurrency = itemView.findViewById(R.id.name_currency);
                symbolCurrency = itemView.findViewById(R.id.symbol_currency);
                itemView.setOnTouchListener(this);
            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //点击放大效果
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setScaleX(1.2f);
                        v.setScaleY(1.2f);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        v.setScaleX(1.0f);
                        v.setScaleY(1.0f);
                        break;
                    case MotionEvent.ACTION_UP:
                        String sym = mlist.get(getLayoutPosition()).getSymbol();
                        SharedPreferencesUtil.saveString(mContext,"state","money_symbol",sym);
                        Local.moneySymbol = sym;
                        Toast.makeText(mContext, "下一次启动时生效", Toast.LENGTH_LONG).show();
                        v.setScaleX(1.0f);
                        v.setScaleY(1.0f);
                        dismiss();
                        break;

                }
                return true;
            }

        }

    }

}
