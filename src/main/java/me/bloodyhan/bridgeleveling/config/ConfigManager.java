package me.bloodyhan.bridgeleveling.config;

import me.bloodyhan.bridgeleveling.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * @author: Bongle (Boougouh)
 * @createDate: 2019/07/22
 * @version: 2.2
 */
public class ConfigManager {

    private YamlConfiguration yml;
    private final File config;

    public ConfigManager(String name) {
        File folder = Main.getInstance().getDataFolder();
        if(!folder.exists()){
            folder.mkdir();
        }
        this.config = new File(folder, name + ".yml");
        try {
            if(this.config.createNewFile()){
                Main.log("§a正在实例化 " + config.getPath());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.yml = YamlConfiguration.loadConfiguration(this.config);
        init();
    }

    public ConfigManager(String name, String path) {
        this.config = new File(path, name + ".yml");
        try {
            if(this.config.createNewFile()){
                Main.log("§a正在实例化 " + path + "/" + name + ".yml");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.yml = YamlConfiguration.loadConfiguration(this.config);
        init();
    }

    private void init() {
        yml.options().copyDefaults(true);
        for (Field field : this.getClass().getFields()){
            Config cfg = field.getAnnotation(Config.class);
            if(cfg == null){
                continue;
            }
            try {
                Object obj = getYml().get(cfg.path(), field.get(this));
                yml.set(cfg.path(), obj);
                if(obj instanceof String){
                    obj = ChatColor.translateAlternateColorCodes('&', (String) obj);
                }
                if(field.getType() == float.class){
                    field.set(this, Float.parseFloat(String.valueOf(obj)));
                }else{
                    field.set(this, obj);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        try {
            this.yml.save(this.config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload(){
        this.yml = YamlConfiguration.loadConfiguration(this.config);
        init();
    }

    public void set(String string, Object obj) {
        this.yml.set(string, obj);
        this.save();
    }

    public void save() {
        for (Field field : this.getClass().getFields()){
            Config cfg = field.getAnnotation(Config.class);
            if(cfg == null){
                continue;
            }
            try {
                yml.set(cfg.path(), field.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        try {
            this.yml.save(this.config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getOrNull(String s){
        return this.yml.get(s, null);
    }

    public YamlConfiguration getYml(){
        return this.yml;
    }

    public boolean getBoolean(String b) {
        return this.yml.getBoolean(b);
    }

    public int getInt(String i) {
        return this.yml.getInt(i);
    }

    public String getString(String s) {
        return ChatColor.translateAlternateColorCodes('&', this.yml.getString(s));
    }

    public void addDefault(String path, Object obj){
        Object o = getYml().get(path, obj);
        yml.set(path, obj);
    }

    public String getString(String s, String def) {
        return ChatColor.translateAlternateColorCodes('&', this.yml.getString(s, def));
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Config{

        String path();

    }

}
