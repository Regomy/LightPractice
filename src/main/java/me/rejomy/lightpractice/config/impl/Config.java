package me.rejomy.lightpractice.config.impl;

import lombok.Getter;

import me.rejomy.lightpractice.PracticeAPI;
import me.rejomy.lightpractice.config.YamlConfig;

import java.io.File;

@Getter
public class Config extends YamlConfig {
    boolean hideExactlySameGame;
    boolean multiLanguage;

    public Config() {
        super(new File(PracticeAPI.INSTANCE.getPlugin().getDataFolder(), "config.yml"));
    }

    @Override
    public void load() {
        // Settings part
        hideExactlySameGame = getBoolean("settings.hide-exactly-same-game");
        multiLanguage = getBoolean("multi-language");


    }
}
