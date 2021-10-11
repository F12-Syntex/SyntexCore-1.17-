package com.base.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.base.utilities.ListUtils;
import com.base.utilities.MessageUtils;

public abstract class ItemContructor {

	protected ItemStack itemStack;
	
	private ItemContructor data;
	
	private boolean autoColour;
	
	public ItemContructor(ItemStack item) {
		this.itemStack = item;
		this.autoColour = true;
	}
	
	public ItemContructor(ItemStack item, boolean autoColour) {
		this.itemStack = item;
		this.autoColour = autoColour;
	}
	
	public abstract ItemStack get();

	public ItemContructor getData() {
		return data;
	}

	public void setData(ItemContructor data) {
		this.data = data;
	}

	public ItemContructor setName(String name) {
		
		if(name.isEmpty()) {
			ItemMeta meta = this.itemStack.getItemMeta();
			meta.setDisplayName(MessageUtils.translateAlternateColorCodes("&cX", this.autoColour));
			this.itemStack.setItemMeta(meta);
			return this;	
		}
		
		ItemMeta meta = this.itemStack.getItemMeta();
		meta.setDisplayName(MessageUtils.translateAlternateColorCodes(name, this.autoColour));
		this.itemStack.setItemMeta(meta);
		return this;
	}
	
	public ItemContructor setAmount(int amount) {
		this.itemStack.setAmount(amount);
		return this;
	}
	
	public ItemContructor setLore(List<String> lore) {
		ItemMeta meta = this.itemStack.getItemMeta();
		meta.setLore(ListUtils.createLore(lore, this.autoColour));
		this.itemStack.setItemMeta(meta);
		return this;
	}
	
	public ItemContructor setLore(String... lore) {
		ItemMeta meta = this.itemStack.getItemMeta();
		meta.setLore(ListUtils.createLore(this.autoColour, lore));
		this.itemStack.setItemMeta(meta);
		return this;
	}
	
	public ItemContructor addLore(String... lore) {
		ItemMeta meta = this.itemStack.getItemMeta();
		
		List<String> newLore = new ArrayList<String>();
		
		if(meta.hasLore()) {
			newLore = meta.getLore();
		}
		
		newLore.addAll(ListUtils.createLore(this.autoColour, lore));
		
		meta.setLore(ListUtils.createLore(newLore, this.autoColour));
		this.itemStack.setItemMeta(meta);
		return this;
	}
	
	public ItemContructor addFlag(ItemFlag flag) {
		ItemMeta meta = this.itemStack.getItemMeta();
		meta.addItemFlags(flag);
		this.itemStack.setItemMeta(meta);
		return this;
	}
	
	public ItemContructor addFlag(List<ItemFlag> flags) {
		ItemMeta meta = this.itemStack.getItemMeta();
		for(ItemFlag flag : flags) {
			meta.addItemFlags(flag);
		}
		this.itemStack.setItemMeta(meta);
		return this;
	}
	
	public ItemContructor addEnchant(Enchantment enchant, int level) {
		this.itemStack.addUnsafeEnchantment(enchant, level);
		return this;
	}
	
	public ItemContructor addEnchants(Map<Enchantment, Integer> enchantments) {
		for(Enchantment enchant : enchantments.keySet()) {
			this.itemStack.addUnsafeEnchantment(enchant, enchantments.get(enchant));
		}
		return this;
	}
	
	public ItemContructor clone() {
		final ItemContructor contructor = this;
		return contructor;
	}
	
	
	
	
	
	
}
