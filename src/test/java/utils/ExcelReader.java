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

    public static void openFile(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            book = new XSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getSheet(String sheetName) {
        sheet = book.getSheet(sheetName);
    }

    public static int getRowNumber() {
        return sheet.getLastRowNum();
    }

    public static int getColNumber(int rowIndex) {
        return sheet.getRow(rowIndex).getLastCellNum();
    }

    public static String getCellData(int rowIndex, int colIndex) {
        return sheet.getRow(rowIndex).getCell(colIndex).toString();
    }

    public static List<Map<String, String>> excelIntoMap(String filePath, String sheetName) {
        openFile(filePath);
        getSheet(sheetName);

        List<Map<String, String>> listData = new ArrayList<>();
        for (int row = 1; row < getRowNumber(); row++) {
            Map<String, String> map = new HashMap<>();
            for (int col = 0; col < getColNumber(row); col++) {
                map.put(getCellData(0, col), getCellData(row, col));
            }
            listData.add(map);
        }
        return listData;
    }
}
