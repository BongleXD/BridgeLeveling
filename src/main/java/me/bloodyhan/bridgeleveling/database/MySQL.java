package me.bloodyhan.bridgeleveling.database;

import com.google.common.base.Joiner;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.bloodyhan.bridgeleveling.Main;
import me.bloodyhan.bridgeleveling.config.MainConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Bloody_Han
 */
public class MySQL implements Database{

    private HikariDataSource source;

    @Override
    public void init() {
        HikariConfig config = new HikariConfig();
        config.setPoolName("bridgeleveling-hikari");
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl(String.format("jdbc:mysql://%s/%s"
                , MainConfig.MYSQL_URL, MainConfig.MYSQL_DATABASE));
        config.setUsername(MainConfig.MYSQL_USER);
        config.setPassword(MainConfig.MYSQL_PASSWD);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");
        config.addDataSourceProperty("alwaysSendSetIsolation", "false");
        config.addDataSourceProperty("cacheCallableStmts", "true");
        config.addDataSourceProperty("useUnicode", "true");
        config.addDataSourceProperty("characterEncoding", "utf-8");
        config.addDataSourceProperty("useSSL", "false");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(10);
        config.setMaxLifetime(1800000);
        config.setConnectionTimeout(5000);
        Main.log("§e连接至 MySQL 中。。。");
        this.source = new HikariDataSource(config);
        Main.log("§a已经连接至 MySQL！");
    }

    @Override
    public Connection getConnection() {
        try {
            return this.source.getConnection();
        } catch (SQLException ex) {
            Main.log("§c无法连接至 MySQL！ 请到 §bconfig.yml §c查看参数是否有误！");
        }
        return null;
    }

    @Override
    public void close() {
        this.source.close();
    }

}
