//package com.ppx.sbsql.service;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FileUtils;
//import org.apache.ibatis.jdbc.ScriptRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.sql.DataSource;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.List;
//import java.util.function.Consumer;
//
//@Slf4j
//@Service
//public class ImportSqlFileService {
//
//    @Autowired
//    private DataSource dataSource;
//
//    public void excuteFile(String[] args) {
//        try (Connection conn = dataSource.getConnection()) {
////            设置不自动提交
//            conn.setAutoCommit(false);
//            ScriptRunner runner = new ScriptRunner(conn);
//            runner.setStopOnError(true);
//            runner.setSendFullScript(false);
//            //          定义命令间的分隔符
////            runner.setDelimiter(";");
////            runner.setFullLineDelimiter(false);
//
//            String path = "D:\\doc\\BA-DMP-DOC\\项目管理\\数据管理平台202007\\03数据库设计\\02DMP2.4~2.5升级sql\\Sqlserver\\dml";
//
//            File file = new File(path);
//
//            String[] list = file.list();
//            for (int i = 0; i < list.length; i++) {
//                log.info("执行文件：" + list[i]);
//                try {
//                    File sqlFile = new File(path + File.separator + list[i]);
////                    log.info("执行：" + FileUtils.readFileToString(sqlFile));
//                    runner.runScript(new InputStreamReader(new FileInputStream(sqlFile), "utf-8"));
//                } catch (Exception e) {
//
//                }
//            }
//
//
//            conn.commit();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//
//    public boolean excute(String[] args) {
//
////        if (args.length == 0) {
////            log.info("无参数，请检查配置命令行");
////            return false;
////        }
//
//        try (
//                Connection connection = dataSource.getConnection();
//                Statement statement = connection.createStatement();
//        ) {
//            String path = "D:\\doc\\BA-DMP-DOC\\项目管理\\数据管理平台202007\\03数据库设计\\02DMP2.4~2.5升级sql\\Sqlserver\\dml";
//
//            File file = new File(path);
//
//            String[] list = file.list();
//            for (int i = 0; i < list.length; i++) {
//                log.info("执行文件：" + list[i]);
//                try {
//                    String sqlList = FileUtils.readFileToString(new File(path + File.separator + list[i]));
//                    log.info("执行：" + sqlList);
//
////                    List<String> sqlList = FileUtils.readLines(new File(path+File.separator+list[i]));
//////                    log.info("执行："+s);
////                    sqlList.stream().forEach(new Consumer<String>() {
////                        @Override
////                        public void accept(String s) {
////                            if (s.contains("go"))
////
////
////                        }
////                    });
////                    s = s.replace("go", "");
////                    statement.addBatch(s);
////                    int[] execute = statement.executeBatch();
////                    log.info(execute+" result。");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//        return false;
//    }
//
//}
