package com.ppx.sbsql.utils;

import com.alibaba.fastjson.JSON;
import com.ppx.sbsql.mapping.ColumnCast;
import com.ppx.sbsql.table.CreateOracleTableCast;
import com.ppx.sbsql.table.CreateTableCast;
import com.ppx.sbsql.table.CreateTableColumn;
import com.ppx.sbsql.table.CreateTableModel;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

class Intert2Transaction {

    String path = "D:\\code\\sqltrans\\springboot-sql\\src\\test\\resources\\insertmssql.sql";
//    String path = "D:\\code\\sqltrans\\springboot-sql\\src\\test\\resources\\insertmssq2l.sql";

    @Test
    public void test() throws IOException {
        List<String> strings = FileUtils.readLines(new File(path));
        List<String> res=new ArrayList<>();

        strings.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                if (StringUtils.isEmpty(s)) {
                    return;
                }

                String tableName = StringUtils.substringBetween(s, "[", "]");

                String s1 = StringUtils.substringAfter(s, "(");
                String s2 = StringUtils.substringBefore(s1, ")");
                String[] split = s2.split(",");

                for (int i = 0; i < split.length; i++) {
                    if (split[i].contains("2020-06-08")) {
                        return;
                    }
                    if (split[i].contains("2020-06-11")) {
                        s = s.replace(split[i], "getdate()");
                    }
                }

                s=s.replace("[","").replace("]","");
                s = s.replace("; GO",";");
                System.out.println(s);



            }
        });


    }


}