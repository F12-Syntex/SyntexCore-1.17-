package com.base.config;

import java.util.List;

import org.bukkit.Material;

import com.base.utilities.LoreUtils;

@ConfigMeta(name = "configure.yml", version = "1.0.3", folder = Folder.DEFAULT)
public class Configure extends Config{

	@Configurable(path = "Items") 
	public String Sections = Material.PAPER.name();
	
	@Configurable(path = "Items")
	public String Text = Material.STRING.name();
	
	@Configurable(path = "Items")
	public String BooleanON = Material.LIME_DYE.name();
	
	@Configurable(path = "Items")
	public String BooleanOFF = Material.GRAY_DYE.name();
	
	@Configurable(path = "Items")
	public String Numbers = Material.STICK.name();
	
	@Configurable(path = "Items")
	public String Lists = Material.REPEATER.name();
	
	
	public Configure() {}
	
	@Override
	public void init() {}
	
	@Override
	public void defaults() {}
	
	@Override
	public List<String> header() {
		return LoreUtils.createLore("Change the datatype items in the configure command");
	}
	
	
}
