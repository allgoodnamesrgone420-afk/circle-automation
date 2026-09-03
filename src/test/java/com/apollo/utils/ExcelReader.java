package com.apollo.utils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    public static Object[][] getData(String path, String sheetName, String typeWanted) {
        List<Object[]> out = new ArrayList<>();
        DataFormatter fmt = new DataFormatter();

        try (FileInputStream fis = new FileInputStream(path);
             Workbook wb = new XSSFWorkbook(fis)) {

            Sheet sheet = wb.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet not found: " + sheetName);
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                if (!"Y".equalsIgnoreCase(fmt.formatCellValue(row.getCell(4)))) {
                    continue;
                }
                String type = fmt.formatCellValue(row.getCell(1));
                if (typeWanted != null && !typeWanted.equalsIgnoreCase(type)) {
                    continue;
                }
                out.add(new Object[] {
                    fmt.formatCellValue(row.getCell(0)),
                    type,
                    fmt.formatCellValue(row.getCell(2)),
                    fmt.formatCellValue(row.getCell(3))
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return out.toArray(new Object[0][0]);
    }
}