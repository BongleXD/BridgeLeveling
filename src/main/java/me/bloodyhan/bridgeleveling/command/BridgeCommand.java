package me.bloodyhan.bridgeleveling.command;

import me.bloodyhan.bridgeleveling.Main;
import me.bloodyhan.bridgeleveling.api.PlayerData;
import me.bloodyhan.bridgeleveling.config.MessageConfig;
import me.bloodyhan.bridgeleveling.util.Method;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Bloody_Han
 */
public class BridgeCommand extends CommandManager {

    public BridgeCommand() {
        super("bridgeleveling", "管理搭路等级命令", "/bl help 显示帮助页面", "bl.admin", "bl");
    }

    @Cmd
    public void mainCommand(CommandSender sender, String[] args){
        sender.sendMessage("§9BridgeLeveling §e由 §aBloody_Han §e编写， 当前插件版本 §b" + Main.getInstance().getDescription().getVersion());
    }

    @Cmd(arg = "help", perm = "bl.admin")
    public void showHelp(CommandSender sender, String[] args){
        sender.sendMessage("§cBridgeLeveling v" + Main.getInstance().getDescription().getVersion() + " 帮助页面");
        sender.sendMessage("§c/bl help 显示帮助页面");
        sender.sendMessage("§c/bl reload 重置配置文件");
        sender.sendMessage("§c/bl stat [玩家名] 查询玩家信息");
        sender.sendMessage("§c/bl add <xp/level> <玩家名> <数量> 增加经验/等级");
        sender.sendMessage("§c/bl set <xp/level/totalBlock/kill/killStreak> <玩家名> <数量> 设置经验/等级/总方块放置数/击杀/连杀");
    }

    @Cmd(arg = "reload", perm = "bl.admin")
    public void reload(CommandSender sender, String[] args){
        Main.getInstance().reloadConfig();
        sender.sendMessage("§a配置文件已重置！");
    }

    @Cmd(arg = "stat <player>", perm = "bl.stat")
    public void stat(CommandSender sender, String[] args){
        Player p = Bukkit.getPlayer(args[1]);
        for (String msg : MessageConfig.STAT) {
            sender.sendMessage(PlaceholderAPI.setPlaceholders(p, Method.transColor(msg)));
        }
    }

    @Cmd(arg = "add xp <player> <integer>", perm = "bl.admin")
    public void addXp(CommandSender sender, String[] args){
        Player p = Bukkit.getPlayer(args[2]);
        int count = Integer.parseInt(args[3]);
        PlayerData data = PlayerData.getData(p.getUniqueId());
        data.addXp(count);
        sender.sendMessage("§a已经为 §e" + p.getDisplayName() + " §a增加经验 " + count + "！");
    }

    @Cmd(arg = "add level <player> <integer>", perm = "bl.admin")
    public void addLevel(CommandSender sender, String[] args){
        Player p = Bukkit.getPlayer(args[2]);
        int count = Integer.parseInt(args[3]);
        PlayerData data = PlayerData.getData(p.getUniqueId());
        data.setLevel(data.getLevel() + count - 1);
        data.levelUp();
        sender.sendMessage("§a已经为 §e" + p.getDisplayName() + " §a增加等级 " + count + "！");
    }

    @Cmd(arg = "set xp <player> <integer>", perm = "bl.admin")
    public void setXp(CommandSender sender, String[] args){
        Player p = Bukkit.getPlayer(args[2]);
        int count = Integer.parseInt(args[3]);
        PlayerData data = PlayerData.getData(p.getUniqueId());
        data.setXp(count);
        data.addXp(0);
        sender.sendMessage("§a已经为 §e" + p.getDisplayName() + " §a设置经验 " + count + "！");
    }

    @Cmd(arg = "set level <player> <integer>", perm = "bl.admin")
    public void setLevel(CommandSender sender, String[] args){
        Player p = Bukkit.getPlayer(args[2]);
        int count = Integer.parseInt(args[3]);
        PlayerData data = PlayerData.getData(p.getUniqueId());
        data.setLevel(count - 1);
        data.levelUp();
        sender.sendMessage("§a已经为 §e" + p.getDisplayName() + " §a设置等级 " + count + "！");
    }

    @Cmd(arg = "set totalBlock <player> <integer>", perm = "bl.admin")
    public void setTotalBlock(CommandSender sender, String[] args){
        Player p = Bukkit.getPlayer(args[2]);
        int count = Integer.parseInt(args[3]);
        PlayerData data = PlayerData.getData(p.getUniqueId());
        data.setTotalBlockPlace(count);
        data.saveData(false);
        sender.sendMessage("§a已经为 §e" + p.getDisplayName() + " §a设置总放置方块 " + count + "！");
    }

    @Cmd(arg = "set kill <player> <integer>", perm = "bl.admin")
    public void setKill(CommandSender sender, String[] args){
        Player p = Bukkit.getPlayer(args[2]);
        int count = Integer.parseInt(args[3]);
        PlayerData data = PlayerData.getData(p.getUniqueId());
        data.setTotalKill(count);
        data.saveData(false);
        sender.sendMessage("§a已经为 §e" + p.getDisplayName() + " §a设置击杀 " + count + "！");
    }

    @Cmd(arg = "set killStreak <player> <integer>", perm = "bl.admin")
    public void setKillStreak(CommandSender sender, String[] args){
        Player p = Bukkit.getPlayer(args[2]);
        int count = Integer.parseInt(args[3]);
        PlayerData data = PlayerData.getData(p.getUniqueId());
        data.setMaxKillStreak(count);
        data.saveData(false);
        sender.sendMessage("§a已经为 §e" + p.getDisplayName() + " §a设置最大连杀 " + count + "！");
    }

}
