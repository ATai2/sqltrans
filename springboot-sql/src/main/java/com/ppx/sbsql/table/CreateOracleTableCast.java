package com.ppx.sbsql.table;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.util.StringUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class CreateOracleTableCast extends CreateTableCast {

    public String cast(CreateTableModel createTableModel) {
       return super.cast(createTableModel, "oracle", new Function<CreateTableColumn, String>() {
            @Override
            public String apply(CreateTableColumn createTableColumn) {

                StringBuilder sb = new StringBuilder();
                sb.append(SPACE4).append(createTableColumn.getFieldName());
                String fieldType = createTableColumn.getFieldType();
                if ("varchar".equalsIgnoreCase(fieldType.trim())) {
                    sb.append(SPACE).append("varchar2(").append(createTableColumn.getFieldLength()).append(")").append(SPACE);
                }
                if ("char".equalsIgnoreCase(fieldType.trim())) {
                    sb.append(SPACE).append("char(").append(createTableColumn.getFieldLength()).append(")").append(SPACE);
                }

                if (createTableColumn.getNotNull()) {
                    sb.append(NOT_NULL).append(SPACE);
                }

                if (createTableColumn.getPk()) {
                    sb.append(PK).append(SPACE);
                }
                if (createTableColumn.getUnique()) {
                    sb.append(UNIQUE).append(SPACE);
                }

                String e = sb.toString().replaceAll(" ,", ",");
                return e;
            }
        });
    }

}
