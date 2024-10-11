package org.server;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

class item_info{
    public String order_number;
    public String invoice_number;

    public  item_info(String str1, String str2){
        order_number = str1;
        invoice_number = str2;
    }
};
public class convertfilethread extends gthread {


    private String input_path;
    private String output_path;

    public convertfilethread(String name){
        super(name);
        input_path = "C:\\Temp\\infile.xlsx";
        output_path = "C:\\Temp\\oufile.xlsx";
    }

    @Override
    protected boolean on_thread_loop() {
        try {
            FileInputStream in = new FileInputStream(input_path);
            ArrayList<item_info> itemlist = read_file();

//            XSSFWorkbook workbook = new XSSFWorkbook(in);
//            XSSFSheet sheet = workbook.getSheetAt(0);
//            int rowNo = 0;
//            int cellIndex = 0;
//
//            int rows = sheet.getPhysicalNumberOfRows();
//            for(rowNo = 0; rowNo < rows; rowNo++) {
//                XSSFRow row = sheet.getRow(rowNo);
//                if (row != null) {
//                    int cells = row.getPhysicalNumberOfCells(); // 해당 Row에 사용자가 입력한 셀의 수를 가져온다
//                    for (cellIndex = 0; cellIndex <= cells; cellIndex++) {
//                        XSSFCell cell = row.getCell(cellIndex); // 셀의 값을 가져온다
//                        String value = "";
//                        if (cell == null) { // 빈 셀 체크
//                            continue;
//                        } else {
//                            // 타입 별로 내용을 읽는다
//                            switch (cell.getCellType()) {
//                                case FORMULA:
//                                    value = cell.getCellFormula();
//                                    break;
//                                case NUMERIC:
//                                    System.out.println("numeric");
//                                    value = cell.getNumericCellValue() + "";
//                                    break;
//                                case STRING:
//                                    System.out.println("string");
//                                    value = cell.getStringCellValue() + "";
//                                    break;
//                                case BLANK:
//                                    value = cell.getBooleanCellValue() + "";
//                                    break;
//                                case ERROR:
//                                    value = cell.getErrorCellValue() + "";
//                                    break;
//                            }
//                        }
//                        System.out.println(rowNo + "번 행 : " + cellIndex + "번 열 값은: " + value);
//                    }
//
//                }

//            }
        } catch (FileNotFoundException e) {
            System.out.println("no file exists. " + input_path);
            //throw new RuntimeException(e);
            return false;
        } catch (IOException e) {
            System.out.println("excetp occurd " + input_path);

        }

        return false;
    };

    protected ArrayList<item_info>  read_file() {

        ArrayList<item_info> itemlist = new ArrayList<>();
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(input_path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rowNo = 0;
        int cellIndex = 0;

        int rows = sheet.getPhysicalNumberOfRows();
        for(rowNo = 0; rowNo < rows; rowNo++) {
            XSSFRow row = sheet.getRow(rowNo);
            if (row == null) {
                continue;
            }

            XSSFCell order = row.getCell(0);
            XSSFCell invoice = row.getCell(1);// 셀의 값을 가져온다
            // 빈 셀 체크
            if (order == null) continue;
            if (invoice == null) continue;


            if ( order.getCellType() == CellType.STRING ){

                String str = order.getStringCellValue();
                if (Objects.equals(str, "판매사이트 주문번호")){
                    continue;
                }

                String[] arr = str.split(",");
                for (String s : arr) {
                    item_info ob = new item_info(s, invoice.getStringCellValue());
                    itemlist.add(ob);
//                        System.out.println(arlist.get(i));

                }
            }

            //System.out.println(rowNo + "번 행 : " + cellIndex + "번 열 값은: " + value);

        }
        return itemlist;
    }
    protected void write_file(ArrayList<item_info> itemlist){

        try {
            gwriteexcel excel = new gwriteexcel(output_path);
            // header
            excel.write_data(0,"판매사이트 주문번호");
            excel.write_data(1,"배송사 송장번호");
            // data
            for (item_info element : itemlist) {
                excel.create_nextrow();
                excel.write_data(0, element.order_number);
                excel.write_data(0, element.invoice_number);
            }

            excel.makefile();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
