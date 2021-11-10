package me.bloodyhan.bridgeleveling.listener;

import me.bloodyhan.bridgeleveling.Main;
import me.bloodyhan.bridgeleveling.api.PlayerData;
import me.bloodyhan.bridgeleveling.api.PlayerKillEvent;
import me.bloodyhan.bridgeleveling.config.MainConfig;
import me.bloodyhan.bridgeleveling.config.MessageConfig;
import me.bloodyhan.bridgeleveling.config.SoundConfig;
import me.bloodyhan.bridgeleveling.task.TargetResetTask;
import me.bloodyhan.bridgeleveling.util.Method;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * @author Bloody_Han
 * @date 2019/02/20
 */
public class KillListener implements Listener {

    public KillListener(){
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player) && PlayerData.getData(e.getEntity().getUniqueId()) == null){
            return;
        }
        Player p = (Player) e.getEntity();
        if (e.getDamager() instanceof Player && !e.isCancelled() && e.getDamager() != e.getEntity()) {
            PlayerData data = PlayerData.getData(p.getUniqueId());
            Player damager = (Player) e.getDamager();
            data.setLastDamager(damager);
            if(data.getTask() != null){
                data.getTask().cancel();
            }
            data.setTask(new TargetResetTask(p));
        }
        else if (e.getDamager() instanceof Projectile && !e.isCancelled() && ((Projectile) e.getDamager()).getShooter() != e.getEntity()) {
            PlayerData data = PlayerData.getData(p.getUniqueId());
            Player shooter = (Player) ((Projectile) e.getDamager()).getShooter();
            data.setLastDamager(shooter);
            if(data.getTask() != null){
                data.getTask().cancel();
            }
            data.setTask(new TargetResetTask(p));
        }
        else{
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        if(e.getEntity() == null){
            return;
        }
        Player p = e.getEntity();
        PlayerData data = PlayerData.getData(p.getUniqueId());
        data.setKillStreak(0);
        if (data.getLastDamager() != null) {
            this.killPlayer(p, data.getLastDamager());
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (e.getTo().getY() < 0.1) {
            PlayerData data = PlayerData.getData(p.getUniqueId());
            data.setKillStreak(0);
            if (data.getLastDamager() != null) {
                this.killPlayer(p, data.getLastDamager());
            }
        }
    }

    public void killPlayer(Player victim, Player damager){
        PlayerData data = PlayerData.getData(damager.getUniqueId());
        data.setKillStreak(data.getKillStreak() + 1);
        data.setTotalKill(data.getTotalKill() + 1);
        if (data.getKillStreak() > data.getMaxKillStreak()) {
            data.setMaxKillStreak(data.getKillStreak());
        }
        boolean flag = (data.getLevel() != MainConfig.MAX_LEVEL || MainConfig.MAX_LEVEL == -1) && MainConfig.SEND_MESSAGE_XP_GAIN;
        if (data.getKillStreak() < 2){
            for (String cmd : MainConfig.TRIGGER_KILL_SINGLE_COMMAND) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(damager, cmd
                        .replace("{victim}", victim.getName())
                        .replace("{victim_uuid}", victim.getUniqueId().toString())
                        .replace("{amount}", String.valueOf(data.getKillStreak()))));
            }
            if(MainConfig.SEND_MESSAGE_KILL){
                String s = MessageConfig.KILL.replace("{victim}", victim.getDisplayName()).replace("{killer}", damager.getDisplayName());
                for(Player online : Bukkit.getOnlinePlayers()){
                    online.sendMessage(s);
                }
            }
            if(flag) {
                damager.sendMessage(PlaceholderAPI.setPlaceholders(damager, MessageConfig.XP_GIVE_KILL
                        .replace("{amount}", Method.toTrisection(killAddXp(damager)))
                        .replace("{streak}", Method.toTrisection(data.getKillStreak()))));
                if(MainConfig.XP_GIVE_KILL_ENABLED){
                    data.addXp(killAddXp(damager));
                }
            }
        } else{
            for (String cmd : MainConfig.TRIGGER_KILL_STREAK_COMMAND) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(damager, cmd
                        .replace("{victim}", victim.getName())
                        .replace("{victim_uuid}", victim.getUniqueId().toString())
                        .replace("{amount}", String.valueOf(data.getKillStreak()))));
            }
            if(MainConfig.SEND_MESSAGE_KILL){
                String s = MessageConfig.KILL_STREAK
                        .replace("{killer}", damager.getDisplayName())
                        .replace("{amount}", String.valueOf(data.getKillStreak()))
                        .replace("{victim}", victim.getDisplayName());
                for(Player online : Bukkit.getOnlinePlayers()){
                    online.sendMessage(s);
                }
            }
            if(flag) {
                damager.sendMessage(PlaceholderAPI.setPlaceholders(damager, MessageConfig.XP_GIVE_KILL_STREAK
                        .replace("{amount}", Method.toTrisection(killAddXp(damager)))
                        .replace("{streak}", Method.toTrisection(data.getKillStreak()))));
                if(MainConfig.XP_GIVE_KILL_ENABLED){
                    data.addXp(killAddXp(damager));
                }
            }
        }
        if (MainConfig.SOUND_PLAY) {
            Location loc = damager.getLocation();
            damager.playSound(loc, Sound.valueOf(SoundConfig.KILL), 1f, SoundConfig.KILL_PITCH);
        }
        data.checkRankUp();
        Bukkit.getPluginManager().callEvent(new PlayerKillEvent(victim, damager));
        PlayerData.getData(victim.getUniqueId()).setLastDamager(null);
        for (String cmd : MainConfig.TRIGGER_DEATH_COMMAND) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(victim, cmd
                    .replace("{damager}", damager.getName())
                    .replace("{damager_uuid}", damager.getUniqueId().toString())));
        }
    }

    private int killAddXp(Player p) {
        PlayerData data = PlayerData.getData(p.getUniqueId());
        int killXp = ( MainConfig.XP_GIVE_KILL_STREAK_BONUS - 1 ) * MainConfig.XP_GIVE_KILL_STREAK_BONUS + MainConfig.XP_GIVE_KILL;
        if(MainConfig.XP_GIVE_KILL_STREAK_BONUS >= data.getKillStreak()) {
            killXp = ( data.getKillStreak() - 1 ) * MainConfig.XP_GIVE_KILL_STREAK_BONUS + MainConfig.XP_GIVE_KILL;
        }
        return (int) (killXp * data.getBoost());
    }
}
