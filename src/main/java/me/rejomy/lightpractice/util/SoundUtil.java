package me.rejomy.lightpractice.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@UtilityClass
public class SoundUtil {

    public void play(String soundName, Player player) {
        play(soundName, player, 1, 1);
    }

    public void play(String soundName, Player player, float volume, float pitch) {
        try {
            Sound sound = Sound.valueOf(soundName);
            play(sound, player, volume, pitch);
        } catch (IllegalArgumentException exception) {
            Logger.warn("");
            Logger.warn("Sound with name " + soundName + " not found!");
            Logger.warn("Your version: " + Bukkit.getServer().getVersion());
            Logger.warn("You can see all sounds here: https://helpch.at/docs/" +
                    Bukkit.getServer().getBukkitVersion() + "/org/bukkit/Sound.html");
            Logger.warn("");
        }
    }

    public void play(Sound sound, Player player) {
        player.playSound(player.getLocation(), sound, 1, 1);
    }

    public void play(Sound sound, Player player, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }
}
