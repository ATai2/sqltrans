package com.ppx.sbsql.table;

import lombok.Data;

import java.util.List;

@Data
public class CreateTableModel {

    private String tableName;
    private String tableComment;
    private List<CreateTableColumn> columnFields;


//    private


}
