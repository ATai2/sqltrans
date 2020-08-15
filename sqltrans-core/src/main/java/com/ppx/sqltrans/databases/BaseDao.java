package com.ppx.sqltrans.databases;

import cn.hutool.core.util.ReflectUtil;
import com.inspur.dmp.dbaccess.delete.DeleteBuilder;
import com.inspur.dmp.dbaccess.insert.InsertBuilder;
import com.inspur.dmp.dbaccess.select.SelectBuilder;
import com.inspur.dmp.dbaccess.update.UpdateBuilder;
import com.inspur.dmp.exception.DmpException;
import com.inspur.dmp.exception.ErrorCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Only support simple JDBC operation.
 * If complex database operation is required, please implement your method by support classes.
 * Go to package dbaccess.
 * todo Function duplicated with JPA, considering if this util is required.
 * NOTICE:  need to inject database.
 * @param <T>
 */
public class BaseDao<T> implements BaseMapper<T> {

    Logger logger = LoggerFactory.getLogger(com.inspur.dmp.dbaccess.BaseDao.class);
    private Database database;

    public void setConnection(Database database) {
        this.database = database;
    }


    @Override
    public int insert(T entity) {
        return new InsertBuilder().fields(entity, false)
                .buildWithExec((s, objects) -> {
                    try {
                        System.out.println(s);
                        System.out.println(Arrays.toString(objects));
                        return database.execSqlStatment(s, objects);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        logger.error("插入数据失败");
                        return 0;
                    }
                });
    }

    @Override
    public int deleteById(String tableName, String idField, String id) {
        return new DeleteBuilder().table(tableName)
                .where(Wrapper.of(idField, Opt.Equals, id))
                .buildWithExec((s, objects) -> {
                    try {
                        return database.execSqlStatment(s, objects);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        throw new DmpException(ErrorCodeEnum.DB_OPERA_DELETE);
                    }
                });
    }

    //    @Override
//    public int deleteByMap(Map<String, Object> columnMap) {
//        return 0;
//    }
//
//    @Override
//    public int deleteBatchIds(Collection<? extends Serializable> idList) {
//        return 0;
//    }
//
    @Override
    public int updateById(T bean) {
        Optional<Field> first = findIdField(bean);
        if (first.isPresent()) {
            Field field = first.get();
            String fieldName = field.getName();
            Column column = field.getAnnotation(Column.class);
            String fieldNameAnno = column.name();

            UpdateBuilder updateBuilder = new UpdateBuilder();
            updateBuilder.fields(bean, false)
                    .where(Wrapper.of(fieldNameAnno, Opt.Equals, ReflectUtil.getFieldValue(bean, fieldName)));
            String sql = updateBuilder.build();
            Object[] statementParams = updateBuilder.getStatementParams();

            try {
                return database.execSqlStatment(sql, statementParams);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw new DmpException(ErrorCodeEnum.SYS_ERROR.getCode(), "更新数据失败");
            }
        } else {
            throw new DmpException(ErrorCodeEnum.SYS_ERROR.getCode(), "更新数据失败");
        }
    }

    @Override
    public T selectById(T bean) {
        Optional<Field> first = findIdField(bean);
        if (first.isPresent()) {
            Field idField = first.get();
            String idFieldName = idField.getName();
            Column column = idField.getAnnotation(Column.class);
            String idFieldNameAnno = column.name();

            SelectBuilder selectBuilder = new SelectBuilder()
                    .fieldsClass(bean.getClass())
                    .where(Wrapper.of(idFieldNameAnno, Opt.Equals, ReflectUtil.getFieldValue(bean, idFieldName)));
            String sql = selectBuilder.build();
            Object[] statementParams = selectBuilder.getStatementParams();
            try {
                List<?> objectList = database.getObjectList(sql, statementParams, bean.getClass());
                if (objectList.size() == 1) {
                    return (T) objectList.get(0);
                } else if (objectList.size() == 0) {
                    throw new DmpException(ErrorCodeEnum.DB_OPERA_SELECT.getCode(), "查无此id数据");
                } else {
                    throw new DmpException(ErrorCodeEnum.DB_OPERA_SELECT.getCode(), "id数据重复");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                logger.error("查无此id数据");
                throw new DmpException(ErrorCodeEnum.DB_OPERA_SELECT.getCode(), "查无此id数据");
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                throw new DmpException(ErrorCodeEnum.DB_OPERA_SELECT.getCode(), "查无此id数据");
            }
        } else {
            throw new DmpException(ErrorCodeEnum.SYS_ERROR);
        }
    }

    private Optional<Field> findIdField(T bean) {
        Field[] fields = bean.getClass().getDeclaredFields();
        return Arrays.asList(fields).stream().filter(field -> !Objects.isNull(field.getAnnotation(Id.class))).findFirst();
    }
//
//    @Override
//    public List<T> selectBatchIds(Collection<? extends Serializable> idList) {
//        return null;
//    }
//
//    @Override
//    public List<T> selectByMap(Map<String, Object> columnMap) {
//        return null;
//    }
//
//    @Override
//    public Integer selectCount(Wrapper queryWrapper) {
//        return null;
//    }
//
//    @Override
//    public List<T> selectList(Wrapper queryWrapper) {
//        return null;
//    }
//
//    /**
//     * 简单查询  与，或条件平级
//     * @param page         分页查询条件（可以为 RowBounds.DEFAULT）
//     * @param queryWrapper 实体对象封装操作类（可以为 null）
//     * @return
//     */
//    @Override
//    public   PageResult<T> selectPage(PageQueryRequest page, Wrapper queryWrapper) {
//        return null;
//    }
//

//    @Override
//    public   PageResult<T> selectPage(PageQueryRequest page, String sql) {
//        PageQueryBuilder queryBuilder=new PageQueryBuilder();
//        Type type = ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
////        获取表名
//
////        拼接where条件
//
////        queryBuilder.table(type.getTypeName())
//
//
//        return null;
//    }
}
