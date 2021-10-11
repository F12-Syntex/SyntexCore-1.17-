package com.base.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.base.main.Base;

public class Communication extends Event{
	
	@EventHandler
	public void onMessage(AsyncPlayerChatEvent e) {
		
		for(com.base.communication.Communication i : Base.getInstance().getCommunicationHandler().getPendingData()) {
			
			Player sender = e.getPlayer();
			
			if(!i.isActive()) continue;
			
			if(sender.getPlayer().getUniqueId().compareTo(i.getUser()) == 0) {
				
				Bukkit.getScheduler().runTask(Base.getInstance(), () -> {
					i.getInput().onRecieve(e.getMessage());
				});
				
				i.setActive(false);
				e.setCancelled(true);
			}
			
		}
	}

}
