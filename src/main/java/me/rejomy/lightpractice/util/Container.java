package me.rejomy.lightpractice.util;

import lombok.experimental.UtilityClass;
import me.rejomy.lightpractice.util.inventory.InventoryBuilder;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

@UtilityClass
public class Container {
    public HashMap<File, Class<? extends InventoryBuilder>> inventoryFileContainers = new HashMap<>();
    public HashMap<Class<? extends InventoryBuilder>, YamlConfiguration> inventoryYamlContainers = new HashMap<>();
}
