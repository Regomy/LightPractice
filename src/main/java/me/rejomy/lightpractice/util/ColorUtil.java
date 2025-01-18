package me.rejomy.lightpractice.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

@UtilityClass
public class ColorUtil {
    public String toColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
