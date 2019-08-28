package com.xz.ska.sql;

import android.database.Cursor;

import com.xz.ska.entity.Book;
import com.xz.ska.entity.CFAS;
import com.xz.ska.entity.Category;

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
    public static void saveBook(long timeStamp, double money, String remarks, int type, String title, int state) {
        Book book = new Book();
        book.setTimeStamp(timeStamp);
        book.setMoney(money);
        book.setRemarks(remarks);
        book.setType(type);
        book.setTitle(title);
        book.setState(state);
        book.save();
    }

    public static void saveBook(Book book) {
        book.save();
    }

    public static void saveCategory(String name, int icon, int state) {
        Category category = new Category();
        category.setName(name);
        category.setIcon(icon);
        category.setState(state);
        category.save();
    }

    public static void saveCFAS(String ...param) {

        CFAS cfas = new CFAS();
        if (param[0]!=null){
            cfas.setK1(param[0]);
        }
        if (param[1]!=null){
            cfas.setK2(param[1]);
        }
        if (param[2]!=null){
            cfas.setK3(param[2]);
        }
        if (param[3]!=null){
            cfas.setK4(param[3]);
        }
        if (param[4]!=null){
            cfas.setK5(param[4]);
        }
        if (param[5]!=null){
            cfas.setK6(param[5]);
        }
        cfas.save();
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
     * @param timeStamp
     */
    public static void deleteBook(long timeStamp) {
        DataSupport.deleteAll(Book.class, "timestamp = ?", timeStamp + "");
    }

    public static void delete(Class<?> c, String... conditions) {
        DataSupport.deleteAll(c, conditions);
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
     * 起始时间戳 --结束时间戳
     *
     * @param start
     * @param end
     * @return
     */
    public static List<Book> queryBookDATE(long start, long end) {
        Cursor cursor = DataSupport
                .findBySQL("SELECT * FROM Book WHERE  timestamp BETWEEN " + start + " AND " + end);
        int length = cursor.getCount();

        List<Book> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Book book = new Book();
            book.setMoney(cursor.getDouble(cursor.getColumnIndex("money")));
            book.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));
            book.setTimeStamp(cursor.getLong(cursor.getColumnIndex("timestamp")));
            book.setState(cursor.getInt(cursor.getColumnIndex("state")));
            book.setType(cursor.getInt(cursor.getColumnIndex("type")));
            book.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            list.add(book);
        }
        cursor.close();
        return list;
    }

    //模糊查询，待完成
    public static List<Book> queryBook(double money) {
        return DataSupport.where("money = ?", money + "").find(Book.class);
    }

    /**
     * 查询一个表的所有数据
     *
     * @param c
     * @return
     */
    public static List<?> queryAll(Class<?> c) {
        return DataSupport.findAll(c);
    }

    /**
     * 查询一个的第一条数据
     *
     * @param c
     * @return
     */
    public static Object queryFirst(Class<?> c) {
        return DataSupport.findFirst(c);
    }

}
