package me.bloodyhan.bridgeleveling.listener;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.bloodyhan.bridgeleveling.Main;
import me.bloodyhan.bridgeleveling.api.PlayerData;
import me.bloodyhan.bridgeleveling.api.PlayerKillEvent;
import me.bloodyhan.bridgeleveling.config.MainConfig;
import me.bloodyhan.bridgeleveling.config.MessageConfig;
import me.bloodyhan.bridgeleveling.config.SoundConfig;
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

import java.util.Map;
import java.util.UUID;

/**
 * @author Bloody_Han
 * @date 2019/02/20
 */
public class KillListener implements Listener {

    private Map<UUID, Pair<UUID, Long>> targetMap = Maps.newHashMap();

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.isCancelled() || !(e.getEntity() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getEntity();
        Player attacker = e.getDamager() instanceof Player
                ? (Player) e.getDamager()
                : e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() != e.getEntity()
                ? (Player) ((Projectile) e.getDamager()).getShooter()
                : null;
        if (attacker == null) {
            return;
        }
        PlayerData data = PlayerData.getData(attacker.getUniqueId());
        if (data == null) {
            return;
        }
        targetMap.put(p.getUniqueId(), new Pair<>(data.getUniqueId(), System.currentTimeMillis() + 10000L));
    }

    public KillListener() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getEntity() == null) {
            return;
        }
        Player p = e.getEntity();
        PlayerData data = PlayerData.getData(p.getUniqueId());
        data.setKillStreak(0);
        Pair<UUID, Long> damageData = targetMap.getOrDefault(data.getUniqueId(), new Pair<>(null, -1L));
        if (damageData.first != null && System.currentTimeMillis() < damageData.second) {
            this.killPlayer(p, Bukkit.getPlayer(damageData.first));
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (e.getTo().getY() < 0.1) {
            PlayerData data = PlayerData.getData(p.getUniqueId());
            data.setKillStreak(0);
            Pair<UUID, Long> damageData = targetMap.getOrDefault(p.getUniqueId(), new Pair<>(null, -1L));
            if (damageData.first != null && System.currentTimeMillis() < damageData.second) {
                this.killPlayer(p, Bukkit.getPlayer(damageData.first));
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
        this.targetMap.remove(victim.getUniqueId());
        for (String cmd : MainConfig.TRIGGER_DEATH_COMMAND) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(victim, cmd
                    .replace("{damager}", damager.getName())
                    .replace("{damager_uuid}", damager.getUniqueId().toString())));
        }
    }

    @Data
    @AllArgsConstructor
    class Pair<A, B> {

        private A first;
        private B second;

    }

    private int killAddXp(Player p) {
        PlayerData data = PlayerData.getData(p.getUniqueId());
        int killXp = (MainConfig.XP_GIVE_KILL_STREAK_BONUS - 1) * MainConfig.XP_GIVE_KILL_STREAK_BONUS + MainConfig.XP_GIVE_KILL;
        if (MainConfig.XP_GIVE_KILL_STREAK_BONUS >= data.getKillStreak()) {
            killXp = (data.getKillStreak() - 1) * MainConfig.XP_GIVE_KILL_STREAK_BONUS + MainConfig.XP_GIVE_KILL;
        }
        return (int) (killXp * data.getBoost());
    }
}
