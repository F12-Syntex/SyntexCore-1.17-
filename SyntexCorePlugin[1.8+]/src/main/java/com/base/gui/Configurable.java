package com.base.gui;

import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import com.base.automation.AutoFill;
import com.base.config.ConfigManager;
import com.base.factories.PlaceholderFactory;

public abstract class Configurable implements Listener{
	
	protected Player player;
	protected Inventory inv;
	protected ConfigManager configManager;
	protected List<GenericGuiItem> items;
	protected InventoryMeta InventoryMeta;
	
	@EventHandler()
	public void onOpen(InventoryOpenEvent e) {
		if(e.getPlayer().getUniqueId().compareTo(this.player.getUniqueId()) != 0) return;
	}
	
	@EventHandler()
	public void onClose(InventoryCloseEvent e) {
		if(e.getPlayer().getUniqueId().compareTo(this.player.getUniqueId()) != 0) return;
		HandlerList.unregisterAll(this);
	}
	
	@AutoFill
	public String name; //data will be injected from the config
	
	@AutoFill
	public String permission; //data will be injected from the config
	
	@AutoFill
	public int size; //data will be injected from the config
	
	public abstract List<GenericGuiItem> getItems();
	public abstract Map<String, Condition> onConditions(Player payer);
	
	public InventoryMeta getMeta() {
		 return this.getClass().getAnnotation(InventoryMeta.class);	
	}
	
	public PlaceholderFactory onSuperExecution(Player player){
		return PlaceholderFactory.createPlaceholder("%player%", player.getName());
	}
	

	protected boolean clickAuthenticated(InventoryClickEvent e) {
		if(e.getWhoClicked().getUniqueId().compareTo(this.player.getUniqueId()) != 0) return false;
		if(e.getClickedInventory() == null) return false;
		if(e.getCurrentItem() == null) return false;
		if(e.getClick() == ClickType.DOUBLE_CLICK) return false;
		return true;
	}
	
	public GenericGuiItem getGenericItem(String id) {
		return this.items.stream().filter(i -> i.getItemID().equalsIgnoreCase(id)).findFirst().get();
	}
	
	
}
