package com.ai.tool;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 123
 */
public class ExcelCellUpdater {
    public static void main(String[] args) {
        String filePath = "path/to/your/excel/file.xlsx";
        String sheetName = "Sheet1";
        int rowNum = 1; // 行号，从0开始
        int cellNum = 2; // 列号，从0开始

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fileInputStream)) {
            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }

            Cell cell = row.getCell(cellNum);
            if (cell == null) {
                cell = row.createCell(cellNum);
            }
            cell.setCellValue("123456789sad"); // 设置新的单元格数据

            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                workbook.write(fileOutputStream);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
