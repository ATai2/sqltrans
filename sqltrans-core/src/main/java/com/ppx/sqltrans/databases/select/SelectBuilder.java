package com.ppx.sqltrans.databases.select;

import cn.hutool.core.util.ReflectUtil;
import com.inspur.dmp.constant.Constant;
import Wrapper;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * insert 方法
 */
public class SelectBuilder {

    private StringBuilder selectSB = new StringBuilder("SELECT ");

    private List<String> keys = new ArrayList<>();
    private List<Object> values = new ArrayList<>();



    public SelectBuilder where(Wrapper wrapper) {
        selectSB.append(" where ");
        wrapper.convertNode2Sql(Constant.MYSQL_TYPE, selectSB, values);
        return this;
    }


    public String build() {
        return selectSB.toString();
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

    public <T> SelectBuilder fieldsClass(Class<T> tClass) {
        Field[] fields = ReflectUtil.getFields(tClass);
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            String fieldNameAnno = column.name();
            String fieldName = field.getName();

            this.keys.add(fieldNameAnno + " AS '" +fieldName+"'");
        }
        selectSB.append(StringUtils.join(this.keys,",")).append(" from ").append(tClass.getAnnotation(Table.class).name());
        return this;
    }
}
