package me.bloodyhan.bridgeleveling.task;

import me.bloodyhan.bridgeleveling.Main;
import me.bloodyhan.bridgeleveling.api.PlayerData;
import me.bloodyhan.bridgeleveling.config.MainConfig;
import me.bloodyhan.bridgeleveling.config.MessageConfig;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author Bloody_Han
 */
public class OnlineRewardTask implements BridgeTask{

    private Player p;
    private BukkitTask task;
    private int xp;

    public OnlineRewardTask(){
        this.xp = MainConfig.XP_GIVE_ONLINE;
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), () -> {
            for (Player online : Bukkit.getOnlinePlayers()) {
                PlayerData data = PlayerData.getData(p.getUniqueId());
                data.addXp(this.xp);
                p.sendMessage(PlaceholderAPI.setPlaceholders(p, MessageConfig.XP_GIVE_ONLINE.replace("{amount}", String.valueOf(this.xp))));
            }
        },MainConfig.REQUIREMENT_ONLINE_MILLIS, MainConfig.REQUIREMENT_ONLINE_MILLIS);
    }

    @Override
    public void cancel(){
        if(this.task != null) {
            this.task.cancel();
            this.task = null;
        }
    }

}
