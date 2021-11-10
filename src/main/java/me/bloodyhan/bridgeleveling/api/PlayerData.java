package me.bloodyhan.bridgeleveling.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.bloodyhan.bridgeleveling.Main;
import me.bloodyhan.bridgeleveling.config.MainConfig;
import me.bloodyhan.bridgeleveling.config.MessageConfig;
import me.bloodyhan.bridgeleveling.config.SoundConfig;
import me.bloodyhan.bridgeleveling.database.SQLite;
import me.bloodyhan.bridgeleveling.database.SqlValue;
import me.bloodyhan.bridgeleveling.database.Value;
import me.bloodyhan.bridgeleveling.database.ValueType;
import me.bloodyhan.bridgeleveling.task.BridgeTask;
import me.bloodyhan.bridgeleveling.util.Method;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author Bloody_Han
 */
@Getter @Setter
public class PlayerData {

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE) private UUID uuid;
    private int level = 1;
    private int xp = 0;
    private int maxKillStreak = 0;
    private int totalBlockPlace = 0;
    private int totalKill = 0;
    private double boost = 1.0;
    private int levelUpCost;
    private int killStreak = 0;
    private int blockPlace = 0;
    private Player lastDamager = null;
    private Rank rank = null;
    private BridgeTask task;
    private static HashMap<UUID, PlayerData> dataMap = Maps.newHashMap();

    public PlayerData(@NonNull UUID uuid){
        this.uuid = uuid;
        load();
        loadRank();
        this.levelUpCost = MainConfig.getLevelUpCost(level);
        dataMap.put(uuid, this);
    }

    public static PlayerData getData(UUID uuid){
        return dataMap.getOrDefault(uuid, null);
    }

    private void load() {
        if(!Main.getInstance().getBlockDatabase().checkDataExists("player_levels", "uuid", uuid.toString())){
            this.saveData(false);
            return;
        }
        try {
            List<Object> list = Main.getInstance().getBlockDatabase().getData("player_levels", "uuid", this.uuid.toString(),
                    "level",
                    "xp",
                    "totalBlockPlaced",
                    "maxKillStreaks",
                    "totalKill");
            this.level = (int) list.get(0);
            this.xp = (int) list.get(1);
            this.totalBlockPlace = (int) list.get(2);
            this.maxKillStreak = (int) list.get(3);
            this.totalKill = (int) list.get(4);
        }catch (NullPointerException ex){
            Main.log("§c数据导入失败 UUID: " + uuid);
        }
    }

    public void loadRank(){
        for (Rank rank : MainConfig.config.getRankList()) {
            if(level >= rank.getLevelRequirement()
                    && totalKill >= rank.getKillRequirement()
                    && totalBlockPlace >= rank.getBlockPlaceRequirement()
                    && maxKillStreak >= rank.getKillStreakRequirement()){
                this.rank = rank;
                break;
            }
        }
    }

    public void addXp(int amount){
        xp += amount;
        for (String cmd : MainConfig.TRIGGER_ADD_XP_COMMAND) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(Bukkit.getPlayer(this.uuid), cmd
                    .replace("{amount}", String.valueOf(amount))));
        }
        checkRankUp();
        if(this.xp >= this.levelUpCost
                && (this.level < MainConfig.MAX_LEVEL || MainConfig.MAX_LEVEL == -1)) {
            this.levelUp();
        }
    }

    public Rank getNextRank(){
        List<Rank> rankList = Lists.newArrayList(MainConfig.config.getRankList());
        Collections.reverse(rankList);
        boolean b = false;
        for (Rank rank : rankList) {
            if(b){
                return rank;
            }
            if(rank == this.getRank()){
                b = true;
            }
        }
        return null;
    }

    public void levelUp(){
        Player p = Bukkit.getPlayer(this.uuid);
        int level = this.level;
        for (String cmd : MainConfig.TRIGGER_LEVEL_UP_COMMAND) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(Bukkit.getPlayer(this.uuid), cmd));
        }
        checkLevelUp();
        Bukkit.getPluginManager().callEvent(new PlayerLevelUpEvent(p, this.getLevel(), this.levelUpCost));
        List<String> msg = Lists.newArrayList();
        if(this.level >= MainConfig.MAX_LEVEL && MainConfig.MAX_LEVEL != -1 && getNextRank() == null){
            msg = MessageConfig.LEVEL_UP_ALL_MAX;
        } else if((this.getLevel() == MainConfig.MAX_LEVEL && MainConfig.MAX_LEVEL != -1)){
            msg = MessageConfig.LEVEL_MAX;
        } else {
            msg = MessageConfig.LEVEL_UP;
        }
        if (MainConfig.SOUND_PLAY) {
            Location loc = p.getLocation();
            p.playSound(loc, Sound.valueOf(SoundConfig.LEVEL_UP), 1F, SoundConfig.LEVEL_UP_PITCH);
        }
        if(MainConfig.SEND_MESSAGE_LEVEL_UP){
            for (String s : msg){
                p.sendMessage(PlaceholderAPI.setPlaceholders(p, Method.transColor(s)));
            }
        }
        checkRankUp();
        this.saveData(false);
    }

    private void checkLevelUp(){
        ++this.level;
        this.xp -= this.levelUpCost;
        if(this.xp < 0){
            this.xp = 0;
        }
        this.levelUpCost = MainConfig.getLevelUpCost(level);
        if(this.xp >= this.levelUpCost
                && (this.level < MainConfig.MAX_LEVEL || MainConfig.MAX_LEVEL == -1)) {
            this.checkLevelUp();
        }
    }

    public void checkRankUp(){
        if(getNextRank() == null){
            return;
        }
        if(this.totalKill < this.getNextRank().getKillRequirement()
                || this.maxKillStreak < this.getNextRank().getKillStreakRequirement()
                || this.totalBlockPlace < this.getNextRank().getBlockPlaceRequirement()
                || this.level < this.getNextRank().getLevelRequirement()){
            return;
        }
        Player p = Bukkit.getPlayer(this.uuid);
        Rank rank = this.rank;
        this.rank = getNextRank();
        Bukkit.getPluginManager().callEvent(new PlayerRankUpEvent(p, this.getRank()));
        List<String> msg = Lists.newArrayList();
        if((this.level >= MainConfig.MAX_LEVEL && MainConfig.MAX_LEVEL != -1) && getNextRank() == null){
            msg = MessageConfig.RANK_UP_ALL_MAX;
        } else if(this.getNextRank() == null){
            msg = MessageConfig.RANK_MAX;
        } else {
            msg = MessageConfig.RANK_UP;
        }
        if (MainConfig.SOUND_PLAY) {
            Location loc = p.getLocation();
            p.playSound(loc, Sound.valueOf(SoundConfig.RANK_UP), 1F, SoundConfig.RANK_UP_PITCH);
        }
        if(MainConfig.SEND_MESSAGE_RANK_UP){
            for (String s : msg){
                p.sendMessage(PlaceholderAPI.setPlaceholders(p, Method.transColor(s)));
            }
        }
        for (String cmd : MainConfig.TRIGGER_RANK_UP_COMMAND) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(Bukkit.getPlayer(this.uuid), cmd));
        }
        checkRankUp();
        this.saveData(false);
    }

    public void saveData(boolean destroy){
        Main.getInstance().getBlockDatabase().putData("player_levels", "uuid", this.uuid.toString(),
                new SqlValue("level", this.level),
                new SqlValue("xp", this.xp),
                new SqlValue("totalBlockPlaced", this.totalBlockPlace),
                new SqlValue("maxKillStreaks", this.maxKillStreak),
                new SqlValue("totalKill", this.totalKill));
        if (destroy) {
            dataMap.remove(uuid);
        }
    }

    public void deathCleanData(){
        this.blockPlace = 0;
        this.killStreak = 0;
    }

    public static void init(){
        Main.getInstance().getBlockDatabase().create("player_levels",
                Main.getInstance().getBlockDatabase() instanceof SQLite ? new Value(ValueType.SQLITE_ID, "id") : new Value(ValueType.ID, "id"),
                Main.getInstance().getBlockDatabase() instanceof SQLite ? new Value(ValueType.SQLITE_STRING, "uuid") : new Value(ValueType.STRING, "uuid"),
                new Value(ValueType.INTEGER, "level"),
                new Value(ValueType.INTEGER, "xp"),
                new Value(ValueType.INTEGER, "totalBlockPlaced"),
                new Value(ValueType.INTEGER, "maxKillStreaks"),
                new Value(ValueType.INTEGER, "totalKill"));
    }

    public UUID getUniqueId() {
        return this.uuid;
    }

}