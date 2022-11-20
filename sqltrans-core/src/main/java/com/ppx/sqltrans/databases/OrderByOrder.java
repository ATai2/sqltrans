package com.ppx.sqltrans.databases;

public enum OrderByOrder {

    ASC("asc"),
    DESC("desc");

    String order;

    OrderByOrder(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }
}
