//package com.ppx.sqltrans;
//
//import com.alibaba.druid.sql.SQLUtils;
//import com.alibaba.druid.sql.ast.SQLExpr;
//import com.alibaba.druid.sql.ast.SQLStatement;
//import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
//import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
//import com.alibaba.druid.sql.parser.SQLStatementParser;
//import com.alibaba.druid.support.json.JSONUtils;
//import com.ppx.sqltrans.tools.Tools;
//import org.junit.Test;
//
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class SqlCastTest {
//
//    @Test
//    public void mysql2Oracle() {
//    }
//
//    @Test
//    public void showDatabases() {
//        String mysqlStr = "show databases;";
//        System.out.println(SQLUtils.formatMySql(mysqlStr));
////        SQLUtils.parseStatements(mysqlStr, );
////        SQLExpr sqlExpr = SQLUtils.toMySqlExpr(mysqlStr);
////
////        System.out.println(JSONUtils.toJSONString(sqlExpr));
//
//    }
//
//    @Test
//    public void createTable(){
//        String str="CREATE TABLE IF NOT EXISTS `runoob_tbl`(\n" +
//                "   `runoob_id` INT UNSIGNED AUTO_INCREMENT,\n" +
//                "   `runoob_title` VARCHAR(100) NOT NULL,\n" +
//                "   `runoob_author` VARCHAR(40) NOT NULL,\n" +
//                "   `submission_date` DATE,\n" +
//                "   PRIMARY KEY ( `runoob_id` )\n" +
//                ")ENGINE=InnoDB DEFAULT CHARSET=utf8;";
//        SQLStatementParser parser=new MySqlStatementParser(str);
//        List<SQLStatement> stmtList = parser.parseStatementList();
//
//        // 将AST通过visitor输出
//        StringBuilder out = new StringBuilder();
//        MySqlOutputVisitor visitor = new MySqlOutputVisitor(out);
//
//        for (SQLStatement stmt : stmtList) {
//            stmt.accept(visitor);
//            out.append(";");
//
//            String x = Tools.outputOracle(stmt);
//            System.out.println(x);
//        }
//
//        System.out.println(out.toString());
//        int a=3;
////        SQLExpr sqlExpr = SQLUtils.toMySqlExpr(str);
////        System.out.println(JSONUtils.toJSONString(sqlExpr));
//
//
//    }
//}