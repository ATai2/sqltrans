package com.ppx.sqltrans.databases;



import com.ppx.sqltrans.DmpException;
import com.ppx.sqltrans.ErrorCodeEnum;
import com.ppx.sqltrans.databases.convert.MysqlConvertor;
import com.ppx.sqltrans.databases.convert.OracleConvertor;
import com.ppx.sqltrans.databases.convert.SqlServerConvertor;

import java.util.List;
import java.util.Objects;

/**
 * 数据查询的包装类
 * where 子句条件
 */
public class Wrapper {


    /**
     * 当前节点数据   field = ?   : value   形式
     */
    private WrapperNode node;

    /**
     * 直接给sql语句的形式，不带参数
     */
    private String sqlClause;
    /**
     * 操作符
     */
    private Opt opt;

    /**
     * 下一个节点
     */
    private Wrapper nextWrapper;
    private Wrapper subWrapper;
    private Opt subOpt;

    public static Wrapper of(String s) {
        Wrapper wrapper = new Wrapper();
        wrapper.sqlClause = s;
        return wrapper;
    }

    public static Wrapper of(String s, Opt biLike, Object value) {
        return new Wrapper(WrapperNode.of(s, biLike, value));
    }

    /**
     * 子查询，replace方法
     *
     * @param s
     * @param replace
     * @param node
     * @return
     */
    public static Wrapper ofReplace(String s, String replace, WrapperNode node) {


        return null;
    }

    /**
     * @param s
     * @param smallerOrEqual
     * @param s1
     * @param date
     * @return
     */
    public static Wrapper of(String s, Opt smallerOrEqual, String s1, SqlTypeEnum date) {
        Wrapper wrapper = of(s, smallerOrEqual, s1);

        return wrapper;
    }

    public Wrapper addToTail(Opt opt, Wrapper node) {

        if (null == nextWrapper) {
            nextWrapper = node;
            this.opt = opt;
            return this;
        }
        Wrapper nextWrapper = this.nextWrapper;
        Wrapper thisWrapper = this;
        while (nextWrapper != null) {
            thisWrapper = nextWrapper;
            nextWrapper = nextWrapper.nextWrapper;
        }
        thisWrapper.opt = opt;
        thisWrapper.setNextWrapper(node);
        return this;
    }

    public Wrapper of(WrapperNode node) {
        return new Wrapper(node);
    }

    /**
     * 与
     *
     * @param nextWrapper
     * @return
     */
    public Wrapper and(Wrapper nextWrapper) {
        return addToTail(Opt.And, nextWrapper);
    }

    public Wrapper and(String s, Opt biLike, Object value) {
        Wrapper newWrapper = of(s, biLike, value);
        return addToTail(Opt.And, newWrapper);
    }

    /**
     * @param need        是否添加
     * @param nextWrapper
     * @return
     */
    public Wrapper and(Boolean need, Wrapper nextWrapper) {
        if (!need) {
            return this;
        }
        this.opt = Opt.And;
        return addToTail(Opt.And, nextWrapper);
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public Wrapper and(Boolean need, String s) {
        if (!need) {
            return this;
        }
        Wrapper wrapper = new Wrapper();
        wrapper.sqlClause = s;
        this.opt = Opt.And;
        return addToTail(Opt.And, wrapper);
    }

    public Wrapper and(String s) {
        Wrapper wrapper = new Wrapper();
        wrapper.sqlClause = s;
        this.opt = Opt.And;

        return addToTail(Opt.And, wrapper);
    }

    public Wrapper andSub(Wrapper wrapper) {
        return addSubToTail(Opt.And, wrapper);
    }

    public Wrapper andSub(boolean need, Wrapper wrapper) {
        if (!need) {
            return this;
        }

        return addSubToTail(Opt.And, wrapper);
    }

    private Wrapper addSubToTail(Opt opt, Wrapper wrapper) {
        if (null == nextWrapper) {
            this.subWrapper = wrapper;
            this.subOpt = opt;
            return this;
        }
        Wrapper nextWrapper = this.nextWrapper;
        Wrapper thisWrapper = this;
        while (nextWrapper != null) {
            thisWrapper = nextWrapper;
            nextWrapper = nextWrapper.nextWrapper;
        }
        thisWrapper.subOpt = opt;
        thisWrapper.subWrapper = wrapper;
        return this;
    }

    /**
     * 或
     *
     * @param nextWrapper
     * @return
     */
    public Wrapper or(Boolean need, Wrapper nextWrapper) {
        if (!need) {
            return this;
        }
        this.opt = Opt.Or;
        return addToTail(Opt.Or, nextWrapper);
    }


    public Wrapper or(String s, Opt biLike, Object value) {
        Wrapper newWrapper = of(s, biLike, value);
        return addToTail(Opt.Or, newWrapper);
    }

    public Wrapper or(Wrapper nextWrapper) {
        this.opt = Opt.Or;
        return addToTail(Opt.Or, nextWrapper);
    }

    public Wrapper() {
    }

    public Wrapper(WrapperNode node) {
        this.node = node;
    }

    String dbType;

    public void convertNode2Sql(String dbType, StringBuilder sb, List valueList) {
        this.setDbType(dbType);
        this.convertSql(sb, this, valueList);
    }

    /**
     * 递归方法，cast  wrapper
     *
     * @param sb
     * @param wrapper
     * @param valueList
     */
    protected void convertSql(StringBuilder sb, Wrapper wrapper, List valueList) {

        if (!Objects.isNull(wrapper.getSqlClause())) {
            sb.append(wrapper.getSqlClause());
        } else {
            WrapperNode node = wrapper.getNode();
            String s = convertNode2Sql(node, valueList);
            sb.append(s);
        }
//        判断旁支
        Wrapper subWrapper = wrapper.subWrapper;
        if (null != subWrapper) {
//            有关系节点
            sb.append(" ").append(wrapper.subOpt.getOperation()).append(" ");
            sb.append("(");
            convertSql(sb, subWrapper, valueList);
            sb.append(")");
        }

        Wrapper nextWrapper = wrapper.getNextWrapper();
        if (null != nextWrapper) {
            sb.append(" ").append(wrapper.getOpt().getOperation()).append(" ");
            convertSql(sb, nextWrapper, valueList);
        }
    }


    protected String convertNode2Sql(WrapperNode node, List valueList) {
        String res = "";
        switch (this.dbType) {

            case DataBaseConfig.MYSQL_TYPE:
            case DataBaseConfig.MARIADB_TYPE:
                res = new MysqlConvertor().cast(node, valueList);
                break;
            case DataBaseConfig.ORACLE_TYPE:
            case DataBaseConfig.DAMENG_TYPE:
                res = new OracleConvertor().cast(node, valueList);
                break;
            case DataBaseConfig.SQLSERVER_TYPE:
                res = new SqlServerConvertor().cast(node, valueList);
                break;
            default:
                throw new DmpException(ErrorCodeEnum.DB_TYPE_NOT_SUPPORT);
        }
        return res;
    }


    public WrapperNode getNode() {
        return node;
    }

    public void setNode(WrapperNode node) {
        this.node = node;
    }

    public Opt getOpt() {
        return opt;
    }

    public void setOpt(Opt opt) {
        this.opt = opt;
    }

    public Wrapper getNextWrapper() {
        return nextWrapper;
    }

    public void setNextWrapper(Wrapper nextWrapper) {
        this.nextWrapper = nextWrapper;
    }

    public String getSqlClause() {
        return sqlClause;
    }

    public void setSqlClause(String sqlClause) {
        this.sqlClause = sqlClause;
    }


}
