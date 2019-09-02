package com.xz.ska.entity;

import java.util.ArrayList;
import java.util.List;

public class Framily {

    /**
     * total : 3
     * msg : 正常
     * code : 1
     * DATA : [{"icon":"http://192.168.0.177:8080/Family/icon/jlw.png","name":"捡漏王","download":null},{"icon":"http://192.168.0.177:8080/Family/icon/jlw.png","name":"轻巧记账","download":null},{"icon":"http://192.168.0.177:8080/Family/icon/jlw.png","name":"每日壁纸","download":null},{"icon":"http://192.168.0.177:8080/Family/icon/jlw.png","name":"轻巧系列","download":null},{"icon":"http://192.168.0.177:8080/Family/icon/jlw.png","name":"极简天气","download":null}]
     */

    private int total;
    private String msg;
    private int code;
    private List<DATABean> DATA = new ArrayList<>();

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DATABean> getDATA() {
        return DATA;
    }

    public void setDATA(List<DATABean> DATA) {
        this.DATA = DATA;
    }

    public static class DATABean {
        /**
         * icon : http://192.168.0.177:8080/Family/icon/jlw.png
         * name : 捡漏王
         * download : null
         */

        private String icon;
        private String name;
        private Object download;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getDownload() {
            return download;
        }

        public void setDownload(Object download) {
            this.download = download;
        }
    }
}
