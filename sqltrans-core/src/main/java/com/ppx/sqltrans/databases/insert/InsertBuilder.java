package com.ppx.sqltrans.databases.insert;

import cn.hutool.core.util.ReflectUtil;
import com.inspur.dmp.exception.DmpException;
import com.inspur.dmp.exception.ErrorCodeEnum;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * insert 方法
 */
public class InsertBuilder {

    private StringBuilder insertSb = new StringBuilder("INSERT INTO ");

    private List<String> keys = new ArrayList<>();
    private List<Object> values = new ArrayList<>();

    /**
     * 表名，配合field方法一起使用
     * 勿与fields（T bean, boolean b) 同用
     *
     * @param tableName
     * @return
     */
    public InsertBuilder table(String tableName) {
        if (Objects.isNull(tableName)) {
            throw new DmpException(ErrorCodeEnum.SYS_ERROR.getCode(), "tableName 不能为空");
        }
//        if (!Objects.isNull(sql)) {
//            throw new DmpException(ErrorCodeEnum.SYS_ERROR.getCode(), "sqlClause，table方法不能同时使用");
//        }

        insertSb.append(tableName);
        return this;
    }

    /**
     * DO数据
     * 强限制：全注解  @javax.persistence.Table  javax.persistence.Column
     *
     * @param bean 数据DO
     * @return
     */
    public <T> InsertBuilder fields(T bean, Boolean preserveNullValue) {
        Table annotation = bean.getClass().getAnnotation(Table.class);
        String tableName = annotation.name();
        insertSb.append(tableName);
        Field[] fields = ReflectUtil.getFields(bean.getClass());
        for (Field field : fields) {
            String fieldName = field.getName();

            Column column = field.getAnnotation(Column.class);
            String fieldNameAnno = column.name();
            Object fieldValue = ReflectUtil.getFieldValue(bean, fieldName);
            if (!preserveNullValue && Objects.isNull(fieldValue)) {
                continue;
            }
            this.keys.add(fieldNameAnno);
            this.values.add(fieldValue);
        }
        return this;
    }

    public InsertBuilder fields(Map<String, Object> map, Boolean preserveNullValue) {
        map.forEach((s, o) -> {
            if (!preserveNullValue && Objects.isNull(o)) {
                return;
            }
            this.keys.add(s);
            this.values.add(o);
        });
        return this;
    }

    public InsertBuilder field(String fieldName, Object value) {
        keys.add(fieldName);
        values.add(value);
        return this;
    }

    public String build() {
        insertSb.append(" (").append(StringUtils.join(this.keys, ",")).append(" )")
                .append(" VALUES (")
                .append(StringUtils.repeat("?", ",", keys.size()))
                .append(")");
        return insertSb.toString();
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
