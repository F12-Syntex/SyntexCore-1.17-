package com.base.commands.arguments;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class ArgumentProperty {
	
	private ArgumentBuilder argumentBuilder;
	private final String text;
	
	public ArgumentProperty(ArgumentBuilder argumentBuilder) {
		this.argumentBuilder = argumentBuilder;
		this.text = this.argumentBuilder.getArgs()[this.argumentBuilder.getIndex()];
	}
	
	public BooleanValue getBoolean() {
		String text = this.argumentBuilder.getArgs()[this.argumentBuilder.getIndex()];
		
		if(text.equalsIgnoreCase("yes")) return new BooleanValue(true, false);
		if(text.equalsIgnoreCase("no")) return new BooleanValue(false, false);
		if(text.equalsIgnoreCase("true")) return new BooleanValue(true, false);
		if(text.equalsIgnoreCase("false")) return new BooleanValue(false, false);
	
	
		return new BooleanValue(false, false);
	}
	
	public String getString() {
		return text;
	}
	
	public boolean isOnlinePlayer() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.getName().equals(text)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isOfflinePlayer() {
		for(OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			if(player.isOnline()) continue;
			if(player.getName().equals(text)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isPlayer() {
		for(OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			if(player.getName().equals(text)) {
				return true;
			}
		}
		return false;
	}
	
}
