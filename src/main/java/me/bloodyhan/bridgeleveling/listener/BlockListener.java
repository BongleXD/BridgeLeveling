package me.bloodyhan.bridgeleveling.listener;

import me.bloodyhan.bridgeleveling.Main;
import me.bloodyhan.bridgeleveling.api.PlayerData;
import me.bloodyhan.bridgeleveling.config.MainConfig;
import me.bloodyhan.bridgeleveling.config.MessageConfig;
import me.bloodyhan.bridgeleveling.util.Method;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author Bloody_Han
 */
public class BlockListener implements Listener {

    public BlockListener(){
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if (!e.isCancelled()) {
            PlayerData data = PlayerData.getData(p.getUniqueId());
            data.setTotalBlockPlace(data.getTotalBlockPlace() + 1);
            data.checkRankUp();
            if((data.getLevel() != MainConfig.MAX_LEVEL || MainConfig.MAX_LEVEL == -1) && MainConfig.XP_GIVE_BLOCK_ENABLED) {
                data.setBlockPlace(data.getBlockPlace() + 1);
                if(MainConfig.XP_GIVE_BLOCK_PLACE <= data.getBlockPlace()) {
                    data.addXp((int) (MainConfig.XP_GIVE_BLOCK_PLACE * data.getBoost()));
                    data.setBlockPlace(0);
                    if(MainConfig.SEND_MESSAGE_XP_GAIN){
                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, Method.transColor(MessageConfig.XP_GIVE_BLOCK_PLACE.replace("{amount}", String.valueOf((int) (MainConfig.XP_GIVE_BLOCK_PLACE * data.getBoost()))))));
                    }
                }
            }
        }
    }

}
