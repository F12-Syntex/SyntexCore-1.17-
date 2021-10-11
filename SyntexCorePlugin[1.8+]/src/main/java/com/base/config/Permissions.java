package com.base.config;

import java.util.List;

import com.base.utilities.LoreUtils;

@ConfigMeta(folder = Folder.DEFAULT, name = "permissions.yml", version = "1.0.0")
public class Permissions extends Config{

	@Configurable(path = "default")
	public String everyone = "bukkit.command.help";
	
	@Configurable(path = "admin")
	public String reload = "battlepets.admin.reload";
	
	@Configurable(path = "admin")
	public String configure = "battlepets.admin.configure";
	
	@Configurable(path = "admin")
	public String set = "battlepets.admin.set";

	public Permissions() {
		
	}

	@Override
	public void defaults() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void init() {}

	@Override
	public List<String> header() {
		return LoreUtils.createLore("All (un catogarised) permissions which are not labaled in the other configs will be here.");
	}

	
}
