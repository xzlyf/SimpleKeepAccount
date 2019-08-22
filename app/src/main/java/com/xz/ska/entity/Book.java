package com.xz.ska.entity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class Book extends DataSupport implements Serializable {
    private long timeStamp;//时间戳
    private double money;//钱 正数-收入 负数-支出
    private String remarks;//备注
    private int type;//类型
    private int state;// 0 =  支出  1 = 收入

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
