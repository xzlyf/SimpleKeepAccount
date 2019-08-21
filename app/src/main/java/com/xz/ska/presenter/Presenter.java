package com.xz.ska.presenter;

import com.xz.com.log.LogUtil;
import com.xz.ska.base.BaseActivity;
import com.xz.ska.entity.Book;
import com.xz.ska.entity.TopInfo;
import com.xz.ska.model.Model;
import com.xz.ska.sql.LitePalUtil;
import com.xz.ska.utils.TimeUtil;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.xz.ska.utils.ActivityUtil.list;

public class Presenter {
    private Model model;
    private BaseActivity view;

    public Presenter(BaseActivity view) {
        this.view = view;
        model = new Model();
    }


    /**
     * 获取本地支配细节数据
     */
    public void getDetailData() {

        new Thread(new Runnable() {
            @Override
            public void run() {


                //查询今天的startdate和enddate
                String[] sco = TimeUtil.getStartAndEndDate(System.currentTimeMillis());
                //查询今天范围内的数据
                List<Book> bookList = LitePalUtil.queryBookDATE(sco[0], sco[1]);

                //存储顶部信息的容器
                TopInfo topInfo = new TopInfo();

                //今天支出总数
                BigDecimal bg = new BigDecimal(Double.toString(0));
                for (Book book : bookList) {
                    bg = bg.add(new BigDecimal(Double.toString(book.getMoney())));
                }
                topInfo.setRi_zhipei(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());


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

                view.backToUi(topInfo);
                view.backToUi(bookList);

            }
        }).start();
    }
    /**
     * 指定日期
     * 获取本地支配细节数据
     */
    public void getDetailData(final long time) {

        new Thread(new Runnable() {
            @Override
            public void run() {


                //查询今天的startdate和enddate
                String[] sco = TimeUtil.getStartAndEndDate(time);
                //查询今天范围内的数据
                List<Book> bookList = LitePalUtil.queryBookDATE(sco[0], sco[1]);

                //存储顶部信息的容器
                TopInfo topInfo = new TopInfo();

                //今天支出总数
                BigDecimal bg = new BigDecimal(Double.toString(0));
                for (Book book : bookList) {
                    bg = bg.add(new BigDecimal(Double.toString(book.getMoney())));
                }
                topInfo.setRi_zhipei(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());


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

                view.backToUi(topInfo);
                view.backToUi(bookList);

            }
        }).start();
    }
}
