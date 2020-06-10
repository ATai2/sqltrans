package com.ppx.sbsql.utils;


import java.util.*;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelPoiReaderUtils {


    public static Workbook readExcel2Workbook(String filePath) {
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }

        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                wb = new XSSFWorkbook(is);
            } else {
                wb = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public static List<String> getSheetsNames(Workbook book) {
        int numberOfSheets = book.getNumberOfSheets();
        System.out.println(numberOfSheets);
        if (numberOfSheets < 1) {
            throw new RuntimeException("no sheets");
        }
        List<String> sheetNames = new ArrayList<>();

        for (int i = 0; i < numberOfSheets; i++) {
            sheetNames.add(book.getSheetName(i));
        }
        return sheetNames;
    }

    public static List<String> getColumnNames(Sheet sheet) {
        List<String> list = new ArrayList<>();
        int rownum = sheet.getPhysicalNumberOfRows();
        Row row = sheet.getRow(0);

        int physicalNumberOfCells = row.getPhysicalNumberOfCells();
        for (int i = 0; i < physicalNumberOfCells; i++) {
            String stringCellValue = row.getCell(i).getStringCellValue();
            list.add(stringCellValue);
        }
        return list;
    }

    public static List<Map<String, Object>> readData(Sheet sheet, List<String> columns) {
        int rownum = sheet.getPhysicalNumberOfRows();
        List<Map<String, Object>> list=new ArrayList<>();
        for (int i = 1; i < rownum; i++) {
            Row row = sheet.getRow(i);
            Map<String,Object> rowMap=new HashMap<>();
            int physicalNumberOfCells = row.getPhysicalNumberOfCells();
            for (int j = 0; j < physicalNumberOfCells; j++) {
                Cell cell = row.getCell(j);
                if (cell == null) {
                    continue;
                }
                rowMap.put(columns.get(j), getCellValue(cell));
            }
            list.add(rowMap);
        }
        return list;
    }

    private static Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                return cell.getStringCellValue();
            default:
                return "";
        }

    }

    /**
     * 读取excel 第1张sheet （xls和xlsx）
     *
     * @param filePath excel路径
     * @param columns  列名（表头）
     * @return
     * @author lizixiang ,2018-05-08
     */
    public List<Map<String, String>> readExcel(String filePath, String columns[]) {
        Sheet sheet = null;
        Row row = null;
        Row rowHeader = null;
        List<Map<String, String>> list = null;
        String cellData = null;
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                wb = new XSSFWorkbook(is);
            } else {
                wb = null;
            }
            if (wb != null) {
                // 用来存放表中数据
                list = new ArrayList<Map<String, String>>();
                // 获取第一个sheet
                sheet = wb.getSheetAt(0);
                // 获取最大行数
                int rownum = sheet.getPhysicalNumberOfRows();
                // 获取第一行
                rowHeader = sheet.getRow(0);
                row = sheet.getRow(0);
                // 获取最大列数
                int colnum = row.getPhysicalNumberOfCells();
                for (int i = 1; i < rownum; i++) {
                    Map<String, String> map = new LinkedHashMap<String, String>();
                    row = sheet.getRow(i);
                    if (row != null) {
                        for (int j = 0; j < colnum; j++) {
                            if (columns[j].equals(getCellFormatValue(rowHeader.getCell(j)))) {
                                cellData = (String) getCellFormatValue(row
                                        .getCell(j));
                                map.put(columns[j], cellData);
                            }
                        }
                    } else {
                        break;
                    }
                    list.add(map);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取单个单元格数据
     *
     * @param cell
     * @return
     * @author lizixiang ,2018-05-08
     */
    public String getCellFormatValue(Cell cell) {
        String cellValue = null;
//        cellValue = cell.getStringCellValue()
        return cellValue;
    }

}
