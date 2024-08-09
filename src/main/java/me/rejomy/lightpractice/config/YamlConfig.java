package me.rejomy.lightpractice.config;

import me.rejomy.lightpractice.PracticeAPI;
import me.rejomy.lightpractice.util.FileUtil;
import me.rejomy.lightpractice.util.Logger;
import me.rejomy.lightpractice.util.interfaces.Loadable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.Objects;

public abstract class YamlConfig implements Loadable {
    protected final YamlConfiguration config;

    public YamlConfig (File config) {
        if (!config.exists()) {
            Logger.info("Load " + config.getName() + " from jar. Full path: " + config.getPath());

            FileUtil.createFileFromJar("files/" + config.getPath());
        }

        this.config = YamlConfiguration.loadConfiguration(config);
    }

    protected int getIntElse(String path, int any) {
        return (int) Objects.requireNonNullElse(value(path), any);
    }

    protected double getDoubleElse(String path, double any) {
        return (double) Objects.requireNonNullElse(value(path), any);
    }

    protected float getFloatElse(String path, float any) {
        return (float) Objects.requireNonNullElse(value(path), any);
    }

    protected boolean getBooleanElse(String path, boolean any) {
        return (boolean) Objects.requireNonNullElse(value(path), any);
    }

    protected String getStringElse(String path, String any) {
        return (String) Objects.requireNonNullElse(value(path), any);
    }

    protected List<String> getStringListElse(String path, List<String> any) {
        return (List<String>) Objects.requireNonNullElse(value(path), any);
    }

    protected boolean getBoolean(String path) {
        return (boolean) value(path);
    }

    private Object value(String path) {
        return config.get(path);
    }
}
