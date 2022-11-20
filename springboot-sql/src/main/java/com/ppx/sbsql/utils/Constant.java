package com.ppx.sbsql.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据管理平台常量类
 * @ClassName: Contant 
 * @Description: 数据管理平台常量类
 * @author: jingcc
 * @date: 2018年7月23日 下午4:07:23
 */
public class Constant {
	
	/**
	 * 数据库类型
	 */
	public static final String DBTYPE = "DBTYPE";
	/**
	 * SQLSERVER类型
	 */
	public static final String SQLSERVER_TYPE = "MSS";
	
	/**
	 * ORACLE类型
	 */
	public static final String ORACLE_TYPE = "ORA";
	/**
	 * MariaDb类型
	 */
	public static final String MARIADB_TYPE="MARIADB";
	/**
	 * MySQL类型
	 */
	public static final String MYSQL_TYPE="MYSQL";
	/**
	 * 人大金仓
	 */
	public static final String KINGBASE_TYPE="KINGBASE";
	/**
	 * 达梦
	 */
	public static final String DAMENG_TYPE="DAMENG";

    /**
     * 支持JPA的数据库
     */
	public static final List<String> DATABASE_JPA_LIST = new ArrayList<String>(){{
	    add(SQLSERVER_TYPE);
	    add(ORACLE_TYPE);
	    add(DAMENG_TYPE);
	    add(MYSQL_TYPE);
	}};
	
	/**
	 * 超级管理员用户名编号（登录名）
	 */
	public static final String ADMINISTRATOR = "scott";
	
	/**
	 * 运行区转换节点类型
	 */
	public static final String RUNAREA_ALLTRANS_NODETYPE = "320";
	
	/**
	 * 运行区作业节点类型
	 */
	public static final String RUNAREA_ALLJOB_NODETYPE = "321";
	
	/**
	 * 设计区具体转换类型
	 */
	public static final String DESIGNAREA_TRANS_NODETYPE = "252";
	/**
	 * 运行区具体转换类型
	 */
	public static final String RUNAREA_TRANS_NODETYPE = "330";
	
	/**
	 * 运行区具体作业类型
	 */
	public static final String RUNAREA_JOB_NODETYPE = "331";
	
	/**
	 * 数据源节点ID
	 */
	public static final String DASM_ID="iDS";
	/**
	 * 数据加工厂节点ID
	 */
	public static final String DAFY_ID="iDF";
	/**
	 * 数据质量节点ID
	 */
	public static final String DAQC_ID="iDQ";
	
	/**
	 * 集群管理节点
	 */
	public static final String CLUSTER_ID="iHD";
	
	/**
	 * 聚数节点
	 */
	public static final String IDI_ID="iDI";
	/**
	 * 运行区节点
	 */
	public static final String RUN_ID="iRUN";
	/**
	 * 系统管理节点ID
	 */
	public static final String SYSTEM_ID="SYS";
	/**
	 * 规则菜单
	 */
	public static final String RULES_ID="RULES";
	/**
	 * 常量0
	 */
	public static final String ZERO="0";
	/**
	 * 常量1
	 */
	public static final String ONE="1";
	/**
	 * 常量2
	 */
	public static final String TWO="2";
	/**
	 * 常量3
	 */
	public static final String THREE="3";
	/**
	 * 常量4
	 */
	public static final String FOUR="4";
	/**
	 * 常量5
	 */
	public static final String FIVE="5";
	/**
	 * 常量6
	 */
	public static final String SIX="6";
	/**
	 * 常量7
	 */
	public static final String SEVEN="7";
	/**
	 * 常量8
	 */
	public static final String EIGHT="8";
	/**
	 * 常量9
	 */
	public static final String NINE="9";
	/**
	 * 常量10
	 */
	public static final String TEN="10";
	/**
	 * 常量11
	 */
	public static final String ELEVEN = "11";
	/**
	 * 常量2
	 */
	public static final String TWELVE = "12";
	/**
	 * 常量13
	 */
	public static final String THIRTEEN = "13";
	/**
	 * 数据字典节点
	 */
	public static final String DATA_DICTIONARY = "1320";
	/**
	 * 集成系统名称：无，使用dmp自身权限
	 */
	public static final String INTEGRATED_SYSTEM_NONE="none";
	/**
	 * 集成系统名称，一站式平台
	 */
	public static final String INTEGRATED_SYSTEM_EAP="eap";
	
	/**
	 * ORACLE JDBC返回类型
	 */
	public static final String ORACLE_DBTYPE="ORA";
	/**
	 * SQLSERVER JDBC返回类型
	 */
	public static final String SQLSERVER_DBTYPE="MSS";
	/**
	 * MYSQL JDBC返回类型
	 */
	public static final String MYSQL_DBTYPE="MYSQL";
	/**
	 * MARIADB JDBC返回类型
	 */
	public static final String MARIADB_DBTYPE="MariaDB";
	/**
	 * KINGBASE JDBC返回类型
	 */
	public static final String KINGBASE_DBTYPE="KingbaseES";
	/**
	 * HIVE JDBC返回类型
	 */
	public static final String HIVE_DBTYPE="Apache Hive";
	/**
	 * DAMENG JDBC返回类型
	 */
	public static final String DAMENG_DBTYPE="DM DBMS";
	/**
	 * GREENPLUM JDBC返回类型
	 */
	public static final String GREENPLUM_DBTYPE="Greenplum";
	/**
	 * POSTGRESQL JDBC返回类型
	 */
	public static final String POSTGRESQL_DBTYPE="PostgreSQL";
	/**
	 * quartz触发器组名称
	 */
	public  static final String TRIGGER_GROUP_NAME="EXTJWEB_TRIGGERGROUP_NAME";
	public static final int HTTP_CODE_CODE_SUCCESS = 200;
}
