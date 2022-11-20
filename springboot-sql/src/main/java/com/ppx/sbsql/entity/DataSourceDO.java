package com.ppx.sbsql.entity;

import lombok.Data;

@Data
public class DataSourceDO {
    private Long id;
    private String host;
    private String port;
    private String userName;
    private String pwd;
}
