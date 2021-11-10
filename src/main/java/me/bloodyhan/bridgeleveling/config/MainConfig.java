package me.bloodyhan.bridgeleveling.config;

import com.google.common.collect.Lists;
import lombok.Getter;
import me.bloodyhan.bridgeleveling.api.Rank;

import java.util.Comparator;
import java.util.List;

/**
 * @author Bloody_Han
 */
public class MainConfig extends ConfigManager{

    public static MainConfig config;
    @Getter private List<Rank> rankList = Lists.newArrayList();

    @Config(path = "mysql.enabled")
    public static boolean MYSQL_ENABLED = false;

    @Config(path = "mysql.url")
    public static String MYSQL_URL = "localhost:3306";

    @Config(path = "mysql.user")
    public static String MYSQL_USER = "root";

    @Config(path = "mysql.passwd")
    public static String MYSQL_PASSWD = "passwd";

    @Config(path = "mysql.database")
    public static String MYSQL_DATABASE = "database";

    @Config(path = "progress-bar.unlocked-color")
    public static String UNLOCK_COLOR = "&b";

    @Config(path = "progress-bar.locked-color")
    public static String LOCK_COLOR = "&7";

    @Config(path = "progress-bar.length")
    public static int LENGTH = 10;

    @Config(path = "progress-bar.symbol")
    public static String SYMBOL = "■";

    @Config(path = "settings.max-level")
    public static int MAX_LEVEL = 1000;

    @Config(path = "settings.xp-give.count.kill.single")
    public static int XP_GIVE_KILL = 10;

    @Config(path = "settings.xp-give.count.kill.streak-bonus")
    public static int XP_GIVE_KILL_STREAK_BONUS = 10;

    @Config(path = "settings.xp-give.count.block-place")
    public static int XP_GIVE_BLOCK_PLACE = 50;

    @Config(path = "settings.xp-give.count.online")
    public static int XP_GIVE_ONLINE = 100;

    @Config(path = "settings.xp-give.online")
    public static boolean XP_GIVE_ONLINE_ENABLED = true;

    @Config(path = "settings.xp-give.block")
    public static boolean XP_GIVE_BLOCK_ENABLED = true;

    @Config(path = "settings.xp-give.kill")
    public static boolean XP_GIVE_KILL_ENABLED = true;

    @Config(path = "settings.requirement.online-millis")
    public static int REQUIREMENT_ONLINE_MILLIS = 10 * 60 * 1000;

    @Config(path = "settings.requirement.block-place")
    public static int REQUIREMENT_BLOCK_PLACE_NEED = 50;

    @Config(path = "settings.requirement.kill-streak-stop-increase")
    public static int REQUIREMENT_KILL_STREAK_STOP_INCREASE = 10;

    @Config(path = "settings.send-message.kill")
    public static boolean SEND_MESSAGE_KILL = true;

    @Config(path = "settings.send-message.rank-up")
    public static boolean SEND_MESSAGE_RANK_UP = true;

    @Config(path = "settings.send-message.level-up")
    public static boolean SEND_MESSAGE_LEVEL_UP = true;

    @Config(path = "settings.send-message.xp-gain")
    public static boolean SEND_MESSAGE_XP_GAIN = true;

    @Config(path = "settings.play-sound")
    public static boolean SOUND_PLAY = true;

    @Config(path = "settings.trigger.level-up")
    public static List<String> TRIGGER_LEVEL_UP_COMMAND = Lists.newArrayList();

    @Config(path = "settings.trigger.kill-single")
    public static List<String> TRIGGER_KILL_SINGLE_COMMAND = Lists.newArrayList();

    @Config(path = "settings.trigger.kill-streak")
    public static List<String> TRIGGER_KILL_STREAK_COMMAND = Lists.newArrayList();

    @Config(path = "settings.trigger.add-xp")
    public static List<String> TRIGGER_ADD_XP_COMMAND = Lists.newArrayList();

    @Config(path = "settings.trigger.death")
    public static List<String> TRIGGER_DEATH_COMMAND = Lists.newArrayList();

    @Config(path = "settings.trigger.rank-up")
    public static List<String> TRIGGER_RANK_UP_COMMAND = Lists.newArrayList();

    public static int getLevelUpCost(int level){
        return config.getYml().contains("level-up-cost." + level)
                ? config.getYml().getInt("level-up-cost." + level)
                : config.getYml().getInt("level-up-cost.other");
    }

    public MainConfig() {
        super("config");
        config = this;
        load();
    }

    @Override
    public void reload(){
        super.reload();
        load();
    }

    private void load(){
        config.getYml().options().copyDefaults(true);
        if(config.getYml().get("rank") == null){
            config.addDefault("rank.unlock.name", "&c段位未解锁");
            config.addDefault("rank.unlock.pattern-A", "&8[{level}✫]");
            config.addDefault("rank.unlock.pattern-B", "&8{level}✫");

            config.addDefault("rank.stone.name", "&7石头");
            config.addDefault("rank.stone.pattern-A", "&7[{level}✫]");
            config.addDefault("rank.stone.pattern-B", "&7{level}✫");
            config.addDefault("rank.stone.rank-up-cost.level-requirement", 20);
            config.addDefault("rank.stone.rank-up-cost.block-place-requirement", 100);

            config.addDefault("rank.iron.name", "&f铁锭");
            config.addDefault("rank.iron.pattern-A", "&f[{level}✫]");
            config.addDefault("rank.iron.pattern-B", "&f{level}✫");
            config.addDefault("rank.iron.rank-up-cost.kill-requirement", 50);
            config.addDefault("rank.iron.rank-up-cost.level-requirement", 100);
            config.addDefault("rank.iron.rank-up-cost.block-place-requirement", 500);

            config.addDefault("rank.gold.name", "&6金锭");
            config.addDefault("rank.gold.pattern-A", "&6[{level}✫]");
            config.addDefault("rank.gold.pattern-B", "&6{level}✫");
            config.addDefault("rank.gold.rank-up-cost.kill-requirement", 100);
            config.addDefault("rank.gold.rank-up-cost.kill-streak-requirement", 3);
            config.addDefault("rank.gold.rank-up-cost.level-requirement", 200);
            config.addDefault("rank.gold.rank-up-cost.block-place-requirement", 1000);

            config.addDefault("rank.diamond.name", "&b钻石");
            config.addDefault("rank.diamond.pattern-A", "&b[{level}✫]");
            config.addDefault("rank.diamond.pattern-B", "&b{level}✫");
            config.addDefault("rank.diamond.rank-up-cost.kill-requirement", 200);
            config.addDefault("rank.diamond.rank-up-cost.kill-streak-requirement", 5);
            config.addDefault("rank.diamond.rank-up-cost.level-requirement", 300);
            config.addDefault("rank.diamond.rank-up-cost.block-place-requirement", 1500);

            config.addDefault("rank.emerald.name", "&2绿宝石");
            config.addDefault("rank.emerald.pattern-A", "&2[{level}✫]");
            config.addDefault("rank.emerald.pattern-B", "&2{level}✫");
            config.addDefault("rank.emerald.rank-up-cost.kill-requirement", 400);
            config.addDefault("rank.emerald.rank-up-cost.kill-streak-requirement", 7);
            config.addDefault("rank.emerald.rank-up-cost.level-requirement", 400);
            config.addDefault("rank.emerald.rank-up-cost.block-place-requirement", 3000);

            config.addDefault("rank.sapphire.name", "&3蓝宝石");
            config.addDefault("rank.sapphire.pattern-A", "&3[{level}✫]");
            config.addDefault("rank.sapphire.pattern-B", "&3{level}✫");
            config.addDefault("rank.sapphire.rank-up-cost.kill-requirement", 600);
            config.addDefault("rank.sapphire.rank-up-cost.kill-streak-requirement", 10);
            config.addDefault("rank.sapphire.rank-up-cost.level-requirement", 500);
            config.addDefault("rank.sapphire.rank-up-cost.block-place-requirement", 5000);

            config.addDefault("rank.ruby.name", "&4红宝石");
            config.addDefault("rank.ruby.pattern-A", "&4[{level}✫]");
            config.addDefault("rank.ruby.pattern-B", "&4{level}✫");
            config.addDefault("rank.ruby.rank-up-cost.kill-requirement", 800);
            config.addDefault("rank.ruby.rank-up-cost.kill-streak-requirement", 12);
            config.addDefault("rank.ruby.rank-up-cost.level-requirement", 600);
            config.addDefault("rank.ruby.rank-up-cost.block-place-requirement", 1000);

            config.addDefault("rank.crystal.name", "&d水晶");
            config.addDefault("rank.crystal.pattern-A", "&d[{level}✫]");
            config.addDefault("rank.crystal.pattern-B", "&d{level}✫");
            config.addDefault("rank.crystal.rank-up-cost.kill-requirement", 1000);
            config.addDefault("rank.crystal.rank-up-cost.kill-streak-requirement", 14);
            config.addDefault("rank.crystal.rank-up-cost.level-requirement", 700);
            config.addDefault("rank.crystal.rank-up-cost.block-place-requirement", 20000);

            config.addDefault("rank.opal.name", "&9蛋白石");
            config.addDefault("rank.opal.pattern-A", "&9[{level}✫]");
            config.addDefault("rank.opal.pattern-B", "&9{level}✫");
            config.addDefault("rank.opal.rank-up-cost.kill-requirement", 2500);
            config.addDefault("rank.opal.rank-up-cost.kill-streak-requirement", 16);
            config.addDefault("rank.opal.rank-up-cost.level-requirement", 800);
            config.addDefault("rank.opal.rank-up-cost.block-place-requirement", 50000);

            config.addDefault("rank.amethyst.name", "&5紫宝石");
            config.addDefault("rank.amethyst.pattern-A", "&5[{level}✫]");
            config.addDefault("rank.amethyst.pattern-B", "&5{level}✫");
            config.addDefault("rank.amethyst.rank-up-cost.kill-requirement", 5000);
            config.addDefault("rank.amethyst.rank-up-cost.kill-streak-requirement", 18);
            config.addDefault("rank.amethyst.rank-up-cost.level-requirement", 900);
            config.addDefault("rank.amethyst.rank-up-cost.block-place-requirement", 100000);

            config.addDefault("rank.rainbow.name", "&cR&6a&ei&an&bb&do&5w");
            config.addDefault("rank.rainbow.pattern-A", "&c[&6{level_1}&e{level_2}&a{level_3}&b{level_4}&d✫&5]");
            config.addDefault("rank.rainbow.pattern-B", "&c{level_1}&6{level_2}&e{level_3}&a{level_4}&b✫");
            config.addDefault("rank.rainbow.rank-up-cost.kill-requirement", 10000);
            config.addDefault("rank.rainbow.rank-up-cost.kill-streak-requirement", 20);
            config.addDefault("rank.rainbow.rank-up-cost.level-requirement", 1000);
            config.addDefault("rank.rainbow.rank-up-cost.block-place-requirement", 1000000);
        }else{
            config.addDefault("rank.unlock.name", "&c段位未解锁");
            config.addDefault("rank.unlock.pattern-A", "&8[{level}✫]");
            config.addDefault("rank.unlock.pattern-B", "&8{level}✫");
        }

        config.addDefault("level-up-cost.1", 50);
        config.addDefault("level-up-cost.2", 100);
        config.addDefault("level-up-cost.3", 150);
        config.addDefault("level-up-cost.4", 200);
        config.addDefault("level-up-cost.5", 300);
        config.addDefault("level-up-cost.6", 400);
        config.addDefault("level-up-cost.7", 500);
        config.addDefault("level-up-cost.8", 600);
        config.addDefault("level-up-cost.9", 700);
        config.addDefault("level-up-cost.10", 800);
        config.addDefault("level-up-cost.11", 900);
        config.addDefault("level-up-cost.12", 1000);
        config.addDefault("level-up-cost.13", 1500);
        config.addDefault("level-up-cost.14", 2000);
        config.addDefault("level-up-cost.15", 3000);
        config.addDefault("level-up-cost.16", 4000);
        config.addDefault("level-up-cost.17", 4500);
        config.addDefault("level-up-cost.other", 5000);

        config.getYml().getConfigurationSection("rank").getKeys(false).forEach(path -> {
            Rank rank = new Rank(path,
                    config.getString("rank." + path + ".name", "&cERROR"),
                    config.getString("rank." + path + ".pattern-A", "&cERROR"),
                    config.getString("rank." + path + ".pattern-B", "&cERROR"),
                    config.getYml().getInt("rank." + path + ".rank-up-cost.level-requirement", 0),
                    config.getYml().getInt("rank." + path + ".rank-up-cost.block-place-requirement", 0),
                    config.getYml().getInt("rank." + path + ".rank-up-cost.kill-requirement", 0),
                    config.getYml().getInt("rank." + path + ".rank-up-cost.kill-streak-requirement", 0));
            rankList.add(rank);});
        rankList.sort(new Comparator<Rank>() {
            @Override
            public int compare(Rank o1, Rank o2) {
                return o2.getLevelRequirement() - o1.getLevelRequirement();
            }
        });
        config.save();
    }

}
