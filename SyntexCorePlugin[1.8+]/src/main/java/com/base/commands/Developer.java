package com.base.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.base.gui.PagedGUI;
import com.base.guis.ExampleGUI;

@CommandMeta(info = "This command is for my personal testing, and shall be removed during the final build", name = "developer", usage = "/%command%")
public class Developer extends SubCommand{

	@Override
	public void onCommand(Player player, String[] args) {
		
		PagedGUI gui = new ExampleGUI();
		gui.open(player, 1);
		
	}

	@Override
	public TabFill tabFill(CommandSender sender) {
		TabFill fill = new TabFill();
		return fill;
	}

}
