package com.base.gui;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.base.factories.PlaceholderFactory;
import com.base.main.Base;
import com.base.utilities.ItemBuilder;
import com.base.utilities.MessageUtils;

public abstract class GUI extends com.base.gui.Configurable implements Listener{

	protected Map<Integer, GenericGuiItem> slotMapping;
	
	protected GUIConfig config;
	
	public GUI() {
		this.InventoryMeta = this.getClass().getAnnotation(com.base.gui.InventoryMeta.class);
		this.slotMapping = new HashMap<>();
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(this.clickAuthenticated(e)) {
			
			if(slotMapping.containsKey(e.getSlot())) {
				GenericGuiItem guiItem = this.slotMapping.get(e.getSlot());
				String id = guiItem.getClickID();

				BukkitRunnable clickable = new BukkitRunnable() {
					@Override
					public void run() {
						GUI.this.onExecution(player).get(id).run();
					}
				};
				clickable.run();
			}
			
		}
		e.setCancelled(!this.config.canTakeItems());
	}
	
	
	public void open(Player player) {
		
		this.configManager = Base.getInstance().getConfigManager();
		this.config = this.configManager.getGUIConfig(this.InventoryMeta.config());
		
		if(!player.hasPermission(this.config.getPermission())) {
			MessageUtils.sendMessageWithoutPrefix(player, Base.getInstance().getConfigManager().messages.invalid_permission);
			return;
		}
		
		this.items = this.getItems();
		Base.getInstance().getServer().getPluginManager().registerEvents(this, Base.getInstance());

		this.inv = Bukkit.createInventory(this.player, (this.InventoryMeta.rows() * 9), MessageUtils.translateAlternateColorCodes(this.config.getName()));
		this.player = player;
		
		this.items = this.config.loadedGenericItems;
		
		
		
		this.populate();
		
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0f, 0f);
		player.openInventory(inv);
	}
	
	public void populate() {
		this.slotMapping.clear();
		this.loadInventory();
	}
	
	public abstract void loadInventory();
	public abstract Map<String, Runnable> onExecution(Player payer);
	
	public abstract PlaceholderFactory subGuiPlaceholders(Player player);
	
	public PlaceholderFactory placeholders(Player payer){
		
		
		//add super placeholders
		PlaceholderFactory factory = PlaceholderFactory.createPlaceholder(this.onSuperExecution(payer).get());
		
		//add GUI placeholders
		
		
		//add child placeholders
		factory.addPlaceholder(this.subGuiPlaceholders(payer).get());
		
		
		return factory;
	}

	public ItemStack getItem(String id) {
		ItemStack item = this.items.stream().filter(i -> i.getItemID().equalsIgnoreCase(id)).findFirst().get().getItem(this);
		
		String stringItem = this.placeholders(this.player).applyPlaceHolders(ItemBuilder.itemToStringBlob(item));
		
		item = ItemBuilder.stringBlobToItem(stringItem);
		
		return item;
	}
	
	public void setSlot(int slot, String id) {
		this.inv.setItem(slot, this.getItem(id));
		this.slotMapping.put(slot, this.getGenericItem(id));
	}
	
	public void fillSlots(int min, int max, String id) {
		for(int i = min; i < max; i++) {
			this.inv.setItem(i, this.getItem(id));
			this.slotMapping.put(i, this.getGenericItem(id));
		}
	}
	
	public void fillEmpty(String id) {
		for(int i = 0; i < this.InventoryMeta.rows()*9; i++) {
			if(this.inv.getItem(i) == null || this.inv.getItem(i).getType() == Material.AIR) {
				this.inv.setItem(i, this.getItem(id));	
				this.slotMapping.put(i, this.getGenericItem(id));
			}
		}
	}
	
	
}
