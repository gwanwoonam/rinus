package org.server;

import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class gwriteexcel {

    private SXSSFWorkbook workbook = null;
    private SXSSFSheet sheet = null;
    private SXSSFRow row = null;
    private SXSSFCell cell = null;
    private int rows = 0;
    private int cols = 0;
    private int cur_row = 0;


    String filepath;

    public gwriteexcel(String path){
        filepath = path;
        workbook = new SXSSFWorkbook();
        sheet = workbook.createSheet("sheet1");
        row = sheet.createRow(cur_row);

    }

    void write_data(int col, String str){
        cell = row.createCell(col);
        cell.setCellValue(str);
        cols++;
    }

    void create_nextrow(){
        cur_row++;
        row = sheet.createRow(cur_row);
        rows++;
    }

    boolean makefile () {
        try {
            File file = new File(filepath);
            boolean fileCreated = file.createNewFile();
            if (!fileCreated) {
                throw new IOException("Unable to create file at specified path. It already exists");
            }


            FileOutputStream fos = null;

            fos = new FileOutputStream(file);

            if (workbook != null) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return  true;
    }

}
