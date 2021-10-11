package com.base.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.base.automation.AutoFill;
import com.base.cooldowns.Cooldown;
import com.base.factories.PlaceholderFactory;
import com.base.utilities.MessageUtils;

public abstract class SubCommand {

	public SubCommand() {}
	
	@AutoFill
	public String name; //data will be injected from the config
	
	@AutoFill
	public String permission; //data will be injected from the config
	
	@AutoFill
	public String info; //data will be injected from the config
	
	@AutoFill
	public String usage; //data will be injected from the config
	
	@AutoFill
	public Cooldown cooldown; //data will be injected from the config
	
	
    public abstract void onCommand(Player player, String[] args);
    public abstract TabFill tabFill(CommandSender sender);
    
    
    public CommandMeta getMeta() {
    	return this.getClass().getAnnotation(CommandMeta.class);	
    }
    
    public void sendInvalidUsageError(Player player) {
    	String messageToSend = this.usage;
    	
    	PlaceholderFactory factory = PlaceholderFactory.createPlaceholder("%command%", this.name);
    	
    	messageToSend = factory.applyPlaceHolders(messageToSend);
    	
    	MessageUtils.sendMessageWithoutPrefix(player, messageToSend);
    }
    
}
