package com.base.gui;

import java.util.Map;

import org.bukkit.inventory.ItemStack;

import com.base.factories.ItemContructor;

public class GenericGuiItem {

	private ItemStack item;
	private String clickID;
	private String itemID;
	private Actions actions;
	

	public GenericGuiItem(ItemStack item, String itemID, String clickID, Actions actions) {
		this.item = item;
		this.clickID = clickID;
		this.setItemID(itemID);
		this.actions = actions;
	}
	public GenericGuiItem(ItemStack item, String itemID, String clickID) {
		this.item = item;
		this.clickID = clickID;
		this.setItemID(itemID);
		this.actions = new Actions();
	}

	public String getClickID() {
		return clickID;
	}

	public Actions getActions() {
		return actions;
	}

	public void setActions(Actions actions) {
		this.actions = actions;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}
	
	public static GenericGuiItem builder(ItemContructor builder) {
		GenericGuiItem instance = new GenericGuiItem(builder.get(), "", "");
		return instance;
	}
	
	public GenericGuiItem setClickID(String id) {
		this.clickID = id;
		return this;
	}
	
	public GenericGuiItem addActions(Action action) {
		this.actions.addAction(action);
		return this;
	}
	
	public GenericGuiItem addActions(String condition, ItemStack block) {
		Action data = new Action(condition, block);
		this.actions.addAction(data);
		return this;
	}
	
	public GenericGuiItem setItemID(String id) {
		this.itemID = id;
		return this;
	}

	public ItemStack getBaseItem() {
		return this.item;
	}
	
	public ItemStack getItem(Configurable gui) {
		
		for(Action action : this.actions.getActions()) {
			Map<String, Condition> conditions = gui.onConditions(gui.player);
			if(conditions.containsKey(action.getCondition())) {
				if(conditions.get(action.getCondition()).valid(gui)) {
					return action.getBlock();
				}
			}
		}
	
		return item;
	}

	public String getItemID() {
		return itemID;
	}
}
