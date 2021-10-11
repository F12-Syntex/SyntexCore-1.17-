package com.base.main;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.base.commands.CommandManager;
import com.base.communication.CommunicationHandler;
import com.base.config.ConfigManager;
import com.base.dependencies.Dependencies;
import com.base.events.EventManager;

public class Base extends JavaPlugin implements Listener{

    private static Base instance;
	
    private CommandManager commandManager;
    private ConfigManager configManager;
    private EventManager eventManager;
    private Dependencies dependencies;
    private CommunicationHandler communicationHandler;
    
	@Override
	public void onEnable(){
		
		Base.instance = this;
		
		this.configManager = new ConfigManager();
		this.configManager.setup();

		this.commandManager = new CommandManager();
		this.commandManager.registerCommands();
		
		this.eventManager = new EventManager();
		this.eventManager.setup();
		
		this.dependencies = new Dependencies();
		this.dependencies.syncInstall();
		
		this.communicationHandler = new CommunicationHandler();

	}

	@Override
	public void onDisable(){}

	public static Base getInstance() {
		return instance;
	}

	public EventManager getEventManager() {
		return eventManager;
	}

	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
	}

	public Dependencies getDependencies() {
		return dependencies;
	}

	public void setDependencies(Dependencies dependencies) {
		this.dependencies = dependencies;
	}

	public CommunicationHandler getCommunicationHandler() {
		return communicationHandler;
	}

	public void setCommunicationHandler(CommunicationHandler communicationHandler) {
		this.communicationHandler = communicationHandler;
	}
	
	public CommandManager getCommandManager() {
		return commandManager;
	}

	public void setCommandManager(CommandManager commandManager) {
		this.commandManager = commandManager;
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public void setConfigManager(ConfigManager configManager) {
		this.configManager = configManager;
	}
	
	
}
