package me.bloodyhan.bridgeleveling.task;

import me.bloodyhan.bridgeleveling.Main;
import me.bloodyhan.bridgeleveling.api.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author Bloody_Han
 */
public class TargetResetTask implements BridgeTask{

    private Player p;
    private BukkitTask task;

    public TargetResetTask(Player p){
        this.p = p;
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), () -> {
            PlayerData data = PlayerData.getData(p.getUniqueId());
            if(data.getLastDamager() != null) {
                data.setLastDamager(null);
            }
            cancel();
        },10 * 20L, 10 * 20L);
    }

    @Override
    public void cancel(){
        if(this.task != null) {
            this.task.cancel();
            this.task = null;
        }
    }

}
