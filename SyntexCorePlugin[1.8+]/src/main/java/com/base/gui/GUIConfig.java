package com.base.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.base.config.Config;
import com.base.config.ConfigItem;
import com.base.factories.ItemGenerator;
import com.base.utilities.MessageUtils;

public abstract class GUIConfig extends Config{
	public abstract String getName();
	public abstract String getPermission();
	public abstract List<GenericGuiItem> items();
	public abstract boolean canTakeItems();
	
	public List<GenericGuiItem> loadedGenericItems = new ArrayList<>();
	
    public ItemStack getItemStackFromSection(ConfigurationSection attributes) {
    	
		String ItemName = attributes.getString("name");
		Material type = Material.valueOf(attributes.getString("type"));
		int amount = attributes.getInt("amount");

		
		List<String> lore = attributes.getStringList("lore");						
		List<ItemFlag> flags = attributes.getStringList("flags").stream().map(i -> ItemFlag.valueOf(i)).collect(Collectors.toList());						
		
		Map<Enchantment, Integer> enchants = new HashMap<>();						
		
		if(attributes.isConfigurationSection("enchants")) {
			for(String enchant : attributes.getConfigurationSection("enchants").getKeys(false)) {
				enchants.put(Enchantment.getByKey(NamespacedKey.minecraft(enchant)), attributes.getInt("enchants." + enchant));
			}
		}
		
		ItemStack item = ItemGenerator.getInstance(type)
				.setName(ItemName)
				.setAmount(amount)
				.setLore(lore)
				.addFlag(flags)
				.addEnchants(enchants)
				.get();
    	
    	return item;
    }
    
    public List<ConfigItem> getDefaultSerialisedItemStackPath(String path, ItemStack item) {
		
		String name = "";
		Material material = item.getType();
		int amount = item.getAmount();
		
		List<ConfigItem> paths = new ArrayList<>();
		
		List<String> lore = new ArrayList<>();
		Map<Enchantment, Integer> enchantments = new HashMap<>();
		List<String> flags = new ArrayList<String>();
		
		if(item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			lore = MessageUtils.removeColourCodeSymbol(meta.getLore());
			enchantments = meta.getEnchants();
			flags = meta.getItemFlags().stream().map(i -> i.name()).collect(Collectors.toList());
			name = meta.getDisplayName();
		}
    	
		paths.add(new ConfigItem(path + ".name", name.replace("§", "&")));
		paths.add(new ConfigItem(path+ ".type", material.name()));
		paths.add(new ConfigItem(path + ".amount", amount));
		
		paths.add(new ConfigItem(path + ".lore", lore));
		
		paths.add(new ConfigItem(path + ".flags", flags));
		
		for (java.util.Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
			paths.add(new ConfigItem(path + ".enchants." + entry.getKey().getKey().getKey(), entry.getValue()));
		}
		
		return paths;
    }
	
    public void registerItems(List<GenericGuiItem> genericItems) {
    	
		for(GenericGuiItem item : genericItems) {
			
			ItemStack object = item.getBaseItem();
			
			String path = "GUI.items." + item.getItemID();
			
			this.items.addAll(this.getDefaultSerialisedItemStackPath(path + ".item", object));

			this.items.add(new ConfigItem("GUI.items." + item.getItemID() + ".onClick", item.getClickID()));
			
			
			if(!item.getActions().isEmpty()) {
				for(Action action : item.getActions().getActions()) {
					String dir = "GUI.items." + item.getItemID() + ".actions." + action.getCondition() + ".item";
					this.items.addAll(this.getDefaultSerialisedItemStackPath(dir, action.getBlock()));
				}
			}
			
		}
    }
    
    public List<GenericGuiItem> getLoadedItems() {
		List<GenericGuiItem> items = new ArrayList<>();
		
		if(this.getConfiguration().getConfigurationSection("GUI").isConfigurationSection("items")) {

			ConfigurationSection itemSection = this.getConfiguration().getConfigurationSection("GUI.items");
			
			for(String key : itemSection.getKeys(false)) {
				ConfigurationSection attributes = itemSection.getConfigurationSection(key + ".item");
				
				String executable = itemSection.getString(key + ".onClick");
				
				Actions actions = new Actions();
				
				if(itemSection.isConfigurationSection(key + ".actions")) {
					ConfigurationSection actionsSection = itemSection.getConfigurationSection(key + ".actions");
					for(String action : actionsSection.getKeys(false)) {
						ItemStack actionItem = getItemStackFromSection(actionsSection.getConfigurationSection(action + ".item"));
						Action data = new Action(action, actionItem);
						actions.addAction(data);
					}	
				}
				
				ItemStack item = getItemStackFromSection(attributes);
				
				GenericGuiItem genericGuiItem = new GenericGuiItem(item, key, executable, actions);
			
				items.add(genericGuiItem);
				
			}
		}

		return items;
    }
    
	
}
