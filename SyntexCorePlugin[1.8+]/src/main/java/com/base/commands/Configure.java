package com.base.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.base.configure.gui.ConfigsFolderView;

@CommandMeta(info = "modify configs in game!", name = "configure", usage = "/%command%", permission = "base.configure", version = "1.0.0")
public class Configure extends SubCommand{
	
	@Override
	public void onCommand(Player player, String[] args) {
		ConfigsFolderView gui = new ConfigsFolderView(player, null, null);
    	gui.open();		    	
	}

	@Override
	public TabFill tabFill(CommandSender sender) {
		TabFill fill = new TabFill();
		return fill;
	}

}
