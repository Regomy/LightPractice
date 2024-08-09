package me.rejomy.lightpractice;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public enum PracticeAPI {
    INSTANCE;

    JavaPlugin plugin;

    public void load(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void start(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void stop(JavaPlugin plugin) {
        this.plugin = plugin;
    }
}
