package com.base.communication;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.base.main.Base;
import com.base.utilities.MessageUtils;

public class Communication {

	private UUID user;
	private Input input;
	
	private boolean active;

	public BukkitTask timer;
	
	public Communication(UUID user, Input input) {
		this.user = user;
		this.input = input;
		this.active = true;
		
		this.timer = new BukkitRunnable() {
			@Override
			public void run() {
				if(!active) {
					this.cancel();
					return;
				}
				if(Bukkit.getPlayer(user) != null) {
					MessageUtils.sendMessageWithoutPrefix(Bukkit.getPlayer(user), Base.getInstance().getConfigManager().messages.configure_timed_out);
				}
				active = false;
				this.cancel();
			}
		}.runTaskLater(Base.getInstance(), Base.getInstance().getConfigManager().settings.reponse_time);
	}
	
	public UUID getUser() {
		return user;
	}
	public void setUser(UUID user) {
		this.user = user;
	}
	public Input getInput() {
		return input;
	}
	public void setInput(Input input) {
		this.input = input;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
}
