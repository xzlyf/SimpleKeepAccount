package com.xz.ska.sql;

import android.database.Cursor;

import com.xz.com.log.LogUtil;
import com.xz.ska.entity.Book;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class LitePalUtil {
    /**
     * save====================================
     *
     * @param timeStamp
     * @param money
     * @param remarks
     * @param type
     */
    public static void saveBook(long timeStamp, double money, String remarks, int type) {
        Book book = new Book();
        book.setTimeStamp(timeStamp);
        book.setMoney(money);
        book.setRemarks(remarks);
        book.setType(type);
        book.save();
    }

    public static void saveBook(Book book) {
        book.save();
    }

    /**
     * update===================================
     *
     * @param book
     * @param param
     */
    public static void updataBook(Book book, String... param) {
        book.updateAll(param);
    }

    /**
     * delete====================================
     *
     * @param c
     * @param timeStamp
     */
    public static void deleteBook(Class<Book> c, long timeStamp) {
        DataSupport.deleteAll(c, "timestamp = ?", timeStamp + "");
    }

    public static void delete(Class<?> c, String... param) {
        DataSupport.deleteAll(c, param);
    }

    /**
     * query=======================================
     *
     * @return
     */
    public static List<Book> queryBookAll() {
        return DataSupport.findAll(Book.class);
    }

    /**
     * 查询一个范围内的数据
         起始时间戳 --结束时间戳
     * @param start
     * @param end
     * @return
     */
    public static List<Book> queryBookDATE(String start, String end) {
        Cursor cursor = DataSupport.findBySQL("SELECT * FROM Book WHERE  timestamp BETWEEN " + start + " AND " + end);
        int length = cursor.getCount();

        List<Book> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Book book = new Book();
            book.setMoney(cursor.getDouble(cursor.getColumnIndex("money")));
            book.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));
            book.setTimeStamp(cursor.getLong(cursor.getColumnIndex("timestamp")));
            book.setType(cursor.getInt(cursor.getColumnIndex("type")));
            list.add(book);
        }
        cursor.close();
        return list;
    }

    //模糊查询，待完成
    public static List<Book> queryBook(double money) {
        return DataSupport.where("money = ?", money + "").find(Book.class);
    }

}
