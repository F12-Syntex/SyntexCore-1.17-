package com.base.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.base.main.Base;
import com.base.utilities.MessageUtils;

@CommandMeta(info = "displays logs for staff, in order to identify issues", name = "debug", usage = "/%command%", permission = "base.debug", version = "1.0.1")
public class Debug extends SubCommand{

	public List<UUID> whitelisted = new ArrayList<>();
	
	@Override
	public void onCommand(Player player, String[] args) {
		
		if(player.hasPermission(this.permission)) {
			if(whitelisted.contains(player.getUniqueId())) {
				//removed
				MessageUtils.sendMessageWithoutPrefix(player, Base.getInstance().getConfigManager().messages.debug_logs_hide);
				this.whitelisted.remove(player.getUniqueId());
			}else {
				//add
				MessageUtils.sendMessageWithoutPrefix(player, Base.getInstance().getConfigManager().messages.debug_logs_show);
				this.whitelisted.add(player.getUniqueId());
			}
		}
		
	}

	@Override
	public TabFill tabFill(CommandSender sender) {
		TabFill fill = new TabFill();
		return fill;
	}

}
