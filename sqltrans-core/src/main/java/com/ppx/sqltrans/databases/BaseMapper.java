package com.ppx.sqltrans.databases;

public interface BaseMapper<T> {

    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     */
    int insert(T entity);

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    public int deleteById(String tableName, String idField, String id);

//    /**
//     * 根据 columnMap 条件，删除记录
//     *
//     * @param columnMap 表字段 map 对象
//     */
//    int deleteByMap(Map<String, Object> columnMap);
//
//
//    /**
//     * 删除（根据ID 批量删除）
//     *
//     * @param idList 主键ID列表(不能为 null 以及 empty)
//     */
//    int deleteBatchIds(Collection<? extends Serializable> idList);
//
    /**
     * 根据 ID 修改
     *
     * @param entity 实体对象
     */
    int updateById(T entity);

    /**
     * 根据 ID 查询
     *
     * @param bean 主键ID 必填
     */
    T selectById(T bean);
//
//    /**
//     * 查询（根据ID 批量查询）
//     *
//     * @param idList 主键ID列表(不能为 null 以及 empty)
//     */
//    List<T> selectBatchIds(Collection<? extends Serializable> idList);
//
//    /**
//     * 查询（根据 columnMap 条件）
//     *
//     * @param columnMap 表字段 map 对象
//     */
//    List<T> selectByMap(Map<String, Object> columnMap);
//
//
//    /**
//     * 根据 Wrapper 条件，查询总记录数
//     *
//     * @param queryWrapper 实体对象封装操作类（可以为 null）
//     */
//    Integer selectCount(Wrapper queryWrapper);
//
//    /**
//     * 根据 entity 条件，查询全部记录
//     *
//     * @param queryWrapper 实体对象封装操作类（可以为 null）
//     */
//    List<T> selectList(Wrapper queryWrapper);
//
//
//    /**
//     * 根据 entity 条件，查询全部记录（并翻页）
//     *
//     * @param page         分页查询条件（可以为 RowBounds.DEFAULT）
//     * @param queryWrapper 实体对象封装操作类（可以为 null）
//     */
//    PageResult<T> selectPage(PageQueryRequest page, Wrapper queryWrapper);
//
//
//    PageResult<T> selectPage(PageQueryRequest page, String sql);
}
