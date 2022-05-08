package utils;

import com.google.gson.internal.bind.util.ISO8601Utils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ExcelReader {

    static Workbook book;
    static Sheet sheet;

    //to open
    public static void openExcel(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            book = new XSSFWorkbook(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //open the sheet in excel file
    public static void getSheet(String sheetName) {
        sheet = book.getSheet(sheetName);
    }

    //it will return total no. of rows available in the worksheet
    public static int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    //it will return the total no. of columns it every row
    public static int getColsCount(int rowIndex) {
        return sheet.getRow(rowIndex).getPhysicalNumberOfCells();
    }

    //it will return the data from cell in string format
    public static String getCellData(int rowIndex, int collIndex) {
        return sheet.getRow(rowIndex).getCell(collIndex).toString();
    }

    public static List<Map<String, String>> excelIntoMap(String filePath, String sheetName) {
        openExcel(filePath);
        getSheet(sheetName);
        List<Map<String, String>> listData = new ArrayList<>();

        for (int row = 1; row < getRowCount(); row++) {
            //creating a map for every row
            Map<String, String> map = new LinkedHashMap<>();
            for (int col = 0; col < getColsCount(row); col++) {
                map.put(getCellData(0, col), getCellData(row, col));
            }
            listData.add(map);
        }
        return listData;
    }

}
