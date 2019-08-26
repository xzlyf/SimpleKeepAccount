package com.xz.ska.entity;

import org.litepal.crud.DataSupport;

public class Category extends DataSupport {
    private int icon;
    private String name;
    private int state;// 0 =  支出  1 = 收入

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
