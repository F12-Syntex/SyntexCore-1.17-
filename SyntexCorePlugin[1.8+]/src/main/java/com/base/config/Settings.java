package com.base.config;

import java.util.List;

import com.base.utilities.LoreUtils;

@ConfigMeta(name = "settings.yml", version = "1.0.4", folder = Folder.DEFAULT)
public class Settings extends Config{

	@Configurable(path = "Settings.configure") 
	public long reponse_time = 20L*60; //1 minute
	
	public Settings() {}
	
	@Override
	public void init() {}
	
	@Override
	public void defaults() {}
	
	@Override
	public List<String> header() {
		return LoreUtils.createLore("You can change all sorts of settings over here!");
	}
	
	
}
