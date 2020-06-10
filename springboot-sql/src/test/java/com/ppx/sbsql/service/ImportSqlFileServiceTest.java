package com.ppx.sbsql.service;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.apache.tools.ant.types.EnumeratedAttribute;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImportSqlFileServiceTest {


    @Test
    public void test_batch() throws Exception {
        String sqlFile = "D:\\code\\sqltrans\\springboot-sql\\src\\test\\resources\\upgrade_DASV_mss_dml.sql";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=paas";
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String username = "SA";
        String password = "asdfF@1234";

        Class.forName(driver).newInstance();
        Connection conn = DriverManager.getConnection(url, username, password);
        Statement stmt = null;
        List<String> sqlList = loadSql(sqlFile);
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            for (String sql : sqlList) {
                if (sql.trim().length() > 0) {
                    stmt.addBatch(sql);
                }
            }
//            stmt.get
            int[] rows = stmt.executeBatch();
            System.out.println("Row count:" + Arrays.toString(rows));

        } catch (Exception ex) {

            throw ex;
        } finally {

        }

    }

    private List<String> loadSql(String sqlFile) throws Exception {
        List<String> sqlList = new ArrayList<String>();

        try {
            List<String> strings = FileUtils.readLines(new File(sqlFile));
            for (int i = 0; i < strings.size(); i++) {
                String line = strings.get(i);
                line = line.replaceAll("\\r", "").replaceAll("\\n", "");
//                line=
                if ("go".equalsIgnoreCase(line)) {
                    continue;
                }
                sqlList.add(line);
            }
            System.out.println(sqlList);
            return sqlList;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private List<String> loadSqlOrigin(String sqlFile) throws Exception {
        List<String> sqlList = new ArrayList<String>();

        try {
            InputStream sqlFileIn = new FileInputStream(sqlFile);

            StringBuffer sqlSb = new StringBuffer();
            byte[] buff = new byte[1024];
            int byteRead = 0;
            while ((byteRead = sqlFileIn.read(buff)) != -1) {
                sqlSb.append(new String(buff, 0, byteRead));
            }

            // Windows 下换行是 /r/n, Linux 下是 /n
            String[] sqlArr = sqlSb.toString().split("(;//s*//r//n)|(;//s*//n)");
            for (int i = 0; i < sqlArr.length; i++) {
                String sql = sqlArr[i].replaceAll("--.*", "").trim();
                if (!sql.equals("")) {
                    if ("go".equalsIgnoreCase(sql)) {
                        continue;
                    }

                    sqlList.add(sql);
                }
            }
            System.out.println(sqlList);
            return sqlList;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void execute(Connection conn, String sqlFile) throws Exception {
        Statement stmt = null;
        List<String> sqlList = loadSql(sqlFile);
        stmt = conn.createStatement();
        for (String sql : sqlList) {
            stmt.addBatch(sql);
        }
        int[] rows = stmt.executeBatch();
        System.out.println("Row count:" + Arrays.toString(rows));
    }

    /**
     * 自建连接，独立事物中执行 SQL 文件
     *
     * @param sqlFile SQL 脚本文件
     * @throws Exception
     */
    public void execute(String sqlFile) throws Exception {

    }

    @Test
    public void test_mybatis() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=paas";
            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String username = "SA";
            String password = "asdfF@1234";

            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url, username, password);
            ScriptRunner runner = new ScriptRunner(conn);
            Resources.setCharset(Charset.forName("utf-8")); //设置字符集,不然中文乱码插入错误
            runner.setLogWriter(null);//设置是否输出日志
            runner.setSendFullScript(true);

            runner.runScript(Resources.getResourceAsReader("upgrade_DASV_mss_dml.sql"));
//            runner.runScript(Resources.getResourceAsReader("sql/CC21-01.sql"));
            runner.closeConnection();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_ant() {
        SQLExec sqlExec = new SQLExec();
//设置数据库参数
        sqlExec.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        sqlExec.setUrl("jdbc:sqlserver://localhost:1433;databaseName=paas");
        sqlExec.setUserid("SA");
        sqlExec.setPassword("asdfF@1234");
//要执行的脚本
        sqlExec.setSrc(new File("D:\\code\\sqltrans\\springboot-sql\\src\\test\\resources\\upgrade_DASV_mss_dml.sql"));
//有出错的语句该如何处理
        sqlExec.setOnerror((SQLExec.OnError) (EnumeratedAttribute.getInstance(
                SQLExec.OnError.class, "abort")));
        sqlExec.setPrint(true); //设置是否输出
//输出到文件 sql.out 中；不设置该属性，默认输出到控制台
        sqlExec.setOutput(new File("src/sql.out"));
        sqlExec.setProject(new Project()); // 要指定这个属性，不然会出错
        sqlExec.execute();
    }

}