package com.base.utilities;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class ItemBuilder {
	
    public static ItemStack stringBlobToItem(String stringBlob) {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.loadFromString(stringBlob);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return config.getItemStack("i", null);
    }
    
    
    public static String itemToStringBlob(ItemStack itemStack) {
            YamlConfiguration config = new YamlConfiguration();
            config.set("i", itemStack);
            return config.saveToString();
        }

}
