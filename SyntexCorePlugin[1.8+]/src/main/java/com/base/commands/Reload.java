package com.base.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.base.main.Base;
import com.base.utilities.MessageUtils;

@CommandMeta(info = "Reloads the plugin", name = "reload", permission = "bukkit.*", usage = "%prefix% /%command%", version = "1.0.1")
public class Reload extends SubCommand{

	@Override
	public void onCommand(Player player, String[] args) {
		Base.getInstance().getConfigManager().reload();
		MessageUtils.sendMessageWithoutPrefix(player, Base.getInstance().getConfigManager().messages.reload);
	}

	@Override
	public TabFill tabFill(CommandSender sender) {
		TabFill fill = new TabFill();
		return fill;
	}


}
