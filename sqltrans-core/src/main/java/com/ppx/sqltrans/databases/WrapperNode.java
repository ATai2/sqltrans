package com.ppx.sqltrans.databases;

public class WrapperNode {

    public static final String SINGLE_BLANK = " ";
    //        比较字段
    String field;
    //        操作符
    Opt opt;
    //        单值比较: 字符串，int
    //        列表比较  between a and b;   in(a,b,c)  2020-09-06 暂不实现
    Object value;

    String dbType;
    SqlTypeEnum sqlTypeEnum;

    public WrapperNode() {
    }

    public static WrapperNode of(String field, Opt opt, Object value) {
        WrapperNode wrapperNode = new WrapperNode(field, opt, value);
        return wrapperNode;
    }

    public static WrapperNode of(String field, Opt opt, Object value, SqlTypeEnum sQlTypeEnum) {
        WrapperNode wrapperNode = new WrapperNode(field, opt, value);
        wrapperNode.setSqlTypeEnum(sQlTypeEnum);
        return wrapperNode;
    }

    public static WrapperNode of(String field, Opt opt, Object value, String dbType) {

        WrapperNode wrapperNode = new WrapperNode(field, opt, value);
        wrapperNode.dbType = dbType;
        return wrapperNode;
    }

    public WrapperNode(String field, Opt opt, Object value) {
        this.field = field;
        this.opt = opt;
        this.value = value;
    }

    public SqlTypeEnum getSqlTypeEnum() {
        return sqlTypeEnum;
    }

    public void setSqlTypeEnum(SqlTypeEnum sqlTypeEnum) {
        this.sqlTypeEnum = sqlTypeEnum;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Opt getOpt() {
        return opt;
    }

    public void setOpt(Opt opt) {
        this.opt = opt;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }


    public String toSql(String field, Opt opt, Object value) {
        assert value != null;
        StringBuffer sql = new StringBuffer(field);
        sql.append(opt.getOperation());
        if (value instanceof String) {
            sql.append("'").append(value.toString()).append("'");
        } else {
            sql.append(value.toString());
        }
        return sql.toString();
    }
}