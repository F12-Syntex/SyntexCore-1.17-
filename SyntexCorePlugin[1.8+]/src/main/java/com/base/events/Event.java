package com.base.events;

import org.bukkit.event.Listener;

import com.base.config.Cooldowns;
import com.base.config.Messages;
import com.base.config.Permissions;
import com.base.main.Base;

public class Event implements Listener {

	protected Messages messages;
	protected Permissions permissions;
	protected Cooldowns cooldowns;
	
	public Event() {
		this.messages = Base.getInstance().getConfigManager().messages;
		this.permissions = Base.getInstance().getConfigManager().permissions;
		this.cooldowns = Base.getInstance().getConfigManager().cooldowns;
	}
	
}
