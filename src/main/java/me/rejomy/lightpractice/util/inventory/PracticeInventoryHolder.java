package me.rejomy.lightpractice.util.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class PracticeInventoryHolder implements InventoryHolder {
    public static PracticeInventoryHolder INSTANCE = new PracticeInventoryHolder();

    @Override
    public Inventory getInventory() {
        return null;
    }
}
