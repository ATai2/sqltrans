//package com.ppx.example;
//
//import org.junit.Test;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.Assert.*;
//
//public class MainTest {
//
//
//    @Test
//    public void dasvApiGenerator() {
//
//        String uuid = UUID.randomUUID().toString();
//        DataServiceModel serviceParam=new DataServiceModel(uuid,"uploadFile","","0","","");
//        List<ServiceParam> serviceParams = Arrays.asList(
//                new ServiceParam.ServiceParamBuilder().id(uuid).parametername("").build(),
//        );
//        String mssStr = replaceHeader(SqlDatabaseConfig.mssqlParamMap.get(SqlDatabaseConfig.header),)
//
//
//        System.out.println(mssStr);
//    }
//
//    String replaceHeader(String header, ServiceParam serviceParam) {
//        return header.replace("$id$", serviceParam.getId());
//    }
//
//    String replaceSerceField(String sql, DataServiceModel serviceParam) {
//        sql.replace("$id$", serviceParam.getId());
//        sql.replace("$apiCode$", serviceParam.getCode());
//        sql.replace("$apiCaption$", serviceParam.getCaption());
//        sql.replace("$apiDesc$", serviceParam.getDesc());
//        sql.replace("$apiType$", serviceParam.getType());
//        sql.replace("$apiUrl$", serviceParam.getRequestUrl());
//        return sql;
//    }
//
//    String replaceParamField(String sql, ServiceParam serviceParam) {
//        sql.replace("$id$", serviceParam.getId());
//        sql.replace("$xh$", serviceParam.getXh() + "");
//        sql.replace("$pName$", serviceParam.getParametername());
//        sql.replace("$pPosition$", serviceParam.getParameterposition() + "");
//        sql.replace("$pDefaultValue$", serviceParam.getDefaultvalue());
//        sql.replace("$pType$", serviceParam.getParametertype());
//        sql.replace("$pDesc$", serviceParam.getDescription());
//
//        return sql;
//    }
//}