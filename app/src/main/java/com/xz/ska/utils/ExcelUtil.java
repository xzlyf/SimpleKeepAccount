package com.xz.ska.utils;

import android.content.Context;
import android.widget.Toast;

import com.xz.ska.entity.Book;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 版权声明：本文为博主原创文章，遵循 CC 4.0 by-sa 版权协议，转载请附上原文出处链接和本声明。
 * 本文链接：https://blog.csdn.net/qq_36982160/article/details/82421940
 *
 * @author dmrfcoder
 * @date 2019/2/14
 */

public class ExcelUtil {

    private static WritableFont arial14font = null;

    private static WritableCellFormat arial14format = null;
    private static WritableFont arial10font = null;
    private static WritableCellFormat arial10format = null;
    private static WritableFont arial12font = null;
    private static WritableCellFormat arial12format = null;
    private final static String UTF8_ENCODING = "UTF-8";


    /**
     * 单元格的格式设置 字体大小 颜色 对齐方式、背景颜色等...
     */
    private static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(jxl.format.Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);

            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(Colour.GRAY_25);

            arial12font = new WritableFont(WritableFont.ARIAL, 10);
            arial12format = new WritableCellFormat(arial12font);
            //对齐格式
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            //设置边框
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        } catch (WriteException e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化Excel表格
     *
     * @param filePath  存放excel文件的路径（path/demo.xls）
     * @param sheetName Excel表格的表名
     * @param colName   excel中包含的列名（可以有多个）
     */
    public static void initExcel(String filePath, String sheetName, String[] colName) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                return;
            }
            workbook = Workbook.createWorkbook(file);
            //设置表格的名字
            WritableSheet sheet = workbook.createSheet(sheetName, 0);
            //创建标题栏
            sheet.addCell((WritableCell) new Label(0, 0, filePath, arial14format));
            for (int col = 0; col < colName.length; col++) {
                sheet.addCell(new Label(col, 0, colName[col], arial10format));
            }
            //设置行高
            sheet.setRowView(0, 340);
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将制定类型的List写入Excel中
     *
     * @param objList  待写入的list
     * @param fileName
     * @param c
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> boolean writeObjListToExcel(List<T> objList, String fileName, Context c) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);

                in = new FileInputStream(new File(fileName));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(fileName), workbook);
                WritableSheet sheet = writebook.getSheet(0);

                for (int j = 0; j < objList.size(); j++) {
                    Book demoBean = (Book) objList.get(j);
                    List<String> list = new ArrayList<>();
                    list.add(demoBean.getTimeStamp() + "");
                    list.add(demoBean.getMoney() + "");
                    list.add(demoBean.getRemarks());
                    list.add(demoBean.getType() + "");
                    list.add(demoBean.getTitle());
                    list.add(demoBean.getState() + "");

                    for (int i = 0; i < list.size(); i++) {
                        sheet.addCell(new Label(i, j + 1, list.get(i), arial12format));
                        if (list.get(i).length() <= 4) {
                            //设置列宽
                            sheet.setColumnView(i, list.get(i).length() + 8);
                        } else {
                            //设置列宽
                            sheet.setColumnView(i, list.get(i).length() + 5);
                        }
                    }
                    //设置行高
                    sheet.setRowView(j + 1, 350);
                }


                writebook.write();
                workbook.close();
                Toast.makeText(c, "导出Excel成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return true;

        }
        return false;
    }

    /**
     * 读取Excel文件
     */
    public static List<Book> ReadExcel(String fileName) {
        Workbook mWorkbook;
        Sheet mSheet1 = null;
        InputStream mInputStream = null;
        List<Book> mlist = new ArrayList<>();
        try {
            mInputStream = new FileInputStream(fileName);
            mWorkbook = Workbook.getWorkbook(mInputStream);
            mSheet1 = mWorkbook.getSheet(0);
            int rows = mSheet1.getRows();
            int cols = mSheet1.getColumns();
//            Log.d("xz","当前工作表的名字: "+mSheet1.getName());
//            Log.d("xz", "总行数: "+rows);
//            Log.d("xz", "总列数: "+cols);
            //因为第一行是标题，所以不录入了
            for (int i = 1; i < rows; i++) {
                Book book = new Book();
                for (int j = 0; j < cols; j++) {
                    // getCell(Col,Row)获得单元格的值
//                    Log.w("xz", "ReadExcel: "+(mSheet1.getCell(j, i)).getContents()  );
                    //通过匹配来装入集合，这样子虽然效率很低，但我也不会其他方法了...
                    switch (j) {
                        case 0:
                            book.setTimeStamp(Long.valueOf((mSheet1.getCell(j, i)).getContents()));
                            break;
                        case 1:
                            book.setMoney(Double.valueOf((mSheet1.getCell(j, i)).getContents()));
                            break;
                        case 2:
                            book.setRemarks((mSheet1.getCell(j, i)).getContents());
                            break;
                        case 3:
                            book.setType(Integer.valueOf((mSheet1.getCell(j, i)).getContents()));
                            break;
                        case 4:
                            book.setTitle((mSheet1.getCell(j, i)).getContents());
                            break;
                        case 5:
                            book.setState(Integer.valueOf((mSheet1.getCell(j, i)).getContents()));
                            break;
                    }
                }
                mlist.add(book);
            }
            mWorkbook.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (mInputStream != null) {
                    mInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return mlist;
    }

}
