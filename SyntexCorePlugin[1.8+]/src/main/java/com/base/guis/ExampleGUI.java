package com.base.guis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import com.base.factories.ActionsBuilder;
import com.base.factories.ExecutionBuilder;
import com.base.factories.ItemGenerator;
import com.base.factories.PlaceholderFactory;
import com.base.gui.Condition;
import com.base.gui.GenericGuiItem;
import com.base.gui.InventoryMeta;
import com.base.gui.PagedGUI;

@InventoryMeta(name = "&6Example", rows = 6, config = "example.yml", version = "1.0.1")
public class ExampleGUI extends PagedGUI{

	@Override
	public List<GenericGuiItem> getItems() {
		
		List<GenericGuiItem> item = new ArrayList<>();
		
		item.add(GenericGuiItem.builder(ItemGenerator.getInstance(Material.BLACK_WOOL)
				.setName("&6" + "%player%")
				.addEnchant(Enchantment.DURABILITY, 10)
				.addEnchant(Enchantment.DAMAGE_ALL, 10)
				.addFlag(ItemFlag.HIDE_ATTRIBUTES)
				.addFlag(ItemFlag.HIDE_PLACED_ON)
				.addLore("&cHi")).setItemID("door").setClickID("door:command"));
		
		item.add(GenericGuiItem.builder(ItemGenerator.getInstance(Material.YELLOW_DYE)
				.setName("&6" + "%player%")
				.addEnchant(Enchantment.DURABILITY, 10)
				.addEnchant(Enchantment.DAMAGE_ALL, 10)
				.addFlag(ItemFlag.HIDE_ATTRIBUTES)
				.addFlag(ItemFlag.HIDE_PLACED_ON)
				.addLore("&cHi")).setItemID("door1").setClickID("door1:command"));
		
		return item;
	}

	@Override
	public void loadInventory() {
		this.fillEmpty(1, "door");
		this.fillEmpty(2, "empty_white");
		this.fillEmpty(3, "door1");
	}

	@Override
	public Map<String, Runnable> onSubChildExecution(Player payer) {
		return ExecutionBuilder
				.createExecution("door:command", () -> {
					player.getInventory().addItem(ItemGenerator.getInstance(Material.BLACK_WOOL)
							.setName("&6" + player.getName())
							.addEnchant(Enchantment.DURABILITY, 10)
							.addEnchant(Enchantment.DAMAGE_ALL, 10)
							.addFlag(ItemFlag.HIDE_ATTRIBUTES)
							.addFlag(ItemFlag.HIDE_PLACED_ON)
							.addLore("&cHi")
							.get());
				})
				.addExecution("door1:command", () -> {
					player.getInventory().addItem(ItemGenerator.getInstance(Material.YELLOW_DYE)
							.setName("&6" + player.getName())
							.addEnchant(Enchantment.DURABILITY, 10)
							.addEnchant(Enchantment.DAMAGE_ALL, 10)
							.addFlag(ItemFlag.HIDE_ATTRIBUTES)
							.addFlag(ItemFlag.HIDE_PLACED_ON)
							.addLore("&cHi")
							.get());
				})
				.get();
	}

	@Override
	public PlaceholderFactory subGuiPlaceholders(Player player) {
		return PlaceholderFactory.empty();
	}

	@Override
	public Map<String, Condition> onSubChildConditions(Player payer) {
		return ActionsBuilder.empty();
	}

}
