package com.base.config;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.base.debugger.Debugger;
import com.base.main.Base;
import com.base.utilities.TimeUtils;

public abstract class Config {

	protected FileConfiguration configuration;
	protected File config;
	protected Base base;
	public List<ConfigItem> items;	
	
	private ConfigMeta configMeta;

	public Config() {
		this.base = Base.getInstance();
		this.items = new ArrayList<ConfigItem>();
		this.configMeta = this.getClass().getAnnotation(ConfigMeta.class);
		
	}
	
	public abstract void defaults();
	
	public void loadConfigurableItems() {
		
		for(Field field : this.getClass().getDeclaredFields()){
			
			  Configurable annotation = field.getAnnotation(Configurable.class);
			  
			  if(annotation == null) continue;
			  
			try {
				
				  String dir = "";
				  
				  switch (annotation.child()){
					case INCLUSIVE: {
						dir = annotation.path() + "." + field.getName();
						break;
					}
					case EXCLUSIVE: {
						dir = annotation.path();
						break;
					}
				  }
				  
				ConfigItem path = new ConfigItem(dir, field.get(this));
				
				this.items.add(path);
				
			} catch (Exception e) {
				e.printStackTrace();
			}	
			  
		}
		
		this.defaults();
		
	}
	
    public File initialize() {
    	
    	List<String> missing = new ArrayList<String>();
    	
		for(Field field : this.getClass().getDeclaredFields()){
			
			  Configurable annotation = field.getAnnotation(Configurable.class);
			  
			  if(annotation == null) continue;
			  
			  try {

				  String path = "";
				  
				  switch (annotation.child()){
					case INCLUSIVE: {
						path = annotation.path() + "." + field.getName();
						break;
					}
					case EXCLUSIVE: {
						path = annotation.path();
						break;
					}
				  }
				  
				  Object data = this.getConfiguration().get(path);

				  field.set(this, data);
				  
				  if(data == null) {
					  missing.add(path);
				  }
				  
			} catch (Exception e) {
				e.printStackTrace();
			}
			  
		}
		
		missing = missing.stream().distinct().collect(Collectors.toList());
		
		if(!missing.isEmpty()) {
			missing.forEach(o -> {
				Debugger.error(this.configMeta.name() + " has a missing attribute -> &6" + o);
			});
			Debugger.notice("reseting " + this.configMeta.name());
			return this.backup(BackupReason.INVALID_CONFIGURATION);
		}
		
		
    	
    	this.init();
		return null;
    }

	
	public FileConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(FileConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public void setup() {
		
		this.loadConfigurableItems(); //load configurable items
		
		config = new File(this.base.getDataFolder().getAbsolutePath(), this.configMeta.name());
		
		if(this.configMeta.folder() != Folder.DEFAULT) {
			File old = new File(this.base.getDataFolder().getAbsolutePath(), this.configMeta.folder().getPath());
			config = new File(old, this.configMeta.name());
		}
		
		this.configuration = YamlConfiguration.loadConfiguration(config);
		
		if(!config.exists()) {
		
			this.configuration.set("identity.version", this.configMeta.version());
			
			String header = "";
			
			List<String> headerLore = this.header();
			
			if(!headerLore.isEmpty()) {
				for(int i = 0; i < headerLore.size(); i++) {
					if((i+1) < headerLore.size()) {
						header += headerLore.get(i) + "\n";	
					}else {
						header += headerLore.get(i);
					}
				}
			}
			
			this.configuration.options().header(header);
			
			for(ConfigItem i : this.items) {
				this.configuration.set(i.getPath(), i.getData());
			}
			
			
		}
		
		
		this.save();
		
	}

	public void setDefault() {
		this.configuration.set("identity.version", this.configMeta.version());
		
		for(ConfigItem i : this.items) {
			this.configuration.set(i.getPath(), i.getData());
		}
	}
	
    public void save() {
		try {
			this.configuration.save(this.config);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public abstract void init();
	public abstract List<String> header();
	
	public File backup(BackupReason outdated) {
		
		File depreciated = new File(this.base.getDataFolder(), "depreciated");
		File backups = new File(depreciated, this.configMeta.name());
		
		Debugger.notice("Something happened, backing up " + this.configMeta.name() + " | &7reason for backup -> &c" + outdated.name());
		if(backups.mkdirs() || backups.exists()) {

			Debugger.info("file saved, writting...");
			
			if(!this.getConfig().exists()) return null;
			
				int files = backups.listFiles().length + 1;
				
				String date = TimeUtils.getCurrentTimeInStringSafeForConfig();
				int hour = Integer.parseInt(date.split(" ")[1].split("-")[0]);
				String meridiem = hour > 12 ? "PM" : "AM";
				date = date.split(" ")[0] + " at " + date.split(" ")[1] + " " + meridiem;
				
				File backup = new File(backups, this.configMeta.name().split("[.]")[0] + " (" + date + ") (" + files + ")" + ".yml");
				
				final File tempConfig = this.getConfig();
				
				boolean success = this.getConfig().renameTo(backup);
				
				if(success) {
					Debugger.info("&a" + this.configMeta.name() + " has been backed up!");	
				}else {
					Debugger.error("&cCouldnt backup!");
				}
			
				return tempConfig;
			
		}else if(!backups.exists()) {
			Debugger.info("&cCouldnt create backup folder!");
		}
		
		return null;
		
	}
	
	
	public ConfigMeta getConfigMeta() {
		return configMeta;
	}


	public void setConfigMeta(ConfigMeta configMeta) {
		this.configMeta = configMeta;
	}

	public File getConfig() {
		return config;
	}

	public void setConfig(File config) {
		this.config = config;
	}
	
	
}
