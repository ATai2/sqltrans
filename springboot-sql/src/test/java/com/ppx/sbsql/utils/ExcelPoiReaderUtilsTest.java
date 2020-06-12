package com.ppx.sbsql.utils;

import com.alibaba.fastjson.JSON;
import com.ppx.sbsql.mapping.ColumnCast;
import com.ppx.sbsql.table.CreateOracleTableCast;
import com.ppx.sbsql.table.CreateTableCast;
import com.ppx.sbsql.table.CreateTableColumn;
import com.ppx.sbsql.table.CreateTableModel;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExcelPoiReaderUtilsTest {

    String path = "D:\\code\\sqltrans\\springboot-sql\\src\\test\\resources\\0610.xlsx";

    @Test
    public void test_read2Book() throws IOException {

        Workbook sheets = ExcelPoiReaderUtils.readExcel2Workbook(path);
        List<String> sheetsNames = ExcelPoiReaderUtils.getSheetsNames(sheets);
        System.out.println(sheetsNames);

        StringBuilder result=new StringBuilder();
        StringBuilder oracleResult=new StringBuilder();

        for (int i = 0; i < sheetsNames.size(); i++) {
            Sheet sheet = sheets.getSheetAt(i);
            List<String> columnNames = ExcelPoiReaderUtils.getColumnNames(sheet);
            System.out.println(columnNames);
            String sheetName = sheetsNames.get(i);
            CreateTableModel createTableModel=new CreateTableModel();
            createTableModel.setTableName(sheetName.split("\\|")[0]);
            List<Map<String, Object>> x = ExcelPoiReaderUtils.readData(sheet, columnNames);

//            System.out.println(x);
            List<CreateTableColumn> list=new ArrayList<>();
            ColumnCast columnCast=new ColumnCast();
            for (int j = 0; j < x.size(); j++) {
                CreateTableColumn createTableColumn = columnCast.cast2Bean(x.get(j));
                list.add(createTableColumn);
//                System.out.println(JSON.toJSONString(createTableColumn));
            }
            createTableModel.setColumnFields(list);

//            to mssql
            CreateTableCast createTableCast=new CreateTableCast();
            String cast = createTableCast.cast(createTableModel, null,null);
            result.append(cast).append("\r\n").append("\r\n");
            System.out.println(JSON.toJSONString(createTableModel));

//            to oracle
            CreateOracleTableCast createOracleTableCast=new CreateOracleTableCast();
            String oracleRes = createOracleTableCast.cast(createTableModel);
            oracleResult.append(oracleRes).append("\r\n").append("\r\n");
//            System.out.println(JSON.toJSONString(createTableModel));

        }
        FileUtils.writeStringToFile(new File("mssresult.sql"), result.toString().toUpperCase());
        FileUtils.writeStringToFile(new File("oracalresult.sql"), oracleResult.toString().toUpperCase());
    }

   @Test
    public void test_dmpTable() throws IOException {

        Workbook sheets = ExcelPoiReaderUtils.readExcel2Workbook(path);
        List<String> sheetsNames = ExcelPoiReaderUtils.getSheetsNames(sheets);
        System.out.println(sheetsNames);

        StringBuilder result=new StringBuilder();
        StringBuilder oracleResult=new StringBuilder();

        for (int i = 0; i < sheetsNames.size(); i++) {
            Sheet sheet = sheets.getSheetAt(i);
            List<String> columnNames = ExcelPoiReaderUtils.getColumnNames(sheet);
            System.out.println(columnNames);
            String sheetName = sheetsNames.get(i);
            CreateTableModel createTableModel=new CreateTableModel();
            createTableModel.setTableName(sheetName.split("\\|")[0]);
            List<Map<String, Object>> x = ExcelPoiReaderUtils.readData(sheet, columnNames);

//            System.out.println(x);
            List<CreateTableColumn> list=new ArrayList<>();
            ColumnCast columnCast=new ColumnCast();
            for (int j = 0; j < x.size(); j++) {
                CreateTableColumn createTableColumn = columnCast.cast2Bean(x.get(j));
                list.add(createTableColumn);
//                System.out.println(JSON.toJSONString(createTableColumn));
            }
            createTableModel.setColumnFields(list);

//            to mssql
            CreateTableCast createTableCast=new CreateTableCast();
            String cast = createTableCast.cast(createTableModel, null,null);
            result.append(cast).append("\r\n").append("\r\n");
            System.out.println(JSON.toJSONString(createTableModel));

//            to oracle
            CreateOracleTableCast createOracleTableCast=new CreateOracleTableCast();
            String oracleRes = createOracleTableCast.cast(createTableModel);
            oracleResult.append(oracleRes).append("\r\n").append("\r\n");
//            System.out.println(JSON.toJSONString(createTableModel));

        }
        FileUtils.writeStringToFile(new File("mssresult.sql"), result.toString().toUpperCase());
        FileUtils.writeStringToFile(new File("oracalresult.sql"), oracleResult.toString().toUpperCase());
    }



    @Test
    public void test_parseJson() throws IOException {
        String s = FileUtils.readFileToString(new File("D:\\code\\sqltrans\\springboot-sql\\src\\main\\resources\\sqlTemplate.json"));

        Map map = JSON.parseObject(s, Map.class);
        Map createTabel = (Map) map.get("createTabel");
        Object mssql = createTabel.get("mssql");
        System.out.println(mssql);

        String createStr= (String) mssql;

//        createStr.replace()


    }



}