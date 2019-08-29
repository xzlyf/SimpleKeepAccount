package com.xz.ska.entity;

public class UpdateServer {

    /**
     * level : 0
     * name : 0.2-bate
     * code : 2
     * msg : 1.测试版2.前端功能基本实现3.后端正在学习4.测试6.全栈工程师7.测试
     * link : http://xzlyf.club/SimpleKeepAccount/apk/ska.apk
     */

    private int level;
    private String name;
    private int code;
    private String msg;
    private String link;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
