package dev.azarplay_polar.retroalmas.utils;

import org.bukkit.ChatColor;

import java.util.List;

public class TranslateUtils {
    public String translateColors(String string) {
        if (!(string.contains("&"))) {
            return ChatColor.GRAY + string;
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }
    public List<String> translateAllColors(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, translateColors(list.get(i)));
        }
        return list;
    }
}
