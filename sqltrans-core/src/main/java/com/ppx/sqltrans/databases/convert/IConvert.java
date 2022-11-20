package com.ppx.sqltrans.databases.convert;



import com.ppx.sqltrans.databases.WrapperNode;

import java.util.List;

/**
 * 与数据库类型相关的语法处理都在convertor处理
 */
public interface IConvert {

    /**
     * 逻辑执行
     * @param wrapperNode
     * @return
     */


    String cast(WrapperNode wrapperNode, List valueList);

    /**
     * 普通转换
     * @param wrapperNode
     * @param valueList
     * @return
     */
    public String convert(WrapperNode wrapperNode, List valueList);

    /**
     * 左右模糊查询转换
     * @param wrapperNode
     * @param valueList
     * @return
     */
    public String convertBiLike(WrapperNode wrapperNode, List valueList);
}
