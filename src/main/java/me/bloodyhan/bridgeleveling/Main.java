package me.bloodyhan.bridgeleveling;

import me.bloodyhan.bridgeleveling.api.PlayerData;
import me.bloodyhan.bridgeleveling.command.BridgeCommand;
import me.bloodyhan.bridgeleveling.command.CommandManager;
import me.bloodyhan.bridgeleveling.config.MainConfig;
import me.bloodyhan.bridgeleveling.config.MessageConfig;
import me.bloodyhan.bridgeleveling.config.SoundConfig;
import me.bloodyhan.bridgeleveling.database.Database;
import me.bloodyhan.bridgeleveling.database.MySQL;
import me.bloodyhan.bridgeleveling.database.SQLite;
import me.bloodyhan.bridgeleveling.listener.BlockListener;
import me.bloodyhan.bridgeleveling.listener.KillListener;
import me.bloodyhan.bridgeleveling.listener.LevelListener;
import me.bloodyhan.bridgeleveling.task.BridgeTask;
import me.bloodyhan.bridgeleveling.task.OnlineRewardTask;
import me.bloodyhan.bridgeleveling.util.PlaceholderHook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Bloody_Han
 */
public class Main extends JavaPlugin {

    private static Main instance;
    private Database db;
    private BridgeTask onlineTask;

    public static Main getInstance() {
        return instance;
    }

    public static void log(String text){
        Bukkit.getConsoleSender().sendMessage(String.format("[%s] %s","BridgeLeveling", ChatColor.translateAlternateColorCodes('&',text)));
    }

    @Override
    public void onLoad() {
        Main.log("\n" +
                "  ____       _     _            _                    _ _             \n" +
                " |  _ \\     (_)   | |          | |                  | (_)            \n" +
                " | |_) |_ __ _  __| | __ _  ___| |     _____   _____| |_ _ __   __ _ \n" +
                " |  _ <| '__| |/ _` |/ _` |/ _ \\ |    / _ \\ \\ / / _ \\ | | '_ \\ / _` |\n" +
                " | |_) | |  | | (_| | (_| |  __/ |___|  __/\\ V /  __/ | | | | | (_| |\n" +
                " |____/|_|  |_|\\__,_|\\__, |\\___|______\\___| \\_/ \\___|_|_|_| |_|\\__, |\n" +
                "                      __/ |                                     __/ |\n" +
                "                     |___/                                     |___/");
    }

    @Override
    public void onDisable() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            PlayerData data = PlayerData.getData(online.getUniqueId());
            data.saveData(true);
        }
        this.db.close();
        Main.log("§c已卸载 BridgeLeveling v" + Main.getInstance().getDescription().getVersion() + " By.Bloody_Han");
    }

    @Override
    public void onEnable() {
        instance = this;
        Main.log("§a已加载 BridgeLeveling v" + Main.getInstance().getDescription().getVersion() + " By.Bloody_Han");
        new MainConfig();
        new SoundConfig();
        new MessageConfig();
        this.db = MainConfig.MYSQL_ENABLED ? new MySQL() : new SQLite();
        this.db.init();
        PlayerData.init();
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderHook(this).register();
            Main.log("§a已接入 PlaceHolderAPI");
        }
        new BlockListener();
        new KillListener();
        new LevelListener();
        CommandManager.regCommand(new BridgeCommand(), this);
        this.onlineTask = new OnlineRewardTask();
    }

    @Override
    public void reloadConfig(){
        MainConfig.config.reload();
        SoundConfig.config.reload();
        MessageConfig.config.reload();
        this.onlineTask.cancel();
        this.onlineTask = new OnlineRewardTask();
    }

    public Database getBlockDatabase() {
        return db;
    }

}
