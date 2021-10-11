package com.base.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

import com.base.factories.ActionsBuilder;
import com.base.factories.ExecutionBuilder;
import com.base.factories.ItemGenerator;
import com.base.factories.PlaceholderFactory;
import com.base.main.Base;
import com.base.utilities.ItemBuilder;
import com.base.utilities.MessageUtils;

public abstract class PagedGUI extends com.base.gui.Configurable implements Listener{

	//protected Map<Slot, GenericGuiItem> slotMapping;
	//protected SlotMappingHandler slotMapping;
	protected Map<Integer, Page> pages;
	
	
	public GUIConfig config;
	public int page;
	
	public PagedGUI() {
		this.InventoryMeta = this.getClass().getAnnotation(com.base.gui.InventoryMeta.class);
		//this.slotMapping = new HashMap<>();
		//this.slotMapping = new SlotMappingHandler();
		this.pages = new HashMap<>();
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(this.clickAuthenticated(e)) {
			
			GenericGuiItem guiItem = this.pages.get(this.page).getItem(e.getSlot());
			String id = guiItem.getClickID();

			BukkitRunnable clickable = new BukkitRunnable() {
				@Override
				public void run() {
					PagedGUI.this.onExecution(player).get(id).run();
				}
			};
			
			clickable.run();
			
		}
		
		e.setCancelled(!this.config.canTakeItems());
	}
	
	
	public void open(Player player, int page) {
		
		this.page = page;
		
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
		this.loadInventory();
	}
	
	public abstract void loadInventory();
	
	public Map<String, Runnable> onExecution(Player payer){
		return ExecutionBuilder
				.createExecution("empty", () -> {})
				.addExecution("page:next", () -> {
					if(this.getLastPage() < (page+1)) return;
					this.page++;
					this.refresh();
				})
				.addExecution("page:prev", () -> {
					if(this.getFirstPage() > (page-1)) return;
					this.page--;
					this.refresh();
				})
				.include(this.onSubChildExecution(payer))
				.get();
	}
	
	@Override
	public Map<String, Condition> onConditions(Player player) {
		return ActionsBuilder.createActions("first_page", new Condition() {	
			@Override
			public boolean valid(Configurable instance) {
				return page <= PagedGUI.this.getFirstPage();
			}
		}).addActions("last_page", new Condition() {	
			@Override
			public boolean valid(Configurable instance) {
				return page >= PagedGUI.this.getLastPage();
			}
		}).include(this.onSubChildConditions(player)).get();
	}
	
	public abstract Map<String, Runnable> onSubChildExecution(Player payer);
	public abstract PlaceholderFactory subGuiPlaceholders(Player player);
	public abstract Map<String, Condition> onSubChildConditions(Player players);
	
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
	
	public void setItem(String id, Slot slot) {	
		GenericGuiItem item = this.getGenericItem(id);
		
		if(!this.pages.containsKey(slot.getPage())) {
			this.pages.put(slot.getPage(), new Page(this.InventoryMeta.rows()*9));
		}
		
		this.pages.get(slot.getPage()).setItem(slot.getSlot(), item);
		this.refresh();
	}
	
	public void fillSlots(int page, int min, int max, String id) {
		
		if(!this.pages.containsKey(page)) {
			this.pages.put(page, new Page(this.InventoryMeta.rows()*9));
		}
		
		GenericGuiItem item = this.getGenericItem(id);
		for(int i = min; i < max; i++) {
			this.pages.get(page).setItem(i, item);
		}
		this.refresh();
	}
	
	public void fillEmpty(int page, String id) {
		
		if(!this.pages.containsKey(page)) {
			this.pages.put(page, new Page(this.InventoryMeta.rows()*9));
		}
		
		GenericGuiItem item = this.getGenericItem(id);
		this.pages.get(page).fillEmpty(item);
		this.refresh();
	}
	
	public void refresh() {
		
		Map<Integer, GenericGuiItem> items = this.pages.get(page).getItems();
		
		for(int item : items.keySet()) {
			ItemStack itemStack = this.getItem(items.get(item).getItemID());
			this.inv.setItem(item, itemStack);
		}

		//add the defualt page bar
		int secondLastRow = (this.InventoryMeta.rows()*9) - 18;
		int LastRow = (this.InventoryMeta.rows()*9) - 9;
		
		if(secondLastRow < 0) return;
		
		for(int i = secondLastRow; i < LastRow; i++) {
			GenericGuiItem genericItem = this.getGenericItem("empty_white");
			this.pages.get(page).setItem(new Slot(this.page, i).getSlot(), genericItem);
			this.inv.setItem(i, genericItem.getItem(this));
		}
		for(int i = LastRow; i < this.InventoryMeta.rows()*9; i++) {
			GenericGuiItem genericItem = this.getGenericItem("empty_black");
			this.pages.get(page).setItem(new Slot(this.page, i).getSlot(), genericItem);
			this.inv.setItem(i, genericItem.getItem(this));
		}

		GenericGuiItem next = this.getGenericItem("next");
		Slot nextSlot = new Slot(this.page, (this.InventoryMeta.rows()*9)-1);
		this.pages.get(page).setItem(nextSlot.getSlot(), next);
		this.inv.setItem(nextSlot.getSlot(), next.getItem(this));
		
		GenericGuiItem back = this.getGenericItem("back");
		Slot backSlot = new Slot(this.page, (this.InventoryMeta.rows()*9)-9);
		this.pages.get(page).setItem(backSlot.getSlot(), back);
		this.inv.setItem(backSlot.getSlot(), back.getItem(this));
				
	}
	
	public List<GenericGuiItem> getDefaultBarItems() {
		
		List<GenericGuiItem> items = new ArrayList<>();
		
		ItemStack Wpane = ItemGenerator.getInstance(Material.WHITE_STAINED_GLASS_PANE).setName("").get();
		ItemStack Bpane = ItemGenerator.getInstance(Material.BLACK_STAINED_GLASS_PANE).setName("").get();
		
		ItemStack next = ItemGenerator.getInstance(Material.GREEN_STAINED_GLASS_PANE).setName("&anext").get();
		ItemStack back = ItemGenerator.getInstance(Material.GREEN_STAINED_GLASS_PANE).setName("&aback").get();
		
		ItemStack last = ItemGenerator.getInstance(Material.RED_STAINED_GLASS_PANE).setName("&cnext").get();
		ItemStack first = ItemGenerator.getInstance(Material.RED_STAINED_GLASS_PANE).setName("&cback").get();
		
		
		items.add(new GenericGuiItem(Wpane, "empty_white", "empty"));
		items.add(new GenericGuiItem(Bpane, "empty_black", "empty"));
		items.add(new GenericGuiItem(next, "next", "page:next").addActions("last_page", last));
		items.add(new GenericGuiItem(back, "back", "page:prev").addActions("first_page", first));
		
		return items;	
	}
	
	public int getFirstPage() {
		return Collections.min(this.pages.keySet());
	}
	public int getLastPage() {
		return Collections.max(this.pages.keySet());
	}
	
	
}
