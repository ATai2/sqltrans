package com.ppx.sbsql.table;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CreateTableCast {

    public static final String SPACE4 = "    ";
    public static final String SPACE = " ";
    String NOT_NULL = " not null ";
    String PK = "  primary key ";
    String UNIQUE = "  unique ";


    public String cast(CreateTableModel createTableModel, String dbType, Function<CreateTableColumn, String> function) {
        System.out.println("==============cast================");
        List<String> fieldList = new ArrayList<>();

        List<CreateTableColumn> columnFields = createTableModel.getColumnFields();
        if (function == null) {
            columnFields.forEach(new Consumer<CreateTableColumn>() {
                @Override
                public void accept(CreateTableColumn createTableColumn) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(SPACE4).append(createTableColumn.getFieldName());
                    String fieldType = createTableColumn.getFieldType();
                    if ("varchar".equalsIgnoreCase(fieldType.trim())) {
                        sb.append(SPACE).append("varchar(").append(createTableColumn.getFieldLength()).append(")").append(SPACE);
                    }
                    if ("char".equalsIgnoreCase(fieldType.trim())) {
                        sb.append(SPACE).append("char(").append(createTableColumn.getFieldLength()).append(")").append(SPACE);
                    }

                    if (createTableColumn.getNotNull()) {
                        sb.append(NOT_NULL).append(SPACE);
                    }

                    if (createTableColumn.getPk()) {
                        sb.append(PK).append(SPACE);
                    }
                    if (createTableColumn.getUnique()) {
                        sb.append(UNIQUE).append(SPACE);
                    }

                    String e = sb.toString().replaceAll(" ,", ",");

//                sb.append(",").append("\\r\\n");
                    fieldList.add(e);
                }
            });
        } else {
            columnFields.forEach(new Consumer<CreateTableColumn>() {
                @Override
                public void accept(CreateTableColumn createTableColumn) {
                    fieldList.add(function.apply(createTableColumn));
                }
            });
        }

        String fields = StringUtils.join(fieldList, ",\r\n");
        System.out.println(fields);
        String sql = "";
        try {
            File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "sqlTemplate.json");
            String s = FileUtils.readFileToString(file);
            Map map = JSON.parseObject(s, Map.class);
            Map createTabel = (Map) map.get("createTabel");
            if ("MSSQL".equalsIgnoreCase(dbType) || StringUtils.isEmpty(dbType)) {
                sql = (String) createTabel.get("mssql");
            } else {
                sql = (String) createTabel.get("oracle");
            }
//            System.out.println(s);
            sql = sql.replaceAll("tableName", createTableModel.getTableName());
            sql = sql.replaceAll("fields", fields);
            System.out.println("==============================");
            System.out.println(sql);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sql;
    }
}
