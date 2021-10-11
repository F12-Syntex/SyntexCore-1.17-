package com.base.gui;

import org.bukkit.event.inventory.InventoryClickEvent;

@FunctionalInterface
public interface Clickable {
	public abstract void onClick(InventoryClickEvent e);
}
