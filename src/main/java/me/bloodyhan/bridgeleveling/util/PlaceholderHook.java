package me.bloodyhan.bridgeleveling.util;

import me.bloodyhan.bridgeleveling.Main;
import me.bloodyhan.bridgeleveling.api.PlayerData;
import me.bloodyhan.bridgeleveling.config.MainConfig;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class PlaceholderHook extends PlaceholderExpansion {

    private final Main plugin;

    public PlaceholderHook(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "bl";
    }

    @Override
    public String getAuthor() {
        return "Bloody_Han";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer p, String s) {
        PlayerData data = PlayerData.getData(p.getUniqueId());
        if(data != null){
            if (s.equals("pattern-A")) {
                return Method.getChar(data.getRank().getPatternA(), data.getLevel()).replace("{level}", String.valueOf(data.getLevel()));
            }
            if (s.equals("pattern-B")) {
                return Method.getChar(data.getRank().getPatternB(), data.getLevel()).replace("{level}", String.valueOf(data.getLevel()));
            }
            if (s.equals("level-progress-bar")) {
                return Method.getProgressBar(MainConfig.MAX_LEVEL != -1 && data.getLevel() < MainConfig.MAX_LEVEL ? data.getXp() : data.getLevelUpCost(),
                        data.getLevelUpCost(),
                        MainConfig.LENGTH,
                        MainConfig.SYMBOL,
                        MainConfig.UNLOCK_COLOR,
                        MainConfig.LOCK_COLOR);
            }
            if (s.equals("rank-progress-bar")) {
                try {
                    return Method.getProgressBar(Math.min(data.getLevel(), data.getNextRank().getLevelRequirement())
                                    + Math.min(data.getBlockPlace(), data.getNextRank().getBlockPlaceRequirement())
                                    + Math.min(data.getTotalKill(), data.getNextRank().getKillRequirement())
                                    + Math.min(data.getMaxKillStreak(), data.getNextRank().getKillStreakRequirement()),
                            data.getNextRank().getLevelRequirement()
                                    + data.getNextRank().getBlockPlaceRequirement()
                                    + data.getNextRank().getKillRequirement()
                                    + data.getNextRank().getKillStreakRequirement(),
                            MainConfig.LENGTH,
                            MainConfig.SYMBOL,
                            MainConfig.UNLOCK_COLOR,
                            MainConfig.LOCK_COLOR);
                }catch (Exception ex){
                    return Method.getProgressBar(1, 1, MainConfig.LENGTH,
                            MainConfig.SYMBOL,
                            MainConfig.UNLOCK_COLOR,
                            MainConfig.LOCK_COLOR);
                }
            }
            if (s.equals("level-progress-bar-long")) {
                return Method.getProgressBar(MainConfig.MAX_LEVEL != -1 && data.getLevel() < MainConfig.MAX_LEVEL ? data.getXp() : data.getLevelUpCost(),
                        data.getLevelUpCost(),
                        35,
                        MainConfig.SYMBOL,
                        MainConfig.UNLOCK_COLOR,
                        MainConfig.LOCK_COLOR);
            }
            if (s.equals("rank-progress-bar-long")) {
                try {
                    return Method.getProgressBar(Math.min(data.getLevel(), data.getNextRank().getLevelRequirement())
                                    + Math.min(data.getBlockPlace(), data.getNextRank().getBlockPlaceRequirement())
                                    + Math.min(data.getTotalKill(), data.getNextRank().getKillRequirement())
                                    + Math.min(data.getMaxKillStreak(), data.getNextRank().getKillStreakRequirement()),
                            data.getNextRank().getLevelRequirement()
                                    + data.getNextRank().getBlockPlaceRequirement()
                                    + data.getNextRank().getKillRequirement()
                                    + data.getNextRank().getKillStreakRequirement(),
                            35,
                            MainConfig.SYMBOL,
                            MainConfig.UNLOCK_COLOR,
                            MainConfig.LOCK_COLOR);
                }catch (Exception ex){
                    return Method.getProgressBar(1, 1, MainConfig.LENGTH,
                            MainConfig.SYMBOL,
                            MainConfig.UNLOCK_COLOR,
                            MainConfig.LOCK_COLOR);
                }
            }
            if (s.equals("rank")) {
                return String.valueOf(data.getRank().getName());
            }
            if (s.equals("rank-up-level-status")) {
                return data.getLevel()>= data.getNextRank().getLevelRequirement() ? "&a已完成" : "&c(还差 " + Method.toTrisection(data.getNextRank().getLevelRequirement() - data.getLevel()) + " 级)";
            }
            if (s.equals("rank-up-block-place-status")) {
                return data.getTotalBlockPlace()>= data.getNextRank().getBlockPlaceRequirement() ? "&a已完成" : "&c(还差 " + Method.toTrisection(data.getNextRank().getBlockPlaceRequirement() - data.getTotalBlockPlace()) + " 方块)";
            }
            if (s.equals("rank-up-kill-status")) {
                return data.getTotalKill()>= data.getNextRank().getKillRequirement() ? "&a已完成" : "&c(还差 " + Method.toTrisection(data.getNextRank().getKillRequirement() - data.getTotalKill()) + " 击杀)";
            }
            if (s.equals("rank-up-kill-streak-status")) {
                return data.getMaxKillStreak()>= data.getNextRank().getKillStreakRequirement() ? "&a已完成" : "&c(还差 " + Method.toTrisection(data.getNextRank().getKillStreakRequirement() - data.getMaxKillStreak()) + " 连杀)";
            }
            if (s.equals("rank-up-level-requirement")) {
                return String.valueOf(data.getNextRank().getLevelRequirement());
            }
            if (s.equals("rank-up-block-place-requirement")) {
                return String.valueOf(data.getNextRank().getBlockPlaceRequirement());
            }
            if (s.equals("rank-up-kill-requirement")) {
                return String.valueOf(data.getNextRank().getKillRequirement());
            }
            if (s.equals("rank-up-kill-streak-requirement")) {
                return String.valueOf(data.getNextRank().getKillStreakRequirement());
            }
            if (s.equals("level")) {
                return String.valueOf(data.getLevel());
            }
            if (s.equals("xp")) {
                return String.valueOf(MainConfig.MAX_LEVEL != -1 && data.getLevel() < MainConfig.MAX_LEVEL ? data.getXp() : data.getLevelUpCost());
            }
            if (s.equals("level-up-cost")) {
                return String.valueOf(data.getLevelUpCost());
            }
            if (s.equals("block-place")) {
                return String.valueOf(data.getTotalBlockPlace());
            }
            if (s.equals("kill-streak")) {
                return String.valueOf(data.getKillStreak());
            }
            if (s.equals("max-kill-streak")) {
                return String.valueOf(data.getMaxKillStreak());
            }
            if (s.equals("kill")) {
                return String.valueOf(data.getTotalKill());
            }
            if (s.equals("boost")) {
                return String.valueOf(data.getBoost());
            }
            if(s.equals("level-progress-percentage")){
                return Method.getPercent(MainConfig.MAX_LEVEL != -1 && data.getLevel() < MainConfig.MAX_LEVEL ? data.getXp() : data.getLevelUpCost(), data.getLevelUpCost());
            }
            if(s.equals("rank-progress-percentage")){
                try {
                    return Method.getPercent(Math.min(data.getLevel(), data.getNextRank().getLevelRequirement())
                            + Math.min(data.getBlockPlace(), data.getNextRank().getBlockPlaceRequirement())
                            + Math.min(data.getTotalKill(), data.getNextRank().getKillRequirement())
                            + Math.min(data.getMaxKillStreak(), data.getNextRank().getKillStreakRequirement()),
                            data.getNextRank().getLevelRequirement()
                                    + data.getNextRank().getBlockPlaceRequirement()
                                    + data.getNextRank().getKillRequirement()
                                    + data.getNextRank().getKillStreakRequirement());
                }catch (Exception ex){
                    return "100%";
                }
            }
            if (s.equals("next-level")) {
                return String.valueOf(data.getLevel() + 1);
            }
            if (s.equals("next-rank")) {
                if(data.getNextRank() != null){
                    return data.getNextRank().getName();
                }
            }
            if (s.equals("next-level-format")) {
                return Method.toTrisection(data.getLevel() + 1);
            }
            if (s.equals("level-format")) {
                return Method.toTrisection(data.getLevel());
            }
            if (s.equals("xp-format")) {
                return Method.toTrisection(MainConfig.MAX_LEVEL != -1 && data.getLevel() < MainConfig.MAX_LEVEL ? data.getXp() : data.getLevelUpCost());
            }
            if (s.equals("xp-shorten")) {
                return Method.toSuffix(MainConfig.MAX_LEVEL != -1 && data.getLevel() < MainConfig.MAX_LEVEL ? data.getXp() : data.getLevelUpCost());
            }
            if (s.equals("level-up-cost-format")) {
                return Method.toTrisection(data.getLevelUpCost());
            }
            if (s.equals("level-up-cost-shorten")) {
                return Method.toSuffix(data.getLevelUpCost());
            }
            if (s.equals("block-place-format")) {
                return Method.toTrisection(data.getTotalBlockPlace());
            }
            if (s.equals("kill-streak-format")) {
                return Method.toTrisection(data.getKillStreak());
            }
            if (s.equals("max-kill-streak-format")) {
                return Method.toTrisection(data.getMaxKillStreak());
            }
            if (s.equals("kill-format")) {
                return Method.toTrisection(data.getTotalKill());
            }
            if (s.equals("boost-format")) {
                return Method.toTrisection(data.getBoost());
            }
            if (s.equals("rank-up-level-requirement-format")) {
                return Method.toTrisection(data.getNextRank().getLevelRequirement());
            }
            if (s.equals("rank-up-block-place-requirement-format")) {
                return Method.toTrisection(data.getNextRank().getBlockPlaceRequirement());
            }
            if (s.equals("rank-up-kill-requirement-format")) {
                return Method.toTrisection(data.getNextRank().getKillRequirement());
            }
            if (s.equals("rank-up-kill-streak-requirement-format")) {
                return Method.toTrisection(data.getNextRank().getKillStreakRequirement());
            }
        }
        return null;
    }

}
