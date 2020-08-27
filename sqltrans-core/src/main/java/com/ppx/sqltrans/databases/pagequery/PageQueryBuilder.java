package com.ppx.sqltrans.databases.pagequery;


import com.ppx.sqltrans.DmpException;
import com.ppx.sqltrans.ErrorCodeEnum;
import com.ppx.sqltrans.databases.OrderByOrder;
import com.ppx.sqltrans.databases.Wrapper;
import com.ppx.sqltrans.tools.Constant;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * 分页查询
 */
public class PageQueryBuilder {

    private String countSql = "select count(1) from ";
    /**
     * sql查询语句
     */
    private String sql;
    /**
     * 数据库类型
     */
    private String dbType;
    /**
     * 排序字段
     */
    private String orderByName;
    /**
     * 顺序
     */
    private OrderByOrder orderByOrder;
    /**
     * 起始
     */
    private Integer start = 0;
    /**
     * 结束
     */
    private Integer end = 10;
    /**
     * 数据库策略
     */
    private IPageQuery pageQuery;

    /**
     * 添加where
     */
    private Wrapper whereWrapper;
    private List  valueList=new ArrayList(4);

    private StringBuilder genericSelect = new StringBuilder("select * from ");


    public PageQueryBuilder dbType(String dbType) {
        this.dbType = dbType;
        return this;
    }


    public PageQueryBuilder sqlClause(String sql) {
        this.sql = sql;
        return this;
    }

    public PageQueryBuilder table(String tableName) {
        if (Objects.isNull(tableName)) {
            throw new DmpException(ErrorCodeEnum.SYS_ERROR.getCode(), "tableName 不能为空");
        }
        if (!Objects.isNull(sql)) {
            throw new DmpException(ErrorCodeEnum.SYS_ERROR.getCode(), "sqlClause，table方法不能同时使用");
        }

        this.sql = genericSelect.append(tableName).toString();
        return this;
    }

    public PageQueryBuilder range(int start, int end) {
        this.start = start;
        this.end = end;
        return this;
    }

    public PageQueryBuilder orderBy(String name, OrderByOrder orderByOrder) {
        this.orderByName = name;
        this.orderByOrder = orderByOrder;
        return this;
    }

    public String build() {
        switch (this.dbType) {
            case Constant.MYSQL_TYPE:
                pageQuery = new MysqlPageQuery();
                break;
            case Constant.ORACLE_TYPE:
                pageQuery = new OraclePageQuery();
                break;
            case Constant.SQLSERVER_TYPE:
                pageQuery = new MSsqlPageQuery();
                break;
        }
        String pageQuery = this.pageQuery.pageQuery(sql, start, end, orderByName, orderByOrder);
        return pageQuery;
    }

    public PageQueryBuilder where(Wrapper wrapper) {
        StringBuilder whereClause = new StringBuilder(sql).append(" where ");
        wrapper.convertNode2Sql(this.dbType, whereClause, valueList);
        this.sql=whereClause.toString();
        return this;
    }


    public void convertSql(StringBuilder sb, Wrapper wrapper) {
        sb.append(" (");
        wrapper.convertNode2Sql(this.dbType, sb, valueList);
        if (null != wrapper.getNextWrapper()) {
//            有关系节点
            sb.append(" ").append(wrapper.getOpt().getOperation()).append(" ");
            convertSql(sb, wrapper.getNextWrapper());
        }
        sb.append(") ");
    }

    public Integer buildWithExec(BiFunction<String, Object[], Integer> function) {
        return function.apply(this.build(), this.valueList.toArray());
    }
}
