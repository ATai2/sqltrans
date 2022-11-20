package com.ppx.sqltrans.databases.convert;



import com.ppx.sqltrans.DmpException;
import com.ppx.sqltrans.ErrorCodeEnum;
import com.ppx.sqltrans.databases.SqlTypeEnum;
import com.ppx.sqltrans.databases.WrapperNode;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class SqlConvertor implements IConvert {

    @Override
    public String cast(WrapperNode wrapperNode, List valueList) {
        String res = "";
        switch (wrapperNode.getOpt()) {
            case BiLike:
                res = convertBiLike(wrapperNode, valueList);
                break;
            case In:
                res = convertIn(wrapperNode, valueList);
                break;
            default:
                res = convert(wrapperNode, valueList);
                break;

        }
        return res;
    }

    private String convertIn(WrapperNode wrapperNode, List valueList) {

        List list = (List) wrapperNode.getValue();
        if (list.isEmpty()) {
            throw new DmpException(ErrorCodeEnum.SYS_ERROR.getCode(), "in 操作值不能为空");
        }
        StringBuilder res = new StringBuilder();
        List<String> quateList = new ArrayList<>();
        list.forEach(o -> quateList.add("?"));
        if (list.get(0) instanceof String) {
            res.append(wrapperNode.getField() + " in (" + StringUtils.join(quateList, ","));
            res.append(") ");
        }

        valueList.addAll(list);
        return res.toString();
    }

    @Override
    public String convert(WrapperNode wrapperNode, List valueList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(wrapperNode.getField()).append(wrapperNode.getOpt().getOperation()).append("?");

        Object value = wrapperNode.getValue();
        if (SqlTypeEnum.DATE == wrapperNode.getSqlTypeEnum()) {
            String field = wrapperNode.getField();
            wrapperNode.setField(dateFormat(field));
        }

        valueList.add(value);

//        if (value instanceof String) {
//            String valueStr = (String) value;
//            valueList.add(((String) value).replace("_", "/_"));
//        } else {
//            valueList.add(value);
//        }
        return stringBuilder.toString();
    }

//    abstract String


    public String dateFormat(String field) {
        return getDateFormat().replace("$$", field);
    }

    public abstract String getBilike();

    public abstract String getBilikeValue(String origin);

    public abstract String getDateFormat();

    @Override
    public String convertBiLike(WrapperNode wrapperNode, List valueList) {

        String res = getBilike().replace("$field$", wrapperNode.getField());
        valueList.add(getBilikeValue((String) wrapperNode.getValue()));
        return res;
    }
}
