package com.ppx.sbsql.entity;

import lombok.Data;

@Data
public class DataSourceDTO {
    private String driver;
    private String url;
    private String name;
    private String pwd;
}
