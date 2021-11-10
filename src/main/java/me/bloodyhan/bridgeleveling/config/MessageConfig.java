package me.bloodyhan.bridgeleveling.config;

import java.util.Arrays;
import java.util.List;

/**
 * @author Bloody_Han
 */
public class MessageConfig extends ConfigManager{

    public static MessageConfig config;

    @Config(path = "xp-give.block-place")
    public static String XP_GIVE_BLOCK_PLACE = "&b+{amount} 搭路经验 x%bl_boost-format% (方块奖励)";

    @Config(path = "xp-give.kill")
    public static String XP_GIVE_KILL = "&b+{amount} 搭路经验 x%bl_boost-format% (击杀玩家)";

    @Config(path = "xp-give.kill-streak")
    public static String XP_GIVE_KILL_STREAK = "&b+{amount} 搭路经验 x%bl_boost-format% ({streak} 连杀)";

    @Config(path = "xp-give.online")
    public static String XP_GIVE_ONLINE = "&b+{amount} 搭路经验 x%bl_boost-format% (时长奖励)";

    @Config(path = "kill.single")
    public static String KILL = "&c{killer} &7将 &a{victim} &7推入了虚空！";

    @Config(path = "kill.streak")
    public static String KILL_STREAK = "&c{killer} &7将 &a{victim} &7推入了虚空！ &b&l{amount} 连杀！";

    @Config(path = "level-up")
    public static List<String> LEVEL_UP = Arrays.asList(
            "&a&m-----------------------------------------",
            "&e&l升级! &f&l你现在的 &3&l搭路等级 &f&l为 %bl_pattern-B%",
            "",
            "&f&l当前段位进度: &b&l%bl_rank-progress-percentage% %bl_rank% &6➠ %bl_next-rank%",
            "&8[%bl_rank-progress-bar-long%&8]",
            "",
            "&f&l本阶段进阶任务:",
            "  &f&l• 等级: &b%bl_pattern-B%&7/&a%bl_rank-up-level-requirement-format%  %bl_rank-up-level-status%",
            "",
            "&f&l额外进阶任务:",
            "  &f&l• 放置方块数: &b%bl_block-place-format%&7/&a%bl_rank-up-block-place-requirement-format%  %bl_rank-up-block-place-status%",
            "  &f&l• 总击杀数: &b%bl_kill-format%&7/&a%bl_rank-up-kill-requirement-format%  %bl_rank-up-kill-status%",
            "  &f&l• 最大连杀数: &b%bl_max-kill-streak-format%&7/&a%bl_rank-up-kill-streak-requirement-format%  %bl_rank-up-kill-streak-status%",
            "&a&m-----------------------------------------");

    @Config(path = "rank-up")
    public static List<String> RANK_UP = Arrays.asList(
            "&a&m-----------------------------------------",
            "&6&l段位上升! &f&l你现在的 &6&l搭路段位 &f&l为 %bl_rank%",
            "",
            "&f&l当前等级进度: &b&l%bl_level-progress-percentage% &b&l等级 %bl_level% &e➠ 等级 %bl_next-level%",
            "&8[%bl_level-progress-bar-long%&8]",
            "",
            "&f&l本阶段进阶任务:",
            "  &f&l• 等级: &b%bl_pattern-B%&7/&a%bl_rank-up-level-requirement-format%  %bl_rank-up-level-status%",
            "",
            "&f&l额外进阶任务:",
            "  &f&l• 放置方块数: &b%bl_block-place-format%&7/&a%bl_rank-up-block-place-requirement-format%  %bl_rank-up-block-place-status%",
            "  &f&l• 总击杀数: &b%bl_kill-format%&7/&a%bl_rank-up-kill-requirement-format%  %bl_rank-up-kill-status%",
            "  &f&l• 最大连杀数: &b%bl_max-kill-streak-format%&7/&a%bl_rank-up-kill-streak-requirement-format%  %bl_rank-up-kill-streak-status%",
            "&a&m-----------------------------------------");

    @Config(path = "level-max")
    public static List<String> LEVEL_MAX = Arrays.asList(
            "&a&m-----------------------------------------",
            "&e&l升级! &f&l你现在的 &3&l搭路等级 &f&l为 %bl_pattern-B% &6&l最高等级！",
            "",
            "&f&l当前段位进度: &b&l%bl_rank-progress-percentage% %bl_rank% &6➠ %bl_next-rank%",
            "&8[%bl_rank-progress-bar-long%&8]",
            "",
            "&f&l本阶段进阶任务:",
            "  &f&l• 等级: &b%bl_pattern-B%&7/&a%bl_rank-up-level-requirement-format%  %bl_rank-up-level-status%",
            "",
            "&f&l额外进阶任务:",
            "  &f&l• 放置方块数: &b%bl_block-place-format%&7/&a%bl_rank-up-block-place-requirement-format%  %bl_rank-up-block-place-status%",
            "  &f&l• 总击杀数: &b%bl_kill-format%&7/&a%bl_rank-up-kill-requirement-format%  %bl_rank-up-kill-status%",
            "  &f&l• 最大连杀数: &b%bl_max-kill-streak-format%&7/&a%bl_rank-up-kill-streak-requirement-format%  %bl_rank-up-kill-streak-status%",
            "&a&m-----------------------------------------");

    @Config(path = "rank-max")
    public static List<String> RANK_MAX = Arrays.asList(
            "&a&m-----------------------------------------",
            "&6&l段位上升! &f&l你现在的 &6&l搭路段位 &f&l为 %bl_rank% &c&l最高段位！",
            "",
            "&f&l当前等级进度: &b&l%bl_level-progress-percentage% &b&l等级 %bl_level% &e➠ 等级 %bl_next-level%",
            "&8[%bl_level-progress-bar-long%&8]",
            "&a&m-----------------------------------------");

    @Config(path = "rank-up-all-max")
    public static List<String> LEVEL_UP_ALL_MAX = Arrays.asList(
            "&a&m-----------------------------------------",
            "&6&l段位上升! &f&l你现在的 &6&l搭路段位 &f&l为 %bl_rank% &c&l最高段位！",
            "&a&m-----------------------------------------");

    @Config(path = "level-up-all-max")
    public static List<String> RANK_UP_ALL_MAX = Arrays.asList(
            "&a&m-----------------------------------------",
            "&e&l升级! &f&l你现在的 &3&l搭路等级 &f&l为 %bl_pattern-B% &6&l最高等级！",
            "&a&m-----------------------------------------");

    @Config(path = "stat")
    public static List<String> STAT = Arrays.asList(
            "&e玩家 %player_displayname% &e的数据:",
            "&f等级: %bl_pattern-B%  &b%bl_rank-progress-percentage%",
            "&f段位: %bl_rank%",
            "&f总放置方块: &a%bl_block-place-format%",
            "&f总击杀数: &a%bl_kill-format%",
            "&f当前/最大连杀数: &b%bl_kill-streak-format%&7/&a%bl_max-kill-streak-format%");

    public MessageConfig() {
        super("message");
        config = this;
    }

}
