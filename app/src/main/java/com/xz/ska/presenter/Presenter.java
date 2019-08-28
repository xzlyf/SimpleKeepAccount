package com.xz.ska.presenter;

import com.xz.ska.base.BaseActivity;
import com.xz.ska.entity.Book;
import com.xz.ska.entity.Setting;
import com.xz.ska.entity.TopInfo;
import com.xz.ska.model.Model;
import com.xz.ska.sql.LitePalUtil;
import com.xz.ska.utils.ExcelUtil;
import com.xz.ska.utils.SharedPreferencesUtil;
import com.xz.ska.utils.TimeUtil;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Presenter {
    private Model model;
    private BaseActivity view;

    public Presenter(BaseActivity view) {
        this.view = view;
        model = new Model();
    }


    /**
     * 指定日期=====================================================================================
     * 获取本地支配细节数据
     */
    public void getDetailData(final long time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                view.showLoading();
                //查询该月和今天的startdate和enddate
                long[] sco = TimeUtil.getStartAndEndDateV2(time);


                //查询今天范围内的数据
                List<Book> bookList = LitePalUtil.queryBookDATE(sco[0], sco[1]);
                //存储顶部信息的容器
                TopInfo topInfo = new TopInfo();

                //今天支出总数
                BigDecimal bg_zhichu = new BigDecimal(Double.toString(0));
                BigDecimal bg_shouru = new BigDecimal(Double.toString(0));
                for (Book book : bookList) {
                    if (book.getState() == 0) {
                        bg_zhichu = bg_zhichu.add(new BigDecimal(Double.toString(book.getMoney())));
                    } else {
                        bg_shouru = bg_shouru.add(new BigDecimal(Double.toString(book.getMoney())));
                    }
                }
                BigDecimal bg_total = bg_shouru.subtract(bg_zhichu);//收入减去指出
                topInfo.setRi_zhipei(bg_total.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());


                //定制排序,日期越靠前的放前面
                Collections.sort(bookList, new Comparator<Book>() {
                    @Override
                    public int compare(Book o1, Book o2) {
                        if (o1.getTimeStamp() > o2.getTimeStamp()) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                });
                view.backToUi(bookList);


                //查询此月支出收入总数
                List<Book> totalBook = LitePalUtil.queryBookDATE(sco[2], sco[3]);
                BigDecimal month_zhichu = new BigDecimal(Double.toString(0));
                BigDecimal month_shouru = new BigDecimal(Double.toString(0));

                for (Book book : totalBook) {
                    if (book.getState() == 0) {
                        month_zhichu = month_zhichu.add(new BigDecimal(Double.toString(book.getMoney())));
                    } else {
                        month_shouru = month_shouru.add(new BigDecimal(Double.toString(book.getMoney())));
                    }
                }
                topInfo.setYue_shouru(month_shouru.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                topInfo.setYue_zhichu(month_zhichu.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                view.backToUi(topInfo);

                view.dismissLoading();
            }
        }).start();
    }

    /**
     * 获取用户设置==================================================================================
     */
    public void getUserSetting() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //容器
                Setting setting = new Setting();



                int hour;
                int minute;
                boolean mSwitch = SharedPreferencesUtil.getBoolean(view, "alarm", "mswitch", false);
                if (mSwitch) {
                    hour = SharedPreferencesUtil.getInt(view, "alarm", "mhour", 23);
                    minute = SharedPreferencesUtil.getInt(view, "alarm", "minute", 23);
                    setting.setShow(mSwitch);
                    setting.setShowString("每天" + hour + ":" + minute);
                }


                view.backToUi(setting);
            }
        }).start();



    }

    /**
     * 备份
     * 导出excel=====================================================================================
     */
    public void export2Excel(final List<Book> allDate) {
        view.showLoading();
                //        String filePath = "/sdcard/SimpleKeepAccount";
                String filePath = view.getExternalFilesDir("backups").toString();
                File file = new File(filePath);
                if (!file.exists()) {
                    file.mkdirs();
                }


                String excelFileName = "/"+System.currentTimeMillis()+".xls";


                String[] title = {"time", "money", "remarks","type","title","state"};
                String sheetName = "ska_Sheet1";

                filePath = filePath + excelFileName;


                ExcelUtil.initExcel(filePath, sheetName, title);


                boolean result = ExcelUtil.writeObjListToExcel(allDate, filePath, view);

                view.backToUi(result);
                view.dismissLoading();
//        mToast("已导出至："+filePath);



    }
}
