package com.test.mercury.main;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class DataFetchOperations {
    @Test
    public void readXLData(){
        InputStream XlsxFileToRead = null;
        XSSFWorkbook workbook = null;
        String fileName = "F:\\Imp Docs\\Loan\\Doc list.xlsx";
        try {
//            XlsxFileToRead = c;

            //Getting the workbook instance for xlsx file
            workbook = new XSSFWorkbook(new FileInputStream(fileName));
        } catch (FileNotFoundException  e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //getting the first sheet from the workbook using sheet name.
        // We can also pass the index of the sheet which starts from '0'.
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row;
        XSSFCell cell;

        //Iterating all the rows in the sheet
        Iterator rows = sheet.rowIterator();
        while (rows.hasNext()) {
            row = (XSSFRow) rows.next();
            //Iterating all the cells of the current row
            Iterator cells = row.cellIterator();

            while (cells.hasNext()) {
                cell = (XSSFCell) cells.next();


                if (cell.getCellTypeEnum() == CellType.STRING) {
                    System.out.print(cell.getStringCellValue() + " ");
                    Reporter.log(cell.getStringCellValue()+"\n");
                } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                    System.out.print(cell.getNumericCellValue() + " ");
                    Reporter.log(cell.getNumericCellValue() +"\n");
                } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
                    System.out.print(cell.getBooleanCellValue() + " ");

                } else { // //Here if require, we can also add below methods to
                    // read the cell content
                    // XSSFCell.CELL_TYPE_BLANK
                    // XSSFCell.CELL_TYPE_FORMULA
                    // XSSFCell.CELL_TYPE_ERROR
                }
            }
            System.out.println();
            try {
                XlsxFileToRead.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
