package com.ppx.sqltrans.databases;

import com.ppx.sqltrans.tools.Constant;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

/**
 * 数据库操作类，该类通过指定一个连接池id，连接到不同的数据库连接池。</br>
 * 每个数据库操作实例维护一个{@link Connection}对象，该{@link Connection}对象是从指定的连接池中获取到的一个数据库连接。</br>
 * 该类基于PreparedStatement实现，如果SQL语句中需要绑定变量，请使用“?”作为占位符。</br>
 * <p>示例：</p>
 * <ol>
 *  <li>判断数据库是否可连通：boolean canConnect = Database.canConnect("dbid");</li>
 * 	<li>通过sql查询并返回数据：
 * 		Database instance = Database.getInstance("dbid");	//获取数据库操作实例</br>
 * 		List dataList = instance.getDataList("select * from 表 where 条件");	//进行数据库操作</li>
 * </ol>
 * <p>
 * caution: not Singleton
 *
 * @author linchuan
 * @since 2018.6.6
 */

@Slf4j
public class Database {

//	@Value("${spring.datasource.driver-class-name}")
	private String className;
//	@Value("${spring.datasource.url}")
	private String url;
//	@Value("${spring.datasource.username}")
	private String username;
//	@Value("${spring.datasource.password}")
	private String password;

	/**
	 * 数据库连接
	 */
	private Connection conn = null;
	/**
	 * 数据库配置
	 */
	private  ConnConfig connConfig = null;


	private Database(Connection conn, ConnConfig connConfig) {
		this.conn = conn;
		this.connConfig = connConfig;
	}

	/**
	 * 获取系统默认（database.properties配置文件中）的Database实例
	 *
	 * @return 数据库操作实例
	 * @throws IOException  加载配置文件出错
	 * @throws SQLException
	 */
	public static Database getInstance() {
		return null;
//		DataSourceModel dataSourceModel = (DataSourceModel) SpringApplicationContext.getBean("dataSourceModel");
//		String className = dataSourceModel.getDriveClassName();
//		String url = dataSourceModel.getUrl();
//		String username = dataSourceModel.getUserName();
//		String password = dataSourceModel.getPassword();
//		ConnConfig config = new ConnConfig();
//		config.setDriverClass(className);
//		config.setJdbcUrl(url);
//		config.setUserName(username);
//		config.setPassword(password);
//		return getInstance(config);
	}

	/**
	 * 获取一个新的Database实例
	 *
	 * @param dbcpID 数据源id
	 * @return 数据库操作实例
	 * @throws SQLException
	 */
	public static Database getInstance(String dbcpID) {
		ConnConfig config = getConnConfig(dbcpID);
		return getInstance(config);
	}

	/**
	 * 获取一个新的Database实例
	 *
	 * @param connConfig 数据库连接实体类{@link ConnConfig}
	 * @return 数据库操作实例
	 * @throws SQLException
	 */
	public static Database getInstance(ConnConfig connConfig) {
		Connection conn = null;
		try {
			conn = produceConn(connConfig);
		} catch (SQLException e) {
			throw new RuntimeException("获取数据库连接失败！", e);
		}
		Database db = new Database(conn, connConfig);

		return db;
	}

	/**
	 * 销毁数据连接池
	 *
	 * @param poolID 数据库连接池id
	 */
	public static void destroyConnPool(String poolID) {
		//C3P0PoolManager.getInstance().destroy(poolID);

	}

	/**
	 * 开始事务
	 *
	 * @throws SQLException
	 */
	public void beginTransaction() throws SQLException {
		getConn();
		this.conn.setAutoCommit(false);
	}

	/**
	 * 结束事务
	 *
	 * @throws SQLException
	 */
	public void endTransaction() throws SQLException {
		try {
			this.conn.commit();
			this.conn.setAutoCommit(true);
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			releasConn();
		}
	}

	/**
	 * 事务回滚
	 *
	 * @throws SQLException
	 */
	public void rollback() throws SQLException {
		try {
			this.conn.rollback();
			this.conn.setAutoCommit(true);
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			releasConn();
		}
	}

	/**
	 * 判断数据库是否可以连接，请使用静态方法的方式调用，否则无法返回正确结果
	 *
	 * @param connConfig 数据库连接配置
	 * @return
	 * @throws SQLException
	 */
	public static boolean canConnect(ConnConfig connConfig) {
		try {
			Database database = getInstance(connConfig);
			database.releasConn();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//C3P0PoolManager.getInstance().destroy(connConfig.getPoolID());
		}
		return false;
	}

	/**
	 * 判断数据库是否可以连接，请使用静态方法的方式调用，否则无法返回正确结果
	 *
	 * @param dbcpID 数据源id
	 * @return
	 * @throws SQLException
	 */
	public static boolean canConnect(String dbcpID) {
		try {
			Database database = getInstance(dbcpID);
			database.releasConn();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			C3P0PoolManager.getInstance().destroy(dbcpID);
		}
		return false;
	}

	/**
	 * 执行sql语句，可执行insert，update，delete等sql语句
	 *
	 * @param sql 要执行的操作sql语句
	 * @return dml语句操作后受影响的行数（更新的记录数）
	 * @throws SQLException
	 */
	public int execSqlStatment(String sql) throws SQLException {
		return this.execSqlStatment(sql, new Object[]{});
	}

	/**
	 * 执行sql语句，可执行insert，update，delete等sql语句
	 *
	 * @param sql    要执行的操作sql语句
	 * @param params 参数
	 * @return dml语句操作后受影响的行数（更新的记录数）
	 * @throws SQLException
	 */
	public int execSqlStatment(String sql, Object[] params) throws SQLException {
		PreparedStatement ps = null;
		int result = 0;

		try {
			getConn();
			ps = conn.prepareStatement(sql);

			if (null != params && params.length != 0) {
				for (int i = 0; i < params.length; i++) {
					if (params[i] instanceof InputStream) {
						ps.setBinaryStream(i + 1, (InputStream) params[i]);
					} else if (params[i] instanceof byte[]) {
						ps.setBytes(i + 1, (byte[]) params[i]);
					} else {
						ps.setObject(i + 1, params[i]);
					}

				}
			}

			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			outputSqlStatment(sql, params);
			releasResource(ps, null);
		}

		return result;
	}

	/**
	 * 批量执行数据库操作，可执行insert，update，delete等sql语句
	 *
	 * @param sql    要执行的sql语句
	 * @param params 参数
	 * @return dml语句操作后受影响的行数（更新的记录数）
	 * @throws SQLException
	 */
	public int[] execSqlStatment(String sql, List<Object[]> params) throws SQLException {
		PreparedStatement ps = null;
		int[] rowsAffectedArray = null;

		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);

			List<Integer> rowsAffected = new ArrayList<Integer>();
			for (Object[] param : params) {
				for (int i = 0; i < param.length; i++) {
					if (param[i] instanceof InputStream) {
						ps.setBinaryStream(i + 1, (InputStream) param[i]);
					} else if (param[i] instanceof byte[]) {
						ps.setBytes(i + 1, (byte[]) param[i]);
					} else {
						ps.setObject(i + 1, param[i]);
					}
				}

				//oracle调用此方法时出错，原因暂未知，可能是原生的addBatch方法与oracle驱动实现的addBatch有冲突
				//（错误：operation cannot be mixed with Oracle-style batching）
				//ps.addBatch();
				rowsAffected.add(ps.executeUpdate());
			}

			rowsAffectedArray = new int[rowsAffected.size()];
			for (int i = 0; i < rowsAffectedArray.length; i++) {
				rowsAffectedArray[i] = rowsAffected.get(i);
			}

			//int[] result = ps.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new SQLException(e1);
			}
			throw new SQLException(e);
		} finally {
			outputBatchSqlStatment(sql, params);
			//释放资源
			releasResource(ps, null);
		}

		return rowsAffectedArray;
	}

	/**
	 * 批量执行数据库操作
	 *
	 * @param sqls 要执行的sql语句
	 * @return 每条sql语句操作后受影响的行数（更新的记录数）
	 * @throws SQLException
	 */
	public int[] execSqlStatment(String[] sqls) throws SQLException {
		Statement st = null;
		int[] affectedArray = null;

		try {
			st = conn.createStatement();
			for (String sql : sqls) {
				st.addBatch(sql);
			}
			affectedArray = st.executeBatch();
			st.clearBatch();
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			outputSqlStatment(sqls);
			//释放资源
			releasResource(st, null);
		}
		return affectedArray;
	}

	/**
	 * 执行存储过程
	 *
	 * @param sql      执行存储过程语句
	 * @param inParams 输入参数
	 * @param outTypes 输出参数类型
	 * @return 存储过程的调用对象，通过它可以获取存储过程调用后的返回值（如果有），使用后请务必将其关闭。
	 * @throws SQLException
	 * @author linchuan
	 * @date 2018年9月28日
	 */
	public CallableStatement execSqlProcedure(String sql, Object[] inParams, int[] outTypes) throws SQLException {
		CallableStatement cstmt = null;

		try {
			cstmt = conn.prepareCall(sql);

			if (null != inParams && inParams.length != 0) {
				for (int i = 0; i < inParams.length; i++) {
					cstmt.setObject(i + 1, inParams[i]);
				}
			}

			if (null != outTypes && outTypes.length != 0) {
				for (int i = 0; i < outTypes.length; i++) {
					cstmt.registerOutParameter(i + 1, outTypes[i]);
				}
			}
			cstmt.execute();
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			outputSqlStatment(sql, inParams);
			//释放资源
			//releasResource(cstmt, null);
		}
		return cstmt;
	}

	/**
	 * 数据库查询操作
	 *
	 * @param sql 要执行的操作sql语句
	 * @return 查询的结果数据集合
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getDataList(String sql) throws SQLException {
		return getDataList(sql, null);
	}

	/**
	 * 数据库查询操作
	 *
	 * @param sql    要执行的sql查询语句
	 * @param params sql语句中的参数
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getDataList(String sql, Object[] params) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, Object>> data = null;

		try {
			getConn();
			ps = conn.prepareStatement(sql);

			if (null != params && params.length != 0) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			rs = ps.executeQuery();
			data = convertList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			outputSqlStatment(sql, params);
			releasResource(ps, rs);
		}

		return data;
	}

	/**
	 * 数据库查询操作
	 *
	 * @param sql   要执行的sql查询语句
	 * @param clazz 结果集中的class对象
	 * @return 返回对象的集合，sql查询中的列名必须与对象中的属性名相同（不区别大小写），否则无法为对象属性正确赋值
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @author linchuan
	 * @date 2018年8月28日
	 */
	public <T> List<T> getObjectList(String sql, Class<T> clazz)
			throws SQLException, InstantiationException, IllegalAccessException {
		return getObjectList(sql, new Object[]{}, clazz);
	}

	/**
	 * 数据库查询操作
	 *
	 * @param sql    要执行的sql查询语句
	 * @param params sql语句中的参数
	 * @param clazz  结果集中的class对象
	 * @return 返回对象的集合，sql查询中的列名必须与对象中的属性名相同（不区别大小写），否则无法为对象属性正确赋值
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @author linchuan
	 * @date 2018年9月21日
	 */
	public <T> List<T> getObjectList(String sql, List<Object> params, Class<T> clazz)
			throws SQLException, InstantiationException, IllegalAccessException {
		Object[] paramsObj = params.toArray();
		return getObjectList(sql, paramsObj, clazz);
	}

	/**
	 * 数据库查询操作
	 *
	 * @param sql    要执行的sql查询语句
	 * @param params sql语句中的参数
	 * @param clazz  结果集中的class对象
	 * @return 返回对象的集合，sql查询中的列名必须与对象中的属性名相同（不区别大小写），否则无法为对象属性正确赋值
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @author linchuan
	 * @date 2018年8月28日
	 */
	public <T> List<T> getObjectList(String sql, Object[] params, Class<T> clazz)
			throws SQLException, InstantiationException, IllegalAccessException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<T> data = null;

		try {
			getConn();
			ps = conn.prepareStatement(sql);

			if (null != params && params.length != 0) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			rs = ps.executeQuery();
			data = convertObject(rs, clazz);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			outputSqlStatment(sql, params);
			releasResource(ps, rs);
		}
		return data;
	}

	/**
	 * 获取当前用户默认schema下的数据库表信息，“表”可以包含以下类型：
	 * "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
	 *
	 * @param types 需要查找的表类型
	 * @return 数据库中表信息的list集合，每张表包含的属性有：
	 * TABLE_CAT、<strong>TABLE_NAME、TABLE_TYPE</strong>、REMARKS、TABLE_SCHEM
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getTables(String[] types) throws SQLException {
		return this.getTables(getUserDefaultSchema(), types);
	}

	/**
	 * 获取该数据库的表信息，“表”可以包含以下类型：
	 * "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
	 *
	 * @param schemaPattern 数据库对象集合名，通常可以理解为数据库的用户名
	 * @param types         需要查找的表类型
	 * @return 数据库中表信息的list集合，每张表包含的属性有： TABLE_CAT、<strong>TABLE_NAME、TABLE_TYPE</strong>、REMARKS、TABLE_SCHEM
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getTables(String schemaPattern, String[] types) throws SQLException {
		return getTables(schemaPattern, null, types);
	}

	/**
	 * 获取该数据库的表信息，“表”可以包含以下类型：
	 * "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
	 *
	 * @param schemaPattern    数据库对象集合名，通常可以理解为数据库的用户名
	 * @param tableNamePattern 表名匹配字符串
	 * @param types            需要查找的表类型
	 * @return 数据库中表信息的list集合，每张表包含的属性有： TABLE_CAT、<strong>TABLE_NAME、TABLE_TYPE</strong>、REMARKS、TABLE_SCHEM
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getTables(String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
		DatabaseMetaData dbMetaData = null;
		ResultSet tablesRs = null;
		List<Map<String, Object>> data = null;
		tableNamePattern = caseConvertByDbType(tableNamePattern);
		try {
			dbMetaData = conn.getMetaData();
			tablesRs = dbMetaData.getTables(null, schemaPattern, tableNamePattern, types);

			data = convertListToUppercase(tablesRs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			releasResource(null, tablesRs);
		}
		return data;
	}

	/**
	 * 获取当前用户默认schema下的数据库的某个表的主键信息
	 *
	 * @param tableName 要查找的表名称
	 * @return 表中所有主键的list集合，主键的信息有： COLUMN_NAME、PK_NAME
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getTablePrimaryKeys(String tableName) throws SQLException {
		return this.getTablePrimaryKeys(getUserDefaultSchema(), tableName);
	}

	/**
	 * 获取数据库中某个表的主键信息
	 *
	 * @param tableName 要查找的表名称
	 * @return 表中所有主键的list集合，主键的信息有： COLUMN_NAME、PK_NAME
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getTablePrimaryKeys(String schemaPattern, String tableName) throws SQLException {
		DatabaseMetaData dbMetaData = null;
		ResultSet pksRs = null;
		List<Map<String, Object>> data = null;

		try {
			dbMetaData = conn.getMetaData();
			tableName = caseConvertByDbType(tableName);
			pksRs = dbMetaData.getPrimaryKeys(null, schemaPattern, tableName);

			data = new ArrayList<>();
			while (pksRs.next()) {
				Map<String, Object> rowData = new HashMap<>(1);
				rowData.put("COLUMN_NAME", pksRs.getString("COLUMN_NAME"));
				rowData.put("PK_NAME", pksRs.getString("PK_NAME"));
				data.add(rowData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			releasResource(null, pksRs);
		}
		return data;
	}

	/**
	 * 获取当前用户默认schema下的数据库的某个表的字段信息
	 *
	 * @param tableName 要查找的表名称
	 * @return 表中所有字段的list集合，每个字段包含的属性有： COLUMN_NAME、TYPE_NAME、DATA_TYPE、COLUMN_SIZE、DECIMAL_DIGITS、IS_NULLABLE、COLUMN_DEF
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getTableFields(String tableName) throws SQLException {
		return this.getTableFields(getUserDefaultSchema(), tableName);
	}

	/**
	 * 获取该数据库的某个表的所有字段信息
	 *
	 * @param schemaPattern 数据库对象集合名，通常可以理解为数据库的用户名
	 * @param tableName     要查找的表名称
	 * @return 表中所有字段的list集合，每个字段包含的属性有： COLUMN_NAME、TYPE_NAME、DATA_TYPE、COLUMN_SIZE、DECIMAL_DIGITS、NULLABLE、IS_NULLABLE、COLUMN_DEF、REMARKS
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getTableFields(String schemaPattern, String tableName) throws SQLException {
		DatabaseMetaData dbMetaData = null;
		ResultSet fieldsRs = null;
		List<Map<String, Object>> data = null;

		try {
			dbMetaData = conn.getMetaData();
			tableName = caseConvertByDbType(tableName);
			fieldsRs = dbMetaData.getColumns(null, schemaPattern, tableName, null);

			data = new ArrayList<>();
			while (fieldsRs.next()) {
				Map<String, Object> rowData = new HashMap<>(1);
				rowData.put("COLUMN_NAME", fieldsRs.getString("COLUMN_NAME"));
				rowData.put("TYPE_NAME", fieldsRs.getString("TYPE_NAME"));
				rowData.put("DATA_TYPE", fieldsRs.getInt("DATA_TYPE"));
				rowData.put("COLUMN_SIZE", fieldsRs.getInt("COLUMN_SIZE"));
				rowData.put("DECIMAL_DIGITS", fieldsRs.getInt("DECIMAL_DIGITS"));

				/**
				 * oracle的NUMBER类型在不指定精度及有效位数时，返回的默认值有误
				 * 特殊处理：（0.-127修正为38.0）
				 */
				if ("NUMBER".equals(fieldsRs.getString("TYPE_NAME"))) {
					if (0 == fieldsRs.getInt("COLUMN_SIZE")) {
						rowData.put("COLUMN_SIZE", 38);
					}
					if (-127 == fieldsRs.getInt("DECIMAL_DIGITS")) {
						rowData.put("DECIMAL_DIGITS", 0);
					}
				}
				rowData.put("NULLABLE", fieldsRs.getInt("NULLABLE"));
				rowData.put("IS_NULLABLE", fieldsRs.getString("IS_NULLABLE"));
				//rowData.put("COLUMN_DEF", fieldsRs.getString("COLUMN_DEF"));
				rowData.put("REMARKS", fieldsRs.getString("REMARKS"));
				data.add(rowData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			releasResource(null, fieldsRs);
		}
		return data;
	}

	/**
	 * 根据sql语句，查询该语句执行后所返回的字段描述
	 *
	 * @param sql
	 * @return 表中所有字段的list集合，每个字段包含的属性有： COLUMN_NAME、TYPE_NAME、DATA_TYPE、COLUMN_SIZE、DECIMAL_DIGITS、IS_NULLABLE
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getTableFieldsBySQL(String sql) throws SQLException {
		PreparedStatement ps = null;
		List<Map<String, Object>> data = null;

		try {
			ps = conn.prepareStatement(sql);

			ResultSetMetaData rsmd;
			try {
				rsmd = ps.getMetaData();
			} catch (SQLException e) {
				e.printStackTrace();
				rsmd = ps.executeQuery().getMetaData();
			}

			if (null != rsmd) {
				data = new ArrayList<>();
				int columeCount = rsmd.getColumnCount();
				for (int i = 1; i < columeCount + 1; i++) {
					Map<String, Object> rowData = new HashMap<>(1);

					rowData.put("COLUMN_NAME", rsmd.getColumnName(i));
					rowData.put("TYPE_NAME", rsmd.getColumnTypeName(i));
					rowData.put("DATA_TYPE", rsmd.getColumnType(i));
					rowData.put("COLUMN_SIZE", rsmd.getPrecision(i));
					rowData.put("DECIMAL_DIGITS", rsmd.getScale(i));

					/**
					 * oracle的NUMBER类型在不指定精度及有效位数时，返回的默认值有误
					 * 特殊处理：（0.-127修正为38.0）
					 */
					if ("NUMBER".equals(rsmd.getColumnTypeName(i))) {
						if (0 == rsmd.getPrecision(i)) {
							rowData.put("COLUMN_SIZE", 38);
						}
						if (-127 == rsmd.getScale(i)) {
							rowData.put("DECIMAL_DIGITS", 0);
						}
					}
					rowData.put("IS_NULLABLE", rsmd.isNullable(i));
					data.add(rowData);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			outputSqlStatment(sql, new HashMap<>(1));
			releasResource(ps, null);
		}
		return data;
	}

	/**
	 * 将resultset结果集转换为list集合
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private List<Map<String, Object>> convertList(ResultSet rs) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<>();

		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		while (rs.next()) {
			Map<String, Object> rowData = new HashMap<>(1);
			for (int i = 1; i <= columnCount; i++) {
				if (Types.CLOB == md.getColumnType(i)) {
					rowData.put(md.getColumnName(i), clobToString(rs.getClob(i)));
				} else if (Types.BLOB == md.getColumnType(i) || Types.BINARY == md.getColumnType(i) || Types.VARBINARY == md.getColumnType(i)) {
//					rowData.put(md.getColumnName(i), rs.getBinaryStream(i));
					rowData.put(md.getColumnName(i), rs.getBytes(i));
				} else if (Types.REAL == md.getColumnType(i) || Types.FLOAT == md.getColumnType(i) || Types.NUMERIC == md.getColumnType(i)
						|| Types.DECIMAL == md.getColumnType(i) || Types.NUMERIC == md.getColumnType(i)) {
					rowData.put(md.getColumnName(i), rs.getBigDecimal(i));
				} else {
					rowData.put(md.getColumnName(i), rs.getObject(i));
				}
			}
			list.add(rowData);
		}
		return list;
	}

	/**
	 * resultset结果集转换为list集合
	 * convertList方法中没有将resultset结果集中的键统一转为大写，故会造成JDBC
	 * 查询结果LIST中键可能为小写，以致于getTables中返回的LIST键可能为小写，
	 * 这样所有调用getTables的方法获取的结果都会有问题，故新增此方法，处理获取表，函数，存储过程的结果
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @author zhang_peng_lc
	 * @date 2019年12月24日
	 */
	private List<Map<String, Object>> convertListToUppercase(ResultSet rs) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<>();

		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		while (rs.next()) {
			Map<String, Object> rowData = new HashMap<>(1);
			for (int i = 1; i <= columnCount; i++) {
				if (Types.CLOB == md.getColumnType(i)) {
					rowData.put(md.getColumnName(i).toUpperCase(), clobToString(rs.getClob(i)));
				} else {
					rowData.put(md.getColumnName(i).toUpperCase(), rs.getObject(i));
				}
			}
			list.add(rowData);
		}
		return list;
	}

	/**
	 * 将结果集转换成实体对象集合
	 *
	 * @param <T>
	 * @param rs    结果集
	 * @param clazz 要转换为结果集中的class类型
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @author linchuan
	 * @date 2018年8月28日
	 */
	private <T> List<T> convertObject(ResultSet rs, Class<T> clazz) throws SQLException, InstantiationException, IllegalAccessException {
		/**
		 * 结果集 中列的名称和类型的信息
		 */
		ResultSetMetaData rsm = rs.getMetaData();
		int colNumber = rsm.getColumnCount();
		List<T> list = new ArrayList<>();
		Field[] fields = clazz.getDeclaredFields();

		while (rs.next()) {
			T obj = clazz.newInstance();
			/**
			 * 取出每一个字段进行赋值
			 */
			for (int i = 1; i <= colNumber; i++) {
				Object value = rs.getObject(i);
				if (value == null) {
					continue;
				}
				/**
				 * 匹配实体类中对应的属性
				 */
				for (int j = 0; j < fields.length; j++) {
					Field field = fields[j];
					if (field.getName().equalsIgnoreCase(rsm.getColumnName(i))) {
						boolean flag = field.isAccessible();
						field.setAccessible(true);

						if (value.getClass() == field.getType() || value.getClass() == Timestamp.class) {
							field.set(obj, value);
						} else {
							if (value instanceof BigDecimal) {
								int v = ((BigDecimal) value).intValue();
								field.set(obj, v);
							}

						}
						field.setAccessible(flag);
						break;
					}
				}

			}
			list.add(obj);
		}
		return list;
	}

	/**
	 * @param clob
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @author linchuan
	 * @date 2018年10月16日
	 */
	private String clobToString(Clob clob) throws SQLException {
		String reString = "";
		if (null == clob) {
			return reString;
		}
		Reader is = clob.getCharacterStream();
		BufferedReader br = new BufferedReader(is);
		String s;
		try {
			s = br.readLine();

			StringBuffer sb = new StringBuffer();
			while (s != null) {
				sb.append(s);
				s = br.readLine();
			}
			reString = sb.toString();
			if (br != null) {
				br.close();
			}
			if (is != null) {
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reString;
	}

	/**
	 * 原生jdbc产生connection连接
	 *
	 * @param connConfig
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @author linchuan
	 * @date 2018年9月29日
	 */
	private static Connection produceConn(ConnConfig connConfig) throws SQLException {
		Connection conn = null;
		DataSource dataSource = DatabaseAdapter.getDataSource(connConfig);
		conn = dataSource.getConnection();
		return conn;
	}

	/**
	 * 获取连接
	 * 如果连接为空则，则从数据库连接池获取一个连接
	 * 连接不为空，则表示该连接在事务中，还未提交
	 *
	 * @throws SQLException
	 */
	private void getConn() throws SQLException {
		if (null == this.conn || this.conn.isClosed()) {
//			this.conn = C3P0PoolManager.getInstance().getConnection(connConfig);
			this.conn = produceConn(connConfig);
		}
	}

	/**
	 * 释放当前的连接，如果该连接没有在事务中
	 *
	 * @throws SQLException
	 */
	private void releasConn() throws SQLException {
		if (this.conn.getAutoCommit()) {
//			C3P0PoolManager.getInstance().close(conn);
			this.conn.close();
			this.conn = null;
		}
	}

	/**
	 * 释放数据库资源
	 *
	 * @param st sql执行对象
	 * @param rs 查询结果集
	 */
	private void releasResource(Statement st, ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != st) {
					st.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != conn) {
						releasConn();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 根据传入的数据源id，获取连接数据库的基本属性
	 *
	 * @param dbcpID 数据源id
	 * @return
	 */
	private static ConnConfig getConnConfig(String dbcpID) {
		return  null;
//		Database database = Database.getInstance();
//		String sql = "select DBCP_DBURL jdbcUrl, DBCP_DRIVERCLASS driverClass, DBCP_DBUSERNAME userName, DBCP_DBPASSWORD password, "
//				+ " DBCP_INITIALPOOLSIZE initialPoolSize, DBCP_MAXIDLETIME maxIdleTime, DBCP_MAXPOOLSIZE maxPoolSize, DBCP_MINPOOLSIZE minPoolSize, DBCP_MAXSTATEMENTS maxStatements"
//				+ " from DSM_DBCP where DBCP_ID=?";
//
//		List<ConnConfig> config = null;
//		try {
//			config = database.getObjectList(sql, new Object[]{dbcpID}, ConnConfig.class);
//		} catch (InstantiationException | IllegalAccessException | SQLException e) {
//			e.printStackTrace();
//		}
//		if (null == config || config.size() == 0) {
//			return null;
//		}
//		ConnConfig connConfig = config.get(0);
//		String password = DesTools.decryptPassword(connConfig.getPassword());
//		connConfig.setPassword(password);
//		connConfig.setMaxPoolSize(10);
//		//连接池名字默认为该数据库连接的主键
//		connConfig.setPoolID(dbcpID);
//		return connConfig;
	}

	/**
	 * 获取数据库类型
	 *
	 * @return 数据库类型的字符串描述，如"ORA"表示oracle数据库，"MSS"表示SQL Server数据库
	 * @throws SQLException
	 * @author linchuan
	 * @date 2018年9月30日
	 */
	public String getDatabaseType() throws SQLException {
		//Microsoft SQL Server、Oracle
		String dbType = "";
		String productName = conn.getMetaData().getDatabaseProductName();

		String mss = "Microsoft SQL Server";
		String ora = "Oracle";

		if (mss.equals(productName)) {
			dbType = "MSS";
		} else if (ora.equals(productName)) {
			dbType = "ORA";
		} else {
			dbType = productName;
		}
		return dbType;
	}

	/**
	 * 获取当前用户默认的schema
	 *
	 * @return
	 * @throws SQLException
	 */
	public String getUserDefaultSchema() throws SQLException {
		//Microsoft SQL Server、Oracle、MySQL
		String dbProductName = conn.getMetaData().getDatabaseProductName();

		String mss = "Microsoft SQL Server";
		String ora = "Oracle";
		String dameng = "DM DBMS";

		if (mss.equals(dbProductName)) {
			return null;
		} else if (ora.equals(dbProductName)
				|| dameng.equals(dbProductName)) {
			return connConfig.getUserName().toUpperCase();
		} else if (Constant.GREENPLUM_DBTYPE.equals(dbProductName)) {
			/**
			 * GREENPLUM 数据库执行conn.getSchema报错，GreenPlum的驱动有问题
			 * 目前GreeenPlum只支持public模式
			 */
			return "public";
		} else if (Constant.POSTGRESQL_DBTYPE.equals(dbProductName)) {
			return conn.getSchema();
		}
		return null;
	}

	/**
	 * 获取存储过程列表
	 *
	 * @param procName 要查找的存储过程名称
	 * @return 存储过程中所有参数的list集合
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getProcFields(String procName) throws SQLException {
		return this.getProcFields(getUserDefaultSchema(), procName);
	}

	/**
	 * 获取存储过程列表
	 *
	 * @param schemaPattern 数据库对象集合名，通常可以理解为数据库的用户名
	 * @param procName      要查找的存储过程名称
	 * @return 存储过程中所有参数的list集合
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getProcFields(String schemaPattern, String procName) throws SQLException {
		DatabaseMetaData dbMetaData = null;
		ResultSet fieldsRs = null;
		List<Map<String, Object>> data = null;

		try {
			dbMetaData = conn.getMetaData();
			String dbProductName = conn.getMetaData().getDatabaseProductName();
			procName = caseConvertByDbType(procName);
			fieldsRs = dbMetaData.getProcedureColumns(null, schemaPattern, procName, null);

			data = new ArrayList<>();
			while (fieldsRs.next()) {
				Map<String, Object> rowData = new HashMap<>(1);
				rowData.put("COLUMN_NAME", fieldsRs.getString("COLUMN_NAME"));
				// sqlserver存在bug，输出参数会被读成输入输出参数
				if (2 == fieldsRs.getShort("COLUMN_TYPE") && "Microsoft SQL Server".equals(dbProductName)) {
					rowData.put("COLUMN_TYPE", 4);
				} else {
					rowData.put("COLUMN_TYPE", fieldsRs.getShort("COLUMN_TYPE"));
				}
				rowData.put("TYPE_NAME", fieldsRs.getString("TYPE_NAME"));
				rowData.put("DATA_TYPE", fieldsRs.getInt("DATA_TYPE"));
				data.add(rowData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			releasResource(null, fieldsRs);
		}
		return data;
	}

	/**
	 * 获取当前用户默认schema下的数据库存储过程信息
	 *
	 * @return 数据库中存储过程信息的list集合
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getProcDures() throws SQLException {
		return this.getProcDures(getUserDefaultSchema());
	}

	/**
	 * 获取该数据库的存储过程信息
	 *
	 * @param schemaPattern 数据库对象集合名，通常可以理解为数据库的用户名
	 * @return 数据库中存储过程信息的list集合
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getProcDures(String schemaPattern) throws SQLException {
		DatabaseMetaData dbMetaData = null;
		ResultSet tablesRs = null;
		List<Map<String, Object>> data = null;

		// oracle、sqlserver、mysql的jdbc中，提供的查询存储过程的方法，查询结果为存储过程和函数。改用sql获取
		String dbProductName = conn.getMetaData().getDatabaseProductName();
		String ora = "Oracle";
		String mss = "Microsoft SQL Server";
		String mysql = "MySQL";
		if (ora.equalsIgnoreCase(dbProductName)) {
			String sql = "select '' as PROCEDURE_CAT,'' as PROCEDURE_SCHEM,OBJECT_NAME as PROCEDURE_NAME,'' as REMARKS,1 as PROCEDURE_TYPE\r\n" +
					"from user_objects where object_type='PROCEDURE' ";
			return this.getDataList(sql);
		} else if (mss.equalsIgnoreCase(dbProductName)) {
			String sql = "select b.name + ';' + convert(varchar(10),a.number) as PROCEDURE_NAME from syscomments a,sysobjects b where object_id(b.name)=a.id and b.xtype in('P','TR') ";
			return this.getDataList(sql);
		} else if (mysql.equalsIgnoreCase(dbProductName)) {
			String sql = "SELECT SPECIFIC_NAME as PROCEDURE_NAME FROM information_schema.Routines WHERE ROUTINE_TYPE = 'PROCEDURE' ";
			return this.getDataList(sql);
		}

		try {
			dbMetaData = conn.getMetaData();
			tablesRs = dbMetaData.getProcedures(null, schemaPattern, null);

			data = convertListToUppercase(tablesRs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			releasResource(null, tablesRs);
		}
		return data;
	}

	/**
	 * 获取当前用户默认schema下的数据库函数信息
	 *
	 * @return
	 * @throws SQLException
	 * @author linchuan
	 * @date 2019年8月28日
	 */
	public List<Map<String, Object>> getFunctions() throws SQLException {
		return this.getFunctions(getUserDefaultSchema());
	}

	/**
	 * 获取数据库函数信息
	 *
	 * @param schemaPattern 数据库对象集合名，通常可以理解为数据库的用户名
	 * @return 函数列表
	 * @throws SQLException
	 * @author linchuan
	 * @date 2019年8月28日
	 */
	public List<Map<String, Object>> getFunctions(String schemaPattern) throws SQLException {
		DatabaseMetaData dbMetaData = null;
		ResultSet tablesRs = null;
		List<Map<String, Object>> data = null;

		try {
			//sqlserver提供的查询函数的方法，查询结果为存储过程和函数。改用sql获取
			String dbProductName = conn.getMetaData().getDatabaseProductName();
			String mss = "Microsoft SQL Server";
			if (mss.equalsIgnoreCase(dbProductName)) {
				String sql = "select b.name as FUNCTION_NAME"
						+ " from syscomments a,sysobjects b"
						+ " where object_id(b.name)=a.id and b.xtype in('FN','TF') ";
				return this.getDataList(sql);
			}

			dbMetaData = conn.getMetaData();
			tablesRs = dbMetaData.getFunctions(null, schemaPattern, null);

			data = convertListToUppercase(tablesRs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			releasResource(null, tablesRs);
		}
		return data;
	}

	/**
	 * 返回函数的参数及返回类型
	 *
	 * @param functionName 要查找的函数名称
	 * @return
	 * @throws SQLException
	 * @author linchuan
	 * @date 2019年8月29日
	 */
	public List<Map<String, Object>> getFunctionColumns(String functionName) throws SQLException {
		return this.getFunctionColumns(getUserDefaultSchema(), functionName, null);
	}

	/**
	 * 返回函数的参数及返回类型
	 *
	 * @param schemaPattern 数据库对象集合名，通常可以理解为数据库的用户名
	 * @param functionName  要查找的函数名称
	 * @param columnName    列名称
	 * @return
	 * @throws SQLException
	 * @author linchuan
	 * @date 2019年8月29日
	 */
	public List<Map<String, Object>> getFunctionColumns(String schemaPattern, String functionName, String columnName) throws SQLException {
		DatabaseMetaData dbMetaData = null;
		ResultSet fieldsRs = null;
		List<Map<String, Object>> data = null;

		try {
			dbMetaData = conn.getMetaData();
			fieldsRs = dbMetaData.getFunctionColumns(null, schemaPattern, functionName, columnName);

			data = new ArrayList<>();
			while (fieldsRs.next()) {
				Map<String, Object> rowData = new HashMap<>(1);
				rowData.put("COLUMN_NAME", fieldsRs.getString("COLUMN_NAME"));
				rowData.put("COLUMN_TYPE", fieldsRs.getShort("COLUMN_TYPE"));
				rowData.put("TYPE_NAME", fieldsRs.getString("TYPE_NAME"));
				rowData.put("DATA_TYPE", fieldsRs.getInt("DATA_TYPE"));
				data.add(rowData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			releasResource(null, fieldsRs);
		}
		return data;
	}

	/**
	 * 输出执行sql
	 *
	 * @param sqls
	 * @author linchuan
	 * @date 2019年6月21日
	 */
	private void outputSqlStatment(String[] sqls) {
		for (String sql : sqls) {
			this.printSql(sql, null);
		}
	}

	/**
	 * 输出执行sql及其参数
	 *
	 * @param sql
	 * @param paramMap
	 * @author linchuan
	 * @date 2019年6月21日
	 */
	private void outputSqlStatment(String sql, Map<String, ?> paramMap) {
		//拼接sql参数
		StringBuffer params = new StringBuffer();
		if (null != paramMap && paramMap.size() > 0) {
			params.append("Params:[ ");

			Set<String> keys = paramMap.keySet();
			for (String s : keys) {
				params.append(s + ":" + paramMap.get(s) + ",");
			}
			params.deleteCharAt(params.length() - 1);
			params.append(" ]");
		}
		this.printSql(sql, params.toString());
	}

	/**
	 * 输出执行sql及其参数
	 *
	 * @param sql
	 * @param params
	 * @author linchuan
	 * @date 2019年6月21日
	 */
	private void printSql(String sql, String params) {
		return;
//		if (this.log.isInfoEnabled()) {
//			this.log.info("Executing SQL statement:" + (sql != null ? "[ " + sql + " ]" : ""));
//			if (null != params && !"".equals(params)) {
//				this.log.info(params);
//			}
//			java.util.Date today = new java.util.Date();
//			java.text.SimpleDateFormat dateTimeFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			this.log.info("Now is:" + dateTimeFormat.format(today));
//		}
	}

	/**
	 * 输出执行sql及其参数
	 *
	 * @param sql
	 * @param objects
	 * @author linchuan
	 * @date 2019年6月21日
	 */
	private void outputSqlStatment(String sql, Object[] objects) {
		StringBuffer params = new StringBuffer();
		if (null != objects && objects.length > 0) {
			params.append("Params:[ ");

			for (Object s : objects) {
				params.append(s + ",");
			}
			params.deleteCharAt(params.length() - 1);
			params.append(" ]");
		}
		this.printSql(sql, params.toString());
	}

	/**
	 * 输出执行sql及其参数
	 *
	 * @param sql
	 * @param paramList
	 * @author linchuan
	 * @date 2019年6月21日
	 */
	private void outputBatchSqlStatment(String sql, List<Object[]> paramList) {
		StringBuffer params = new StringBuffer();
		if (null != paramList && paramList.size() > 0) {
			params.append("Params:[ ");

			for (Object[] objects : paramList) {
				for (Object o : objects) {
					params.append(o + ",");
				}
				params.deleteCharAt(params.length() - 1);
				params.append(" |");
			}
			params.deleteCharAt(params.length() - 1);
			params.append(" ]");
		}
		this.printSql(sql, params.toString());
	}


	/**
	 * 处理字符串的大小写以适应不同的数据库
	 *
	 * @param str 要处理的字符串
	 * @return 处理后的字符串
	 * @throws SQLException
	 */
	private String caseConvertByDbType(String str) throws SQLException {
		if (str == null) {
			return str;
		} else {
			String dbType = getDatabaseType();
			if (Constant.ORACLE_DBTYPE.equals(dbType)) {
				str = str.toUpperCase();
			} else if (Constant.SQLSERVER_DBTYPE.equals(dbType)) {
				//不处理，保持原样
			} else if (Constant.MYSQL_DBTYPE.equals(dbType)) {
				//不处理，保持原样
			} else if (Constant.MARIADB_DBTYPE.equals(dbType)) {
				//不处理，保持原样
			} else if (Constant.KINGBASE_DBTYPE.equals(dbType)) {
				str = str.toUpperCase();
			} else if (Constant.HIVE_DBTYPE.equals(dbType)) {
				//不处理，保持原样
			} else if (Constant.DAMENG_DBTYPE.equals(dbType)) {
				str = str.toUpperCase();
			} else if (Constant.GREENPLUM_DBTYPE.equals(dbType)) {
				str = str.toLowerCase();
			} else if (Constant.POSTGRESQL_DBTYPE.equals(dbType)) {
				str = str.toLowerCase();
			}
			return str;
		}
	}

}
