package com.base.configure.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import com.base.config.Config;
import com.base.config.Folder;
import com.base.factories.ItemGenerator;
import com.base.main.Base;

public class ConfigsFolderView extends ConfigGUI {

	public ConfigsFolderView(Player player, ConfigGUI back, ConfigGUI front) {
		super(player, back, front, null);
		// TODO Auto-generated constructor stub
		
		if(back != null) {
			back.setFront(this);
		}
		
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "Configs";
	}

	@Override
	public String permission() {
		// TODO Auto-generated method stub
		return Base.getInstance().getConfigManager().permissions.configure;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 54;
	}

	@Override
	public Sound sound() {
		// TODO Auto-generated method stub
		return Sound.BLOCK_LEVER_CLICK;
	}

	@Override
	public float soundLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canTakeItems() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClickInventory(InventoryClickEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOpenInventory(InventoryOpenEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCloseInventory(InventoryCloseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PagedItem> Contents() {
		
		List<PagedItem> items = new ArrayList<PagedItem>();
		
		for(Folder folder : Folder.values()) {
		
			List<String> lore = new ArrayList<String>();
			
			for(Config i : Base.getInstance().getConfigManager().config) {
			
				if(i.getConfigMeta().folder() != folder) continue;
				
				lore.add("&7 - &6" + i.getConfigMeta().name());
				
			}
			
			ItemStack object = new ItemStack(Material.valueOf(Base.getInstance().getConfigManager().configure.Sections));
			
			ItemStack item = ItemGenerator.getItem("&7" + folder.name(), object, lore);
			
			items.add(new PagedItem(item, () -> {
				player.closeInventory();
				ConfigGUI gui = new ConfigsView(player, this, null, folder);
				gui.open();
			}));
			
		}
		
		// TODO Auto-generated method stub
		return items;
	}

	@Override
	public List<SpecialItem> SpecialContents() {
		// TODO Auto-generated method stub
		return new ArrayList<SpecialItem>();
	}


}
