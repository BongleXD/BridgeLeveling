package me.bloodyhan.bridgeleveling.listener;

import me.bloodyhan.bridgeleveling.Main;
import me.bloodyhan.bridgeleveling.api.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Bloody_Han
 */
public class LevelListener implements Listener {

    public LevelListener() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PlayerData data = PlayerData.getData(p.getUniqueId());
        if (data == null) {
            data = new PlayerData(p.getUniqueId());
        }
        data.saveData(false);
        if(p.hasPermission("bl.boost.5")){
            data.setBoost(5);
        }
        else if(p.hasPermission("bl.boost.4.5")){
            data.setBoost(4.5);
        }
        else if(p.hasPermission("bl.boost.4")){
            data.setBoost(4);
        }
        else if(p.hasPermission("bl.boost.3.5")){
            data.setBoost(3.5);
        }
        else if(p.hasPermission("bl.boost.3")){
            data.setBoost(3);
        }
        else if(p.hasPermission("bl.boost.2.5")){
            data.setBoost(2.5);
        }
        else if(p.hasPermission("bl.boost.2")){
            data.setBoost(2);
        }
        else if(p.hasPermission("bl.boost.1.5")){
            data.setBoost(1.5);
        }
        else{
            data.setBoost(1);
        }
        // 本人懒就用 Copy & Paste
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        PlayerData.getData(e.getPlayer().getUniqueId()).saveData(true);
    }

}
