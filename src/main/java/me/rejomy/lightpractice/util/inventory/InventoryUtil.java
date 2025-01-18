package me.rejomy.lightpractice.util.inventory;

import lombok.experimental.UtilityClass;
import me.rejomy.lightpractice.util.Container;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@UtilityClass
public class InventoryUtil {
    public String getInventoryName(Inventory inventory) {
        try {
            // Попытка получить метод для Spigot 1.16.5
            HumanEntity viewer = inventory.getViewers().isEmpty() ? null : inventory.getViewers().get(0);
            if (viewer != null) {
                Method getOpenInventoryMethod = viewer.getClass().getMethod("getOpenInventory");
                getOpenInventoryMethod.setAccessible(true);
                InventoryView view = (InventoryView) getOpenInventoryMethod.invoke(viewer);

                if (view != null) {
                    Method getTitleMethod = view.getClass().getMethod("getTitle");
                    getTitleMethod.setAccessible(true);
                    return (String) getTitleMethod.invoke(view);
                }
            }
        } catch (NullPointerException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            // Если не найдено, попробуйте получить метод для Spigot 1.8
            return inventory.getName();
        }

        return null;
    }

    public InventoryBuilder getInventoryByFileName(String name) {
        InventoryBuilder someInventory = Container.inventoryFileContainers.entrySet().stream()
                .filter(entry -> {
                    String fileName = entry.getKey().getName().replace(".yml", "");
                    return fileName.equals(name);
                }).map(entry -> {
                    try {
                        return entry.getValue().getConstructor(YamlConfiguration.class)
                                .newInstance(Container.inventoryYamlContainers.get(entry.getValue()));
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to create instance of InventoryBuilder", e);
                    }
                }).findAny().orElse(null);

        return someInventory;
    }
}
