package me.rejomy.lightpractice.config;

import lombok.Getter;
import me.rejomy.lightpractice.PracticeAPI;
import me.rejomy.lightpractice.config.impl.Config;
import me.rejomy.lightpractice.util.interfaces.Loadable;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class ConfigManager implements Loadable {
    Config config;

    @Override
    public void load() {
        JavaPlugin plugin = PracticeAPI.INSTANCE.getPlugin();

        // Create plugin folder.
        plugin.getDataFolder().mkdirs();

        config = new Config();
    }
}
