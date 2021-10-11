package com.base.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import com.base.event.custom.LogEvent;
import com.base.main.Base;
import com.base.utilities.MessageUtils;

public class Debug extends Event {

	@EventHandler
	public void onLog(LogEvent e) {
		com.base.commands.Debug debug = (com.base.commands.Debug)Base.getInstance().getCommandManager().getSubCommand("debug");
		for(UUID players : debug.whitelisted) {
			Player user = Bukkit.getPlayer(players);
			if(user == null) continue;
			MessageUtils.sendMessageWithoutPrefix(user, e.getMessage());
		}
	}
	
}
