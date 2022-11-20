package com.ppx.sqltrans.databases;


/**
 * 数据库连接基本属性
 * @author linchuan
 * 
 */
public class ConnConfig {

	/**
	 * 连接池标识
	 */
	private String poolID = "defaultPool";
	/**
	 * 驱动名称
	 */
	private String driverClass;
	/**
	 * 数据库jdbc连接地址
	 */
	private String jdbcUrl;
	/**
	 * 用户名
	 */
    private String userName;
    /**
     * 密码
     */
    private String password;
    
    /**
     * 初始连接数
     */
    private int initialPoolSize = 10;
    /**
     * 最大连接数
     */
    private int maxPoolSize = 10;
    /**
     * 最小连接数
     */
    private int minPoolSize = 10;
    /**
     * 最大空闲时间，超过空闲时间的连接将被丢弃。为0或负数则永不丢弃。默认为0；
     */
    private int maxIdleTime = 1800;
    /**
     * 控制数据源内加载的PreparedStatement数量，默认为0； 
     */
    private int maxStatements = 0;
    /**
     * 从数据库获取新连接失败后重复尝试获取的次数
     */
    private int acquireRetryAttempts = 3;
    /**
     * 等待获取新连接的时间，超时后将抛出 SQLException，如设为0则无限期等待
     */
    private int checkoutTimeout = 8000;
    
    public ConnConfig() {
	}
   
    /**
     * 
     * @param poolID	连接池标识
     * @param driverClass	驱动名称
     * @param jdbcUrl	数据库jdbc连接地址
     * @param user		用户名
     * @param password	密码
     */
	public ConnConfig(String poolID, String driverClass, String jdbcUrl, String user, String password) {
		this.poolID = poolID;
		this.driverClass = driverClass;
		this.jdbcUrl = jdbcUrl;
		this.userName = user;
		this.password = password;
	}
	
	public String getPoolID() {
		return poolID;
	}
	public void setPoolID(String poolID) {
		this.poolID = poolID;
	}

	public String getDriverClass() {
		return driverClass;
	}
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getInitialPoolSize() {
		return initialPoolSize;
	}
	public void setInitialPoolSize(int initialPoolSize) {
		this.initialPoolSize = initialPoolSize;
	}
	public int getMaxPoolSize() {
		return maxPoolSize;
	}
	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}
	public int getMinPoolSize() {
		return minPoolSize;
	}
	public void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public int getMaxIdleTime() {
		return maxIdleTime;
	}

	public void setMaxIdleTime(int maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public int getMaxStatements() {
		return maxStatements;
	}

	public void setMaxStatements(int maxStatements) {
		this.maxStatements = maxStatements;
	}

	public int getAcquireRetryAttempts() {
		return acquireRetryAttempts;
	}

	public void setAcquireRetryAttempts(int acquireRetryAttempts) {
		this.acquireRetryAttempts = acquireRetryAttempts;
	}

	public int getCheckoutTimeout() {
		return checkoutTimeout;
	}

	public void setCheckoutTimeout(int checkoutTimeout) {
		this.checkoutTimeout = checkoutTimeout;
	}
    
    
}
