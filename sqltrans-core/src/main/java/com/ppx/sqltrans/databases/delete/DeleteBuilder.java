package com.ppx.sqltrans.databases.delete;


import com.ppx.sqltrans.DmpException;
import com.ppx.sqltrans.ErrorCodeEnum;
import com.ppx.sqltrans.databases.Wrapper;
import com.ppx.sqltrans.tools.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * delete
 */
public class DeleteBuilder {

    private StringBuilder deleteSB = new StringBuilder("DELETE FROM ");

    private List<String> keys = new ArrayList<>();
    private List<Object> valueList = new ArrayList<>();

    /**
     * 表名，配合field方法一起使用
     * 勿与fields（T bean, boolean b) 同用
     *
     * @param tableName
     * @return
     */
    public DeleteBuilder table(String tableName) {
        if (Objects.isNull(tableName)) {
            throw new DmpException(ErrorCodeEnum.SYS_ERROR.getCode(), "tableName 不能为空");
        }
        deleteSB.append(tableName);
        return this;
    }

    /**
     * simple equals method
     *
     * @param wrapper
     * @return
     */
    public DeleteBuilder where(Wrapper wrapper) {
        StringBuilder whereClause = new StringBuilder(deleteSB).append(" where ");
        wrapper.convertNode2Sql(Constant.MYSQL_TYPE, whereClause, valueList);
        this.deleteSB.append(whereClause.toString());
        return this;
    }

    public String build() {
        return deleteSB.toString();
    }



    /**
     * @param function String sql语   Object[]参数
     * @return
     */
    public Integer buildWithExec(BiFunction<String, Object[], Integer> function) {
        return function.apply(this.build(), this.valueList.toArray());
    }

}
