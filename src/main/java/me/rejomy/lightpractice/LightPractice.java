package me.rejomy.lightpractice;

import org.bukkit.plugin.java.JavaPlugin;

public class LightPractice extends JavaPlugin {
    @Override
    public void onLoad() {
        PracticeAPI.INSTANCE.load(this);
    }

    @Override
    public void onEnable() {
        PracticeAPI.INSTANCE.start(this);
    }

    @Override
    public void onDisable() {
        PracticeAPI.INSTANCE.stop(this);
    }
}
