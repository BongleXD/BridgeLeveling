package me.bloodyhan.bridgeleveling.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Bongle (Boougouh)
 * @createDate 2020/04/05
 * @version 2.1
 */

public class CommandManager extends BukkitCommand {

    private final HashMap<CommandSender, HashMap<Method, Long>> coolDownMap = new HashMap<>();
    private final String usage;
    private CommandManager clazz;
    private boolean containEmptyMethod = false;

    public CommandManager(String name, String desc, String usage, String permission, String... aliases) {
        super(name);
        if(desc != null) {
            this.setDescription(desc);
        }
        if(aliases.length > 0) {
            List<String> list = new ArrayList<>();
            Collections.addAll(list, aliases);
            this.setAliases(list);
        }
        this.usage = usage == null ? "" : "§c用法： " + usage;
        this.setUsage(this.usage);
        this.setPermission(permission);
    }

    @Override
    public String getUsage(){
        return usage;
    }

    public void sendUsage(CommandSender sender, String usage){
        sender.sendMessage(usage);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        Method method = getMethodBySubCmd(args, sender);
        if (method != null) {
            if(this.checkCanExecute(sender, method, args)) {
                try {
                    method.invoke(clazz, sender, args);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }else if(this.usage != null && !this.usage.isEmpty()){
            if(this.getPermission() != null && !sender.hasPermission(this.getPermission())){
                if(!this.containEmptyMethod) {
                    sender.sendMessage(this.getPermissionMessage() == null ? "§c很抱歉， 但是你没有足够的权限来执行这个指令。如果你认为这是个错误， 请联系管理员或者客服！" : this.getPermissionMessage());
                }
            }else{
                sender.sendMessage(this.usage);
            }
        }
        return false;
    }

    public boolean checkInteger(String value){
        try {
            Integer.parseInt(value);
            return true;
        }catch (NumberFormatException ex){
            return false;
        }
    }

    public boolean checkDecimal(String value){
        try {
            Double.parseDouble(value);
            return true;
        }catch (NumberFormatException ex){
            return false;
        }
    }

    private boolean checkCanExecute(CommandSender sender, Method method, String[] args){
        Cmd cmd = method.getAnnotation(Cmd.class);
        if(cmd == null){
            return false;
        }
        if(coolDownMap.containsKey(sender)) {
            if(coolDownMap.get(sender).containsKey(method)) {
                if (coolDownMap.get(sender).get(method) > System.currentTimeMillis()){
                    double coolDown = ((double) coolDownMap.get(sender).get(method) - (double) System.currentTimeMillis()) / 1000;
                    if (coolDown == 0.0) {
                        coolDown = 0.1;
                    }
                    sender.sendMessage("§c指令冷却中！ 请 §e" + new BigDecimal(coolDown).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue() + " §c秒后再试！");
                    return false;
                }else{
                    coolDownMap.get(sender).remove(method);
                }
            }
        }
        if(!sender.hasPermission(cmd.perm()) && !cmd.perm().isEmpty()){
            sender.sendMessage(cmd.permMessage().isEmpty() ? "§c你不能这么做！" : cmd.permMessage());
            return false;
        }
        if(sender instanceof Player){
            if(cmd.only() == CommandOnly.CONSOLE){
                sender.sendMessage("§c此指令仅控制台可执行！");
                return false;
            }
        }else{
            if(cmd.only() == CommandOnly.PLAYER){
                sender.sendMessage("§c此指令仅玩家可执行！");
                return false;
            }
        }
        if(cmd.arg().toLowerCase().contains("<integer>") || cmd.arg().toLowerCase().contains("<decimal>") || cmd.arg().toLowerCase().contains("<player>")){
            String[] cmdArgs = cmd.arg().split(" ");
            List<String> playerNull = new ArrayList<>();
            List<String> integerNull = new ArrayList<>();
            List<String> decimalNull = new ArrayList<>();
            for(int i = 0; i < cmdArgs.length; i++){
                if(cmdArgs[i].equalsIgnoreCase("<player>")){
                    if(Bukkit.getPlayer(args[i]) == null){
                        playerNull.add(args[i]);
                    }
                }else if(cmdArgs[i].equalsIgnoreCase("<integer>")){
                    try {
                        Integer.parseInt(args[i]);
                    }catch (NumberFormatException ex){
                        integerNull.add(args[i]);
                    }
                }else if(cmdArgs[i].equalsIgnoreCase("<decimal>")){
                    try {
                        Double.parseDouble(args[i]);
                    }catch (NumberFormatException ex){
                        decimalNull.add(args[i]);
                    }
                }
            }
            if(!playerNull.isEmpty()){
                sender.sendMessage("§c玩家 " + playerNull.toString().replace("[", "").replace("]", "") + " 不存在！");
                return false;
            }
            if(!integerNull.isEmpty()){
                sender.sendMessage("§c" + integerNull.toString().replace("[", "").replace("]", "") + " 并非是整数！");
                return false;
            }
            if(!decimalNull.isEmpty()){
                sender.sendMessage("§c" + decimalNull.toString().replace("[", "").replace("]", "") + " 并非是数字！");
                return false;
            }
        }
        if (cmd.coolDown() != 0) {
            HashMap<Method, Long> map = new HashMap<>();
            map.put(method, System.currentTimeMillis() + cmd.coolDown());
            this.coolDownMap.put(sender, map);
        }
        return true;
    }

    private Method getMethodBySubCmd(String[] args, CommandSender sender) {
        Method finalMethod = null;
        for(Method method : this.getClass().getMethods()) {
            Cmd cmd = method.getAnnotation(Cmd.class);
            if (cmd == null) {
                continue;
            }
            boolean b = true;
            String[] cmdArgs = cmd.arg().split(" ");
            if (cmd.arg().isEmpty() && args.length == 0) {
                this.containEmptyMethod = true;
                return method;
            }
            if (cmdArgs.length == args.length || cmd.arg().contains("<value...>") && args.length >= cmdArgs.length) {
                for (int i = 0; i < cmdArgs.length; i++) {
                    if (cmd.aliases().length != 0) {
                        boolean pass = false;
                        for (String s : cmd.aliases()) {
                            String[] str = s.split(" ");
                            try {
                                if (args[i].equalsIgnoreCase(str[i])) {
                                    pass = true;
                                    break;
                                }
                            } catch (ArrayIndexOutOfBoundsException ex) {
                                pass = false;
                            }
                        }
                        if (pass) {
                            continue;
                        }
                    }
                    if (args[i].equalsIgnoreCase(cmdArgs[i])) {
                        continue;
                    } else if (cmdArgs[i].equalsIgnoreCase("<value...>") || cmdArgs[i].equalsIgnoreCase("<value>") || cmdArgs[i].equalsIgnoreCase("<integer>") || cmdArgs[i].equalsIgnoreCase("<decimal>") || cmdArgs[i].equalsIgnoreCase("<player>")) {
                        continue;
                    }
                    b = false;
                }
            } else {
                b = false;
            }
            if (b) {
                if (finalMethod != null) {
                    String[] lastArgs = finalMethod.getAnnotation(Cmd.class).arg().split(" ");
                    for (int i = 0; i < Math.min(lastArgs.length, cmdArgs.length); i++) {
                        boolean last = false;
                        boolean now = false;
                        if (lastArgs[i].equalsIgnoreCase("<value...>") || lastArgs[i].equalsIgnoreCase("<value>") || lastArgs[i].equalsIgnoreCase("<integer>") || lastArgs[i].equalsIgnoreCase("<decimal>") || lastArgs[i].equalsIgnoreCase("<player>")) {
                            last = true;
                        }
                        if (cmdArgs[i].equalsIgnoreCase("<value...>") || cmdArgs[i].equalsIgnoreCase("<value>") || cmdArgs[i].equalsIgnoreCase("<integer>") || cmdArgs[i].equalsIgnoreCase("<decimal>") || cmdArgs[i].equalsIgnoreCase("<player>")) {
                            now = true;
                        }
                        if (last && !now) {
                            finalMethod = method;
                            break;
                        }
                        if (i == Math.min(lastArgs.length, cmdArgs.length) - 1) {
                            if (lastArgs.length < cmdArgs.length) {
                                finalMethod = method;
                            }
                        }
                    }
                }else{
                    finalMethod = method;
                }
            }
        }
        return finalMethod;
    }

    public static void regCommand(CommandManager command, Plugin plugin) {
        command.clazz = command;
        try {
            Method commandMap = plugin.getServer().getClass().getMethod("getCommandMap");
            Object obj = commandMap.invoke(plugin.getServer());
            Method register = obj.getClass().getMethod("register", String.class, String.class, Command.class);
            register.invoke(obj, command.getName(), plugin.getDescription().getName(), command);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public enum CommandOnly{

        ALL,
        PLAYER,
        CONSOLE

    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Cmd{

        long coolDown() default 0;

        String arg() default "";

        String perm() default "";

        String permMessage() default "";

        String[] aliases() default "";

        CommandOnly only() default CommandOnly.ALL;

    }

}
