package com.base.configure.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import com.base.communication.Input;
import com.base.config.Config;
import com.base.factories.ItemGenerator;
import com.base.main.Base;
import com.base.utilities.ListUtils;
import com.base.utilities.MessageUtils;
import com.base.utilities.NumberUtils;

public class ConfigSection extends ConfigGUI {

	private ConfigurationSection configSection;
	
	public ConfigSection(Player player, ConfigurationSection config, ConfigGUI back, ConfigGUI front, Config configuration) {
		super(player, back, front, configuration);
		// TODO Auto-generated constructor stub
		this.configSection = config;
		
		
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
		
		this.configSection.getKeys(false).forEach(i -> {

			ItemStack section = new ItemStack(Material.valueOf(Base.getInstance().getConfigManager().configure.Sections));
			ItemStack ints = new ItemStack(Material.valueOf(Base.getInstance().getConfigManager().configure.Numbers));
			ItemStack text = new ItemStack(Material.valueOf(Base.getInstance().getConfigManager().configure.Text));
			ItemStack booleanON = new ItemStack(Material.valueOf(Base.getInstance().getConfigManager().configure.BooleanON));
			ItemStack booleanOFF = new ItemStack(Material.valueOf(Base.getInstance().getConfigManager().configure.BooleanOFF));
			ItemStack lists = new ItemStack(Material.valueOf(Base.getInstance().getConfigManager().configure.Lists));
			
			if(this.configSection.isConfigurationSection(i)) {
				
				List<String> lore = new ArrayList<String>();
				
				for(String o : this.configSection.getConfigurationSection(i).getKeys(false)) {
					lore.add("&7 - &6" + o);	
				}
				
				ItemStack item = ItemGenerator.getItem("&7" + i, section, lore);
				
				items.add(new PagedItem(item, () -> {
					player.closeInventory();

					back.setFront(this);
					
					ConfigGUI gui = new ConfigSection(player, this.configSection.getConfigurationSection(i), this, front, config);
					gui.open();	
					
				}));
				
			}else {
				
				Object obj = this.configSection.get(i);
				
				ItemStack item = ItemGenerator.getItem("&7" + i, section, ListUtils.createLore("&5This item cannot be modified!"));
				Runnable execution = () -> {
					
				};
				
				if(obj instanceof String) {
					item = ItemGenerator.getItem("&7" + i, text, ListUtils.createLore("&6Value: &e" + obj.toString(), "&cClick here to change this value."));
					execution = () -> {
						
						player.closeInventory();
						
						back.setFront(this);
						ConfigGUI gui = new ConfigSection(player, configSection, back, front, config);
						
						MessageUtils.sendMessage(player, "&cPlease type in " + "&7" + i + "'s&c new value");
						
						Base.getInstance().getCommunicationHandler().communicate(player.getUniqueId(), new Input() {
							
							@Override
							public void onRecieve(String o) {
								
								config.getConfiguration().set(configSection.getCurrentPath() + "." + i, o);
								config.save();
								
								Base.getInstance().getConfigManager().reload();
								
								Bukkit.getScheduler().callSyncMethod(Base.getInstance(), new Callable<Object>() {
									@Override
									public Object call() throws Exception {
										gui.open();	 
										return null;
									}
								});
								
								MessageUtils.sendMessage(player, " &cYou have set &7" + i + "&c to &7" + o + "&c.");
								
							}
						});
						
					};
				}
				
				if(obj instanceof Boolean) {
					boolean value = this.configSection.getBoolean(i);
					if(value) {
						item = ItemGenerator.getItem("&7" + i, booleanON, ListUtils.createLore("&6Click here to change this value to &efalse."));	
						execution = () -> {
							player.closeInventory();
							config.getConfiguration().set(configSection.getCurrentPath() + "." + i, false);
							config.save();
							Base.getInstance().getConfigManager().reload();
							back.setFront(this);
							ConfigGUI gui = new ConfigSection(player, configSection, this, front, config);
							
							gui.open();	
						};
					}else {
						item = ItemGenerator.getItem("&7" + i, booleanOFF, ListUtils.createLore("&6Click here to change this value to &etrue."));	
						execution = () -> {
							player.closeInventory();
							config.getConfiguration().set(configSection.getCurrentPath() + "." + i, true);
							config.save();
							Base.getInstance().getConfigManager().reload();
							back.setFront(this);
							ConfigGUI gui = new ConfigSection(player, configSection, this, front, config);
							
							gui.open();	
						};
					}
				}
				
				if(obj instanceof Integer) {
					item = ItemGenerator.getItem("&7" + i, ints, ListUtils.createLore("&6Value: &e" + obj.toString(), "&cClick here to change this value."));
					execution = () -> {
						
						player.closeInventory();
						MessageUtils.sendMessage(player, "&cPlease type in " + "&7" + i + "'s&c new value");
						
						back.setFront(this);
						ConfigGUI gui = new ConfigSection(player, configSection, this, front, config);
												
						Base.getInstance().getCommunicationHandler().communicate(player.getUniqueId(), new Input() {
							@Override
							public void onRecieve(String o) {
								
								if(!NumberUtils.isNumber(o)) {
									MessageUtils.sendMessage(player, " &cThat's not a number!");
									Bukkit.getScheduler().callSyncMethod(Base.getInstance(), new Callable<Object>() {
										@Override
										public Object call() throws Exception {
											gui.open();
											return null;
										}
									});		
									return;
								}
								
								config.getConfiguration().set(configSection.getCurrentPath() + "." + i, Integer.parseInt(o));
								config.save();
								
								Base.getInstance().getConfigManager().reload();
								Bukkit.getScheduler().callSyncMethod(Base.getInstance(), new Callable<Object>() {
									@Override
									public Object call() throws Exception {
										gui.open();	 
										return null;
									}
								});
								
								MessageUtils.sendMessage(player, " &cYou have set &7" + i + "&c to &7" + o + "&c.");
								
							}
						});
					};
				}
				
				if(obj instanceof List) {
					
					List<String> lore = ListUtils.createLore("&6Click here to change these values.");
					
					List<?> object = this.configSection.getList(i);					
					
					for(Object o : object) {
						lore.add("&7 - " + "&6" + o.toString());
					}
					
						item = ItemGenerator.getItem("&7" + i, lists, lore);	
						
						execution = () -> {
						
							
						};
				}
				
				
				items.add(new PagedItem(item, execution));
				
			}

		});
		
		// TODO Auto-generated method stub
		return items;
	}

	@Override
	public List<SpecialItem> SpecialContents() {
		// TODO Auto-generated method stub
		return new ArrayList<SpecialItem>();
	}



}
