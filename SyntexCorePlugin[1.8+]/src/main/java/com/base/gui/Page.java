package com.base.gui;

import java.util.HashMap;
import java.util.Map;

public class Page {
	
	private Map<Integer, GenericGuiItem> items;
	private int size;
	
	
	public Page(int inventorySize) {
		this.items = new HashMap<>();
		this.size = inventorySize;
	}

	public void setItem(int slot, GenericGuiItem item) {
		this.items.put(slot, item);
	}

	public void fillEmpty(GenericGuiItem item) {
		for(int i = 0; i < size-18; i++) {
			if(!items.containsKey(i)) {
				this.setItem(i, item);
			}
		}
	}
	
	public GenericGuiItem getItem(int index) {
		return this.items.get(index);
	}
	
	public Map<Integer, GenericGuiItem> getItems() {
		return this.items;
	}

	
}
