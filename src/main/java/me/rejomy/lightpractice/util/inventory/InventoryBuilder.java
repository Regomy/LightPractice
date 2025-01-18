package me.rejomy.lightpractice.util.inventory;

import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import me.rejomy.lightpractice.util.ColorUtil;
import me.rejomy.lightpractice.util.Logger;
import me.rejomy.lightpractice.util.NumberUtil;
import me.rejomy.lightpractice.util.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class InventoryBuilder {
    @Getter
    final Inventory inventory;
    @Getter
    HashMap<Integer, InventoryItem> items = new HashMap<>();

    public InventoryBuilder (String name, int size) {
        this(Bukkit.createInventory(PracticeInventoryHolder.INSTANCE, size, ColorUtil.toColor(name)));
    }

    public InventoryBuilder (String name, InventoryType type) {
        this(Bukkit.createInventory(PracticeInventoryHolder.INSTANCE, type, ColorUtil.toColor(name)));
    }

    private InventoryBuilder (Inventory inventory) {
        this.inventory = inventory;
    }

    public abstract void fill();

    public abstract void handle(final Player player, final int slot, final InventoryClickEvent event);

    public final void onClick (InventoryClickEvent event) {
        int slot = event.getSlot();

        // We should`t check from inventory clicks, its unnecessary :)
        if (slot < 0) return;

        Inventory inventory = event.getClickedInventory();

        if (isThisInventory(inventory)) {
            // We cancel this action, because a player can move his item to slot and close inventory.
            // Player loose item.
            // Or player can steal items from inventory.
            event.setCancelled(true);

            // Check if items contains slot, if not, player click to empty slot...
            if (!items.containsKey(slot)) {
                return;
            }

            Player player = (Player) event.getWhoClicked();

            // Если в строке commands что-то указано, выполняем это здесь, а потом передаем на обработку.
            for (String command : getItems().get(slot).getCommands()) {
                String[] parts = command.split(" ");

                // Check here if it is plugin internal command.
                char[] chars = parts[0].toCharArray();

                if (chars.length > 2 && chars[0] == '[' && chars[chars.length - 1] == ']') {
                    String action = parts[0].replaceAll("[^A-Za-z]", "");

                    switch (action.toLowerCase()) {
                        case "close" -> player.closeInventory();
                        case "open" -> {
                            if (parts.length == 1) {
                                Logger.warn("Error when executing command " + command + " this command required two args.");
                                Logger.warn("For example: [open] settings");
                                continue;
                            }

                            InventoryUtil.getInventoryByFileName(parts[1]).open(player);
                        }
                        case "sound" -> {
                            if (parts.length == 1) {
                                Logger.warn("Error when executing command " + command + " this command required two args.");
                                Logger.warn("For example: [SOUND] sound_name");
                                continue;
                            }

                            float volume = 1, pitch = 1;

                            if (parts.length > 2) {
                                volume = NumberUtil.parseFloat(parts[2]);

                                if (parts.length > 4)
                                    pitch = NumberUtil.parseFloat(parts[3]);
                            }

                            SoundUtil.play(parts[1], player, volume, pitch);
                        }
                    }

                } else Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("$player", player.getName()));
            }
            // ***

            handle(player, event.getSlot(), event);
        }
    }

    public void open(Player player) {
        // Если до этого инвентарь не был никем открыт, то есть, его либо открывают в первый раз,
        // либо давно не открывали, мы его обновляем и регистрируем слушатели.
        if (inventory.getViewers().isEmpty()) {
            fill();
        }

        player.openInventory(inventory);
    }

    protected void loadItemsFromConfig(FileConfiguration config) {
        for (String itemName : config.getConfigurationSection("items").getKeys(false)) {
            InventoryItem item = new InventoryItem()
                    .setName(config.getString("items." + itemName + ".name"))
                    .setLore(config.getStringList("items." + itemName + ".lore"))
                    .setCommands(config.getStringList("items." + itemName + ".commands"))
                    .setMaterial(XMaterial.valueOf(config.getString("items." + itemName + ".material")));

            Object slot = config.get("items." + itemName + ".slot");

            if (slot == null) {
                continue;
            }

            items.put((int) slot, item);
            setItem(item, (int) slot);
        }
    }

    protected void loadItemFromConfig(FileConfiguration config, String path, BiConsumer<Player, InventoryClickEvent> task, Object... replacers) {
        InventoryItem item = new InventoryItem()
                .setMaterial(XMaterial.valueOf(config.getString(path + ".material")))
                .setName(config.getString(path + ".name"), replacers)
                .setLore(config.getStringList(path + ".lore"), replacers)
                .setBiConsumer(task);

        setItem(item, config.getInt(path + ".slot"));
    }

    protected void loadItemFromConfig(FileConfiguration config, String path, Consumer<Player> task, Object... replacers) {
        InventoryItem item = new InventoryItem()
                .setMaterial(XMaterial.valueOf(config.getString(path + ".material")))
                .setName(config.getString(path + ".name"), replacers)
                .setLore(config.getStringList(path + ".lore"), replacers)
                .setConsumer(task);

        setItem(item, config.getInt(path + ".slot"));
    }

    protected void loadItemFromConfig(FileConfiguration config, String path, Object... replacers) {
        InventoryItem item = new InventoryItem()
                .setMaterial(XMaterial.valueOf(config.getString(path + ".material")))
                .setName(config.getString(path + ".name"), replacers)
                .setLore(config.getStringList(path + ".lore"), replacers);

        setItem(item, config.getInt(path + ".slot"));
    }

    protected void setItem(InventoryItem item, int slot) {
        items.put(slot, item);
        // Sometimes we need to create null item for handle it on handle() method.
        // same with setName
        if (item == null) return;
        inventory.setItem(slot, InventoryItem.build(item));
    }

    protected static InventoryItem getItemFromConfig(FileConfiguration config, String path, Object... replacers) {
        return new InventoryItem()
                .setMaterial(XMaterial.valueOf(config.getString(path + ".material")))
                .setName(config.getString(path + ".name"), replacers)
                .setLore(config.getStringList(path + ".lore"), replacers);
    }

    protected boolean isThisInventory(Inventory inventory) {
        return inventory.getHolder() == PracticeInventoryHolder.INSTANCE &&
                InventoryUtil.getInventoryName(inventory).equals(InventoryUtil.getInventoryName(getInventory()));
    }
}
