package com.base.config;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.reflections8.Reflections;
import org.reflections8.scanners.FieldAnnotationsScanner;
import org.reflections8.scanners.SubTypesScanner;
import org.reflections8.scanners.TypeAnnotationsScanner;
import org.reflections8.util.ClasspathHelper;
import org.reflections8.util.ConfigurationBuilder;

import com.base.automation.AutoFill;
import com.base.commands.SubCommand;
import com.base.debugger.Debugger;
import com.base.gui.GUIConfig;
import com.base.gui.GUIConfigManager;
import com.base.gui.GenericGuiItem;
import com.base.gui.PagedGUI;
import com.base.main.Base;
import com.base.utilities.ListUtils;

public class ConfigManager {

    public ArrayList<Config> config = new ArrayList<Config>();
    
    private List<Config> failed = new ArrayList<>();
    
    
    @AutoFill
    public Messages messages;
    
    @AutoFill
    public Permissions permissions;
    
    @AutoFill
    public Cooldowns cooldowns;
    
    @AutoFill
    public Configure configure;
    
    @AutoFill
    public Settings settings;
    
    public void setup() {
    	
    	this.failed.clear();
    	
    	Reflections reflections = new Reflections(new ConfigurationBuilder()
				  .setUrls(ClasspathHelper.forPackage(this.getClass().getPackageName()))
				  .setScanners(new FieldAnnotationsScanner(),
						  	   new SubTypesScanner(),
						  	   new TypeAnnotationsScanner()));
    	
    		
    	Field[] configs = this.getClass().getDeclaredFields();
    	
    	//dynamically load all the configs
    	reflections.getTypesAnnotatedWith(ConfigMeta.class).forEach(i -> {
			try {
				Config instance = (Config) i.getConstructor().newInstance();
				this.config.add(instance);
				
				for(Field o : configs) {
					if(o.getAnnotation(AutoFill.class) != null) {
						if(o.getType().isAssignableFrom(instance.getClass())) {
							o.set(this, instance);
						}			
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
    	});
    	
    	this.configure(Base.getInstance(), config);
    	
    	//register gui configs
    	GUIConfigManager guiConfigManager = new GUIConfigManager();
    	guiConfigManager.loadGUIs(this);
    	
    	
    	
    }

	public void reload() {
		
		Debugger.info("reloading");
		
		this.config.clear();
		
		Debugger.info("initializing base configs and gui configs");
		//load basic configs
    	this.setup();
    	
    	
		Debugger.info("initializing command configs");
    	//load command configs
    	Base.getInstance().getCommandManager().getCommands().forEach(this::loadCommand);
    	
		Debugger.info("reload complete!");
    	
    }
	
	
    public void configure(Plugin plugin, Config currentConfig) {

		currentConfig.setup();
		
		File content = null;
		
		if(currentConfig.getConfiguration().contains("identity.version") &&
		  (currentConfig.getConfiguration().getString("identity.version").equals(currentConfig.getConfigMeta().version()))) {
			
			content = currentConfig.initialize();
			
			if(content != null) {
				this.failed.add(currentConfig);
			}else {
    			return;
			}
			
		}
		
		File file;
	
		if(content != null) {
			file = content;
			Debugger.info(currentConfig.getConfigMeta().name() + " has been reinstalled!");
		}else {
			file = currentConfig.backup(BackupReason.OUTDATED);
		}
	

		if(currentConfig.getConfigMeta().folder() == Folder.DEFAULT) {
			
			File erase = new File(plugin.getDataFolder(), currentConfig.getConfigMeta().name());
			
			try {
				new FileOutputStream(erase).close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			File path = new File(plugin.getDataFolder(), currentConfig.getConfigMeta().folder().getPath());
			File erase = new File(path, currentConfig.getConfigMeta().name());
			
			try {
				new FileOutputStream(erase).close();
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
	
		if(file != null) {
			final FileConfiguration oldConfig = YamlConfiguration.loadConfiguration(file);
			
			oldConfig.getKeys(true).forEach(o -> {
				if(currentConfig.getConfiguration().contains(o)) {
					currentConfig.getConfiguration().set(o, oldConfig.get(o));
				}
			});
    
			currentConfig.setDefault();
			
    		currentConfig.getConfiguration().set("identity.version", currentConfig.getConfigMeta().version());
			
    		currentConfig.save();
    		currentConfig.initialize();
    		
		}

    	
    }

    public void configure(Plugin plugin, List<Config> configs) {
    	for(int i = 0; i < configs.size(); i++) {
        	this.configure(Base.getInstance(), configs.get(i));
    	}
    }
    
    

    public CommandConfig loadCommand(SubCommand cmd) {
		
		//create instance
		CommandConfig command = new CommandConfig() {
			
			@Configurable(path = "Settings")
			public String name = cmd.getMeta().name();
			
			@Configurable(path = "Settings")
			public String permission = cmd.getMeta().permission();
			
			@Configurable(path = "Settings")
			public String info = cmd.getMeta().info();

			@Configurable(path = "Settings")
			public String usage = cmd.getMeta().usage();
			
			@Configurable(path = "Settings")
			public long cooldown = cmd.getMeta().cooldown();
			
			@Override
			public void defaults() {}
			
			@Override
			public void init() {}
			
			@Override
			public List<String> header() {
				return ListUtils.createLore("Modify the settings for " + cmd.getMeta().name() + " here.");
			}

			@Override
			public String getName() {
				return this.name;
			}

			@Override
			public String getPermission() {
				return this.permission;
			}

			@Override
			public String getInfo() {
				return this.info;
			}

			@Override
			public String getUsage() {
				return this.usage;
			}

			@Override
			public long getCooldown() {
				return this.cooldown;
			}
			
		};
		
		//set meta
		command.setConfigMeta(new ConfigMeta() {
			
			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}
			
			@Override
			public String version() {
				return cmd.getMeta().version();
			}
			
			@Override
			public String name() {
				// TODO Auto-generated method stub
				return cmd.getMeta().name() + ".yml";
			}
			
			@Override
			public Folder folder() {
				return Folder.COMMANDS;
			}
			
		});
		
		this.config.add(command);
		
		//load config
		this.configure(Base.getInstance(), command);
		
		return command;		
    }
    
    public GUIConfig loadGUI(com.base.gui.Configurable gui) {

		//create instance
		GUIConfig guiConfig = new GUIConfig() {
			
			@Configurable(path = "Settings")
			public String name = gui.getMeta().name();
			
			@Configurable(path = "Settings")
			public String permission =  gui.getMeta().permission();
			
			@Configurable(path = "Settings")
			public boolean canTakeItems =  gui.getMeta().takeItems();
			
			
			@Override
			public void defaults() {
			
				this.loadedGenericItems = gui.getItems();
				
				if(gui instanceof PagedGUI) {
					PagedGUI pagedgui = (PagedGUI)gui;
					this.loadedGenericItems.addAll(pagedgui.getDefaultBarItems());
				}
				
				this.registerItems(loadedGenericItems);
				
			}
			
			
			@Override
			public void init() {
				this.loadedGenericItems = this.getLoadedItems();
			}
				
			
			@Override
			public List<String> header() {
				return ListUtils.createLore("Modify the settings for " + gui.getMeta().name() + " here.");
			}

			@Override
			public String getName() {
				return this.name;
			}

			@Override
			public String getPermission() {
				return this.permission;
			}

			@Override
			public boolean canTakeItems() {
				return this.canTakeItems;
			}

			@Override
			public List<GenericGuiItem> items() {
				return null;
			}

		};
		
		//set meta
		guiConfig.setConfigMeta(new ConfigMeta() {
			
			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}
			
			@Override
			public String version() {
				return gui.getMeta().version();
			}
			
			@Override
			public String name() {
				// TODO Auto-generated method stub
				return gui.getMeta().config();
			}
			
			@Override
			public Folder folder() {
				return Folder.GUI;
			}
			
		});
		
		this.config.add(guiConfig);
		
		//load config
		this.configure(Base.getInstance(), guiConfig);
		
		return guiConfig;		
	}

    public CommandConfig getCommandConfig(String name) {
    	return this.config
				   .stream()
				   .filter(i -> i instanceof CommandConfig)
				   .map(i -> (CommandConfig)i)
				   .filter(i -> i.getConfigMeta().name().equals(name))
				   .findFirst().get();
    }
    public GUIConfig getGUIConfig(String name) {
    	return this.config
				   .stream()
				   .filter(i -> i instanceof GUIConfig)
				   .map(i -> (GUIConfig)i)
				   .filter(i -> i.getConfigMeta().name().equals(name))
				   .findFirst().get();
    }
    
}
