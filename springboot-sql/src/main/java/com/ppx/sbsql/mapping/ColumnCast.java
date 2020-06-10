package com.ppx.sbsql.mapping;

import com.ppx.sbsql.table.CreateTableColumn;

import java.util.Map;

public class ColumnCast implements FieldsToBean {

    @Override
    public CreateTableColumn cast2Bean(Map<String, Object> map) {
        CreateTableColumn createTableColumn = new CreateTableColumn();
        createTableColumn.setAlias(map.get("字段名") + "");
        createTableColumn.setFieldName(map.get("字段别名") + "");
        createTableColumn.setFieldType(map.get("类型") + "");
        String length = map.get("长度") + "";
        if (length.trim().length() > 0) {
            double parseDouble = Double.parseDouble(length);
            int lengthInt = (int) parseDouble;
            createTableColumn.setFieldLength(lengthInt);
        }

        String decm = map.get("精度") + "";
        if (decm.trim().length() > 0) {
            int decmInt = Integer.parseInt(decm);
            createTableColumn.setDecimal(decmInt);
        }
        String notNull = map.get("必填") + "";
        if ("是".equalsIgnoreCase(notNull.trim())) {
            createTableColumn.setNotNull(true);
        } else {
            createTableColumn.setNotNull(false);
        }

        String pk = map.get("主键") + "";
        if ("是".equalsIgnoreCase(pk.trim())) {
            createTableColumn.setPk(true);
        } else {
            createTableColumn.setPk(false);
            if ("唯一索引".equalsIgnoreCase(pk.trim())) {
                createTableColumn.setUnique(true);
            }
        }
        createTableColumn.setComment(map.get("备注") + "");
        return createTableColumn;
    }
}
