package com.ppx.example.utils;

import com.ppx.example.DataServiceModel;
import com.ppx.example.ServiceParam;
import org.junit.Test;

import static org.junit.Assert.*;

public class BeanFieldsCastUtilTest {

    @Test
    public void castFields() {
        String str = "insert into SYSSERVICE (id, code, caption, description, stype, requesturl, createtime, updatetime, creator, requestway, system, groupid) values \" +\n" +
                "                \"('$id$', '$apiCode$', '$apiCaption$', '$apiDesc$', $apiType$, \" +\n" +
                "                \"'$apiUrl$',  getdate(), getdate(), 'scott', 2, 1, '1210');\\n\"";

        ServiceParam.ServiceParamBuilder builder = ServiceParam.builder().id("2323").parametername("dede");
        DataServiceModel dataServiceModel = DataServiceModel.builder().id("23")
                .code("apiii").caption("cap")
                .desc("ssss").requestUrl("http://")

                .build();

        BeanFieldsCastUtil.castFields(str, builder.build());

    }

}