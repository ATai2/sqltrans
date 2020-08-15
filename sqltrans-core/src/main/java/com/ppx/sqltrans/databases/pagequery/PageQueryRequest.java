package com.ppx.sqltrans.databases.pagequery;

public class PageQueryRequest {

    private Integer start;
    private Integer end;

    /**
     * 从1开始
     */
    private Integer page = 1;
    /**
     * 每页大小
     */
    private Integer rows = 10;
    /**
     * 查询列  单查询
     */
    private String sort;
    /**
     * asc desc
     */
    private String order;



    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
