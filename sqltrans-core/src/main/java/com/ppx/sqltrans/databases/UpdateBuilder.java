package com.ppx.sqltrans.databases;

import cn.hutool.core.util.ReflectUtil;
import com.inspur.dmp.constant.Constant;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * insert 方法
 */
public class UpdateBuilder {

    private StringBuilder updateSB = new StringBuilder("UPDATE ");

    private List<String> keys = new ArrayList<>();
    private List<Object> values = new ArrayList<>();


    /**
     * DO数据
     * 强限制：全注解  @javax.persistence.Table  javax.persistence.Column
     *
     * @param bean 数据DO
     * @return
     */
    public <T> com.inspur.dmp.dbaccess.update.UpdateBuilder fields(T bean, Boolean preserveNullValue) {
        Table annotation = bean.getClass().getAnnotation(Table.class);
        String tableName = annotation.name();
        updateSB.append(tableName).append(" SET ");
        Field[] fields = ReflectUtil.getFields(bean.getClass());
        for (Field field : fields) {
            String fieldName = field.getName();

            Column column = field.getAnnotation(Column.class);
            String fieldNameAnno = column.name();
            Object fieldValue = ReflectUtil.getFieldValue(bean, fieldName);
            if (!preserveNullValue && Objects.isNull(fieldValue)) {
                continue;
            }
            this.keys.add(fieldNameAnno + " = ?");
            this.values.add(fieldValue);
        }
        updateSB.append(StringUtils.join(this.keys, ", "));
        return this;
    }

    /**
     * simple equals method
     *
     * @param wrapper
     * @return
     */
    public com.inspur.dmp.dbaccess.update.UpdateBuilder where(Wrapper wrapper) {
        StringBuilder whereClause = new StringBuilder().append(" where ");
        wrapper.convertNode2Sql(Constant.MYSQL_TYPE, whereClause, values);
        this.updateSB.append(whereClause.toString());
        return this;
    }


    public String build() {
        return updateSB.toString();
    }

    /**
     * 获取参数
     *
     * @return
     */
    public Object[] getStatementParams() {
        return this.values.toArray();
    }

    /**
     * @param function String sql语   Object[]参数
     * @return
     */
    public Integer buildWithExec(BiFunction<String, Object[], Integer> function) {
        return function.apply(this.build(), this.values.toArray());
    }
}
