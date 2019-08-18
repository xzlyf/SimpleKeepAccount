package com.xz.ska.sql;

import com.xz.ska.entity.Book;

import org.litepal.crud.DataSupport;

import java.util.List;

public class LitePalUtil  {
    /**
     * save====================================
     * @param timeStamp
     * @param money
     * @param remarks
     * @param type
     */
    public static void saveBook(long timeStamp,double money,String remarks,int type){
        Book book = new Book();
        book.setTimeStamp(timeStamp);
        book.setMoney(money);
        book.setRemarks(remarks);
        book.setType(type);
        book.save();
    }
    public static void saveBook(Book book){
        book.save();
    }

    /**
     * update===================================
     * @param book
     * @param param
     */
    public static void updataBook(Book book,String ... param){
        book.updateAll(param);
    }

    /**
     * delete====================================
     * @param c
     * @param timeStamp
     */
    public static void deleteBook(Class<Book> c,long timeStamp){
        DataSupport.deleteAll(c,"timestamp = ?",timeStamp+"");
    }
    public static void delete(Class<?> c,String ... param){
        DataSupport.deleteAll(c,param);
    }

    /**
     * query=======================================
     * @param c
     * @return
     */
    public static List<Book> queryBookAll(Class<Book> c){
        return DataSupport.findAll(c);
    }
    //模糊查询，待完成
    public static List<Book> queryBook(Class<Book> c,double money){
        return DataSupport.where("money = ?",money+"").find(c);
    }

}
