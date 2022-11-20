package com.ppx.sqltrans.databases;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import com.zaxxer.hikari.metrics.MetricsTrackerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 *
 */
public class DatabaseAdapter {
    private static Logger logger = LoggerFactory.getLogger(DatabaseAdapter.class);
    static ConcurrentHashMap<String, DataSource> dataSourceConcurrentHashMap = new ConcurrentHashMap<>(4);

    // 预防内存泄漏
//    static CacheBuilder cacheBuilder;

    static int count = 1;

    /**
     * @param connConfig
     * @return
     */
    public static HikariConfig transferConfig(ConnConfig connConfig) {
        logger.info("config init ==================================================");
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName(connConfig.getDriverClass());
        hikariConfig.setPoolName(connConfig.getPoolID());
        hikariConfig.setJdbcUrl(connConfig.getJdbcUrl());
        hikariConfig.setUsername(connConfig.getUserName());
        hikariConfig.setPassword(connConfig.getPassword());
        hikariConfig.setMaximumPoolSize(connConfig.getMaxPoolSize());
        hikariConfig.setIdleTimeout(connConfig.getMaxIdleTime());
        // 超时时间

        int maxIdleTime = connConfig.getMaxIdleTime() * 1000;
        if (maxIdleTime < 10000) {
            logger.info("idleTimeout is less than 10000ms, setting to default");
        }
        hikariConfig.setIdleTimeout(maxIdleTime);

        hikariConfig.setConnectionTimeout(connConfig.getCheckoutTimeout());
//        部分配置没对齐
        logger.info("config init ===========end=======================================");
        return hikariConfig;
    }


    public static synchronized DataSource getDataSource(ConnConfig connConfig, MetricsTrackerFactory metric) {
        String poolName = connConfig.getPoolID();
        if (dataSourceConcurrentHashMap.containsKey(poolName)) {
            HikariDataSource dataSource = (HikariDataSource) dataSourceConcurrentHashMap.get(poolName);
            return dataSource;

        }
        String driverClass = connConfig.getDriverClass();
        String password = connConfig.getPassword();
        String userName = connConfig.getUserName();
        HikariConfig hikariConfig = transferConfig(connConfig);
//        hikariConfig.setDataSourceClassName("com.zaxxer.hikari.HikariDataSource");
        if (!Objects.isNull(metric)) {
            hikariConfig.setMetricsTrackerFactory(metric);
//            hikariConfig.setMetricRegistry(metric);
        }
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        dataSourceConcurrentHashMap.put(poolName, dataSource);
        return dataSource;
    }

    public static synchronized DataSource getDataSource(ConnConfig connConfig) {
        String poolName = connConfig.getPoolID();
        if (dataSourceConcurrentHashMap.containsKey(poolName)) {
            return (HikariDataSource) dataSourceConcurrentHashMap.get(poolName);
        }

        HikariConfig hikariConfig = transferConfig(connConfig);
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        dataSourceConcurrentHashMap.put(poolName, dataSource);
        return dataSource;
    }

    public Connection getConnection() {
        DataSource dataSource = getDataSource(null);
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public static void printDatasourceConnections() {
        dataSourceConcurrentHashMap.forEach(new BiConsumer<String, DataSource>() {
            @Override
            public void accept(String s, DataSource dataSource) {
                logger.info("datasource: s");
                HikariDataSource ds = (HikariDataSource) dataSource;
                HikariPoolMXBean hikariPoolMXBean = ds.getHikariPoolMXBean();
                logger.info("total connections: {} active connections:{}  idle connections:{}", hikariPoolMXBean.getTotalConnections() + "", hikariPoolMXBean.getActiveConnections() + "", hikariPoolMXBean.getIdleConnections() + "");
            }
        });
    }

    /**
     * 清理数据源，放置到定时程序里，进行清理，避免内存泄漏
     *
     * @param dbId
     */

    public static void deletPool(String dbId) {
        HikariDataSource dataSource = (HikariDataSource) dataSourceConcurrentHashMap.get(dbId);
        if (!Objects.isNull(dataSource)) {
            dataSource.close();
            dataSourceConcurrentHashMap.remove(dbId);
        }
    }
}
