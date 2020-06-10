package com.ppx.sbsql.table;

import lombok.Data;

@Data
public class CreateTableColumn {
    private String fieldName;
    private String fieldType;
    private Integer fieldLength;
    private Integer decimal;
    private Boolean notNull;
    private Boolean pk;
    private Boolean unique;
    private String comment;
    private String alias;
}
