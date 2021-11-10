package me.bloodyhan.bridgeleveling.database;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.bloodyhan.bridgeleveling.Main;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Bloody_Han
 */
public class SQLite implements Database{

    private Connection conn;

    @Override
    public void init() {
        if(!Main.getInstance().getDataFolder().exists()){
            Main.getInstance().getDataFolder().mkdir();
        }
        File file = new File(Main.getInstance().getDataFolder() + "/Level.db");
        try {
            DriverManager.registerDriver((Driver) Bukkit.getServer().getClass().getClassLoader().loadClass("org.sqlite.JDBC").newInstance());
            this.conn = DriverManager.getConnection("jdbc:sqlite:" + file);
        }catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            if (ex instanceof ClassNotFoundException) {
                Main.log("§c你的系统内未安装 SQLite 驱动!");
            }
            ex.printStackTrace();
        }
        Main.log("§a已经连接至 SQLite！");
    }

    @Override
    public Connection getConnection() {
        return this.conn;
    }

    @Override
    public void close() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(String table, Value... values){
        PreparedStatement stmt = null;
        try {
            String query = Joiner.on(", ").join(Arrays.stream(values).map(value -> value.getName() + " " + value.getType().getStatement()).collect(Collectors.toList()));
            stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + "(" + query + ");");
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(stmt != null){
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public List<Object> getData(String table, String flag, String flagData, String... datas){
        List<Object> list = Lists.newArrayList();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < datas.length; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(datas.clone()[i]);
            }
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT " + sb.toString() + " FROM " + table + " WHERE " + flag + " = '" + flagData + "';");
            if (rs.next()) {
                for (int i = 0; i < datas.length; i++) {
                    Object obj = rs.getObject(i + 1);
                    list.add(obj == null ? "" : obj);
                }
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null){
                    rs.close();
                }
                if(stmt != null){
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public boolean checkDataExists(String table, String flag, String data){
        try {
            return conn.prepareStatement("SELECT * FROM " + table + " WHERE " + flag + " = '" + data + "';").executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void insertData(String table, SqlValue... values){
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(
                    "INSERT INTO " + table +
                            " (" + Joiner.on(", ").join(Arrays.stream(values).map(SqlValue::getData).collect(Collectors.toList())) + ") VALUES ('" +
                            Joiner.on("', '").join(Arrays.stream(values).map(SqlValue::getValue).collect(Collectors.toList())) + "');");
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(stmt != null){
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void putData(String table, String flag, String flagData, SqlValue... values){
        PreparedStatement stmt = null;
        try {
            String data = flag + ", " + Joiner.on(", ")
                    .join(Arrays.stream(values.clone())
                            .map(v -> v.getData())
                            .collect(Collectors.toList()));
            String sqlValue = "'" + flagData + "', '" + Joiner.on("', '")
                    .join(Arrays.stream(values.clone())
                            .map(v -> v.getValue())
                            .collect(Collectors.toList())) + "'";
            String set = Joiner.on(", ")
                    .join(Arrays.stream(values.clone())
                            .map(v -> v.getData() + " = '" + v.getValue() + "'")
                            .collect(Collectors.toList()));
            if (!checkDataExists(table, flag, flagData)) {
                stmt = conn.prepareStatement("INSERT INTO " + table + " (" + data + ") VALUES(" + sqlValue + ");");
            } else {
                stmt = conn.prepareStatement("UPDATE " + table + " SET " + set + " WHERE " + flag + " = '" + flagData + "';");
            }
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(stmt != null){
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
