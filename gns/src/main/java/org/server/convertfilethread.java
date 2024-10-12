package org.server;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

import org.apache.poi.ss.usermodel.CellType;
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
    // Const
    final String  INPUT_PATH = "C:\\Temp\\infile.xlsx";
    final String  OUTPUT_PATH = "C:\\Temp\\outfile.xlsx";

    final String COL1_NAME = "판매사이트 주문번호";
    final String COL2_NAME = "배송사 송장번호";


    //member var
    private String input_path;
    private String output_path;

    ArrayList<item_info> itemlist;


    public convertfilethread(String name){
        super(name);
        input_path = INPUT_PATH;
        output_path = OUTPUT_PATH;
    }

    @Override
    protected boolean on_thread_loop() {

        File file = new File(input_path);
        if (!file.exists()) {
            System.out.println("failed to find file. path=" + input_path);
            return false;
        }

        System.out.println("trying to read file. path=" + input_path);
        if (!read_file()){
            System.out.println("failed to read file.");
            return false;
        }
        System.out.println("complete to read file. path=" + input_path);

        System.out.println("trying to write file. path=" + output_path);
        if (!write_file()) {
            System.out.println("failed to write file.");
            return false;
        }
        System.out.println("complete to write file. path=" + output_path);
        return true;

    }


    protected boolean read_file() {

        itemlist = new ArrayList<>();
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(input_path);
        } catch (IOException e) {
            return false;
        }
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rowNo = 0;

        int rows = sheet.getPhysicalNumberOfRows();
        for(rowNo = 0; rowNo < rows; rowNo++) {
            XSSFRow row = sheet.getRow(rowNo);
            if (row == null) {
                continue;
            }

            XSSFCell order = row.getCell(0);
            XSSFCell invoice = row.getCell(1);// 셀의 값을 가져온다
            // 빈 셀 체크
            if (order == null){
                continue;
            }
            if (invoice == null){
                continue;
            }


            if ( order.getCellType() != CellType.STRING) {
                System.out.println("invalid celltype row= "+ rowNo);
            }

            String str = order.getStringCellValue();
            if (Objects.equals(str, COL1_NAME)){
                continue;
            }

            String[] arr = str.split(",");
            for (String s : arr) {
                item_info ob = new item_info(s, invoice.getStringCellValue());
                itemlist.add(ob);
            }
        }
        return true;
    }

    protected boolean write_file(){
        try {
            gwriteexcel excel = new gwriteexcel(output_path);
            // header
            excel.write_data(0,COL1_NAME);
            excel.write_data(1,COL2_NAME);

            // data
            for (item_info element : itemlist) {
                excel.create_nextrow();
                excel.write_data(0, element.order_number);
                excel.write_data(1, element.invoice_number);
            }

            excel.makefile();
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
