package com.base.event.custom;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public abstract class CustomEvent extends org.bukkit.event.Event implements Cancellable{

	protected boolean isCancelled = false;
	private final static HandlerList handlers = new HandlerList();
	
	public CustomEvent() {}
	 
	@Override
	public HandlerList getHandlers() {
	    return handlers;
	}

	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	
}
