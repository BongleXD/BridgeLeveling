package me.bloodyhan.bridgeleveling.util;

import net.md_5.bungee.api.ChatColor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author Bloody_Han
 */
public class Method {

    public static String getChar(String s, int i){
        int j = 0;
        for(char c : String.valueOf(i).toCharArray()){
            s = s.replace(String.format("{level_%s}", j + 1), String.valueOf(c));
            j++;
        }
        return s;
    }

    public static String getPercent(double x, double y){
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("0.0");
        return y == 0 ? "100%" : df.format(x / y * 100).replace(".0", "") + "%";
    }

    public static String transColor(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String toTrisection(double d) {
        if (d == 0) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("#,###.00");
        String result = df.format(d).replace(".00", "");
        return result.endsWith(".0") ? result.replace(".0", "") : result.contains(".") && result.endsWith("0") ? result.substring(0, result.length() - 1) : result;
    }

    public static String toSuffix(int count){
        if (count < 1000) {
            return String.valueOf(count);
        }
        int exp = (int) (Math.log(count) / Math.log(1000));
        DecimalFormat df = new DecimalFormat("0.#");
        df.setRoundingMode(RoundingMode.DOWN);
        String value = df.format(count / Math.pow(1000, exp));
        return String.format("%s%c", value, "kMBTPE".charAt(exp - 1));
    }

    public static String getProgressBar(int xp, int xpToLevelUp, int length, String symbol, String unlock, String lock) {
        StringBuilder sb = new StringBuilder();
        if (xpToLevelUp == 0) {
            xp = 1;
            xpToLevelUp = 1;
        }
        int max = xp * length / xpToLevelUp;
        for (int i = 0; i < length; i++) {
            if (i < max) {
                sb.append(unlock).append(symbol);
            } else {
                sb.append(lock).append(symbol);
            }
        }
        return sb.toString();
    }

}
