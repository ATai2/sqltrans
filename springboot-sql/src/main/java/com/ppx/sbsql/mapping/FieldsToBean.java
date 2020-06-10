package com.ppx.sbsql.mapping;

import com.ppx.sbsql.table.CreateTableColumn;

import java.util.Map;

public interface FieldsToBean {

    CreateTableColumn cast2Bean(Map<String, Object> map);
}
