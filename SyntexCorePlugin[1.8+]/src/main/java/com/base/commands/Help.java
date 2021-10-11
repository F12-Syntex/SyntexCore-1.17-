package com.base.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.base.config.Messages;
import com.base.factories.PlaceholderFactory;
import com.base.main.Base;
import com.base.utilities.ListUtils;
import com.base.utilities.MessageUtils;

@CommandMeta(info = "Displays a list of helpful commands",
			name = "help",
			usage = "%prefix% /%command% {command}",
			version = "1.0.1")
public class Help extends SubCommand{

	@Override
	public void onCommand(Player player, String[] args) {
		
		List<SubCommand> commands = Base.getInstance().getCommandManager().getCommands();
		Messages messages = Base.getInstance().getConfigManager().messages;
		
		if(args.length == 0 || args.length == 1) {
			commands.forEach(i -> {
				 
				String str = messages.help_command;
				 
				PlaceholderFactory factory = PlaceholderFactory.createPlaceholder("%command%", i.name)
															   .addPlaceholder("%permission%", i.permission)
															   .addPlaceholder("%info%", i.info)
															   .addPlaceholder("%usage%", i.usage);
				
				 MessageUtils.sendMessageWithoutPrefix(player, factory.applyPlaceHolders(str));
			});
			return;
		}
		
		SubCommand command = Base.getInstance().getCommandManager().get(args[1]);
		
		if(command == null) {
			this.sendInvalidUsageError(player);
			return;
		}
		
		PlaceholderFactory factory = PlaceholderFactory.createPlaceholder("%command%", command.name)
				   .addPlaceholder("%permission%", command.permission)
				   .addPlaceholder("%info%", command.info)
				   .addPlaceholder("%usage%", command.usage);
		
		List<String> infoMessage = ListUtils.createLore(messages.help_info);
		
		infoMessage = factory.applyPlaceHolders(infoMessage);
		
		for(String i : infoMessage) {
			MessageUtils.sendMessageWithoutPrefix(player, i);
		}
		
	}

	@Override
	public TabFill tabFill(CommandSender sender) {
		
		TabFill fill = new TabFill();
		
		for(SubCommand cmd : Base.getInstance().getCommandManager().getCommands()) {
			fill.createEntry("help." + cmd.name);
		}
		
		return fill;
	}


}
