package com.xz.ska.constan;

import android.content.Context;
import android.content.res.Resources;

import com.xz.ska.R;
import com.xz.ska.entity.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyData {
    private static Resources res;
    private final Context context;
    private static CurrencyData obj;
    private static String[] name;
    private static String[] symbol;

    private CurrencyData(Context context) {
        this.context = context;
        res = context.getResources();
        name = res.getStringArray(R.array.currency_name);
        symbol = res.getStringArray(R.array.currency_sym);
    }


    //程序启动时初始化
    public static CurrencyData initType(Context context) {
        if (obj == null) {
            return new CurrencyData(context);
        }
        return obj;
    }

    /**
     * 获取货币数据
     *
     * @return
     */
    public static List<Currency> getCurrency() {
        List<Currency> list = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            Currency currency = new Currency();
            currency.setName(name[i]);
            currency.setSymbol(symbol[i]);
            list.add(currency);
        }
        return list;
    }
}
