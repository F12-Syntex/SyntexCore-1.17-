package com.base.config;

import java.util.ArrayList;
import java.util.List;

import com.base.utilities.LoreUtils;

@ConfigMeta(name = "messages.yml", version = "1.0.4", folder = Folder.DEFAULT)
public class Messages extends Config{

	@Configurable(path = "Messages") 
	public String prefix = "&c[&6SyntexCore&c]";
	
	@Configurable(path = "Messages")
	public String error = "%prefix% sorry an error has accured!";
	
	@Configurable(path = "Messages")
	public String invalid_syntax = "%prefix% &cInvalid syntax";
	
	@Configurable(path = "Messages")
	public String invalid_permission = "%prefix% &cYou cant do that!";
	
	@Configurable(path = "Messages")
	public String invalid_entitiy = "%prefix% &cplayers only!";  
	
	@Configurable(path = "Messages.configure")
	public String configure_timed_out = "%prefix% &cOperation timed out!";
	
					/* COMMAND SPECIFIC MESSAGES */
	
								/* HELP */
	
	@Configurable(path = "Commands.help.message", child = Inclusive.EXCLUSIVE)
	public String help_command = "%prefix% &c%command% &7:&b %info%"; 

	@Configurable(path = "Commands.help.info", child = Inclusive.EXCLUSIVE)
	public List<String> help_info = this.getInfoMessage();
	
								/* RELOAD */
	
	@Configurable(path = "Commands.reload.message", child = Inclusive.EXCLUSIVE)
	public String reload = "%prefix% &creloaded!";
	
								/* DEBUG */
	
	@Configurable(path = "Commands.debug.added", child = Inclusive.EXCLUSIVE)
	public String debug_logs_show = "%prefix% &cYou will now recieve logs!";
	
	@Configurable(path = "Commands.debug.removed", child = Inclusive.EXCLUSIVE)
	public String debug_logs_hide = "%prefix% &cYou will no longer recieve logs!";

	
	public Messages() {}

	@Override
	public void init() {}

	@Override
	public void defaults() {}
	
	@Override
	public List<String> header() {
		return LoreUtils.createLore("All messages can be modified here.");
	}
	
	

	/*     THIS SECTION SHALL BE USED TO GET SOME OF THE DEFAULT MESSAGES THAT WILL BE CONFIGURABLE     */
	
	private List<String> getInfoMessage(){
		List<String> result = new ArrayList<>();

		result.add("%prefix% &cName:&7 &b%command%");
		result.add("%prefix% &cPermission:&7 &b%permission%");
		result.add("%prefix% &cInfo:&7 &b%info%");
		result.add("%prefix% &cUsage:&7 &b%usage%");

		return result;
	}
	
	
}
