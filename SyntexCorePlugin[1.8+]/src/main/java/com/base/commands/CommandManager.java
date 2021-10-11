package com.base.commands;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.reflections8.Reflections;
import org.reflections8.scanners.FieldAnnotationsScanner;
import org.reflections8.scanners.SubTypesScanner;
import org.reflections8.scanners.TypeAnnotationsScanner;
import org.reflections8.util.ClasspathHelper;
import org.reflections8.util.ConfigurationBuilder;

import com.base.automation.AutoFill;
import com.base.config.CommandConfig;
import com.base.config.ConfigManager;
import com.base.config.Messages;
import com.base.cooldowns.Cooldown;
import com.base.cooldowns.TimeFormater;
import com.base.debugger.Debugger;
import com.base.main.Base;
import com.base.utilities.MessageUtils;
import com.base.utilities.StringUtils;

public class CommandManager implements CommandExecutor{
	
	private List<SubCommand> commands;
	
    //Sub Commands
    public String[] main = {"base"};
	
	private Base plugin;

	public CommandManager() {
		this.commands = new ArrayList<>();
		this.plugin = Base.getInstance();
	}

	public void registerCommands() {
		
		ConfigManager configManager = Base.getInstance().getConfigManager();
	
		Reflections reflections = new Reflections(new ConfigurationBuilder()
													  .setUrls(ClasspathHelper.forPackage(this.getClass().getPackageName()))
													  .setScanners(new FieldAnnotationsScanner(),
															  	   new SubTypesScanner(),
															  	   new TypeAnnotationsScanner()));
		
		Set<Class<?>> commandClazzes = reflections.getTypesAnnotatedWith(CommandMeta.class);
		
		for(Class<?> command : commandClazzes) {
			
			try {
				
				SubCommand instance = (SubCommand) command.getConstructor().newInstance();
				
				this.commands.add(instance);
				
				CommandConfig config = configManager.loadCommand(instance);
				//feed to config
				
				Field[] fields = command.getSuperclass().getDeclaredFields();
				
				for(Field field : fields) {
					if(field.getAnnotation(AutoFill.class) != null) {
						
						switch (field.getName()) {
							case "name": {
								field.set(instance, config.getName());
								break;
							}
							case "permission": {
								field.set(instance, config.getPermission());
								break;
							}
							case "info": {
								field.set(instance, config.getInfo());
								break;
							}
							case "usage": {
								field.set(instance, config.getUsage());
								break;
							}
							case "cooldown": {
								Cooldown cooldown = new Cooldown(instance.getMeta().name(), config.getCooldown());
								field.set(instance, cooldown);
								break;
							}
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		//Automatic tab completer 
    	TabCompleter tabCompleter = new TabCompleter() {
			@Override
			public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
				
				//return list of commands for this plugin
				if(args.length == 1) {
				
					List<String> tabCommands = new ArrayList<String>();
					
					for(SubCommand i : commands) {
						tabCommands.add(i.name);
					}
					
					return tabCommands;
				}
	  
				//get the command if exists
				SubCommand parent = get(args[0]);
				
				//set the auto completer
				TabFill completer = null;
				
				try {
					completer = parent.tabFill(sender);
				}catch (Exception e) {
					return new ArrayList<String>();
				}
				
				if(completer == null) {
					return new ArrayList<String>();
				}
				
				completer.loadEntries();
				
				String filter = "";
				
				for(int i = 0; i < args.length - 1; i++) {
					filter += args[i] + ".";	
				}
				
				filter = StringUtils.removeLastCharOptional(filter, 1);
				
				List<String> values = completer.getTab(filter);
				
				return values;
			}
		};
    	
		
		//register the command.
    	for(String mainCommand : main) {
    		plugin.getCommand(mainCommand).setExecutor(this);
            plugin.getCommand(mainCommand).setTabCompleter(tabCompleter);
    	}    	
    	
    	Map<String, Long> entries = this.commands.stream()
    									.map(i -> i.name)
    									.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    	
    	boolean shutdown = false;
    	
    	for(SubCommand cmd : this.commands) {
    		Long duplicates = entries.get(cmd.name);
    		
    		if(duplicates > 1) {
    			Debugger.error("&cduplicate entry &6" + cmd.getMeta().name() + "&c has the name &6" + cmd.name + "&c x" + duplicates);
    			shutdown = true;
    		}
    	}
    	
    	if(shutdown) {
    		Bukkit.getPluginManager().disablePlugin(Base.getInstance());
    		return;
    	}
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Messages messages = Base.getInstance().getConfigManager().messages;
		
        if (!(sender instanceof Player)) {
        	//Invalid entity
            return true;
        }

        Player player = (Player) sender;

    	try {		
			
	        if(Arrays.asList(this.main).stream().filter(i -> i.equalsIgnoreCase(command.getName())).count() > 0) {
	
	        	//if the player has only executed the base command 
	            if (args.length == 0) {
	            
	            	SubCommand cmd = get("help");
	            	
	            	if(!player.hasPermission(cmd.permission)) {
			    		//insufficient permissions for help command
	            		MessageUtils.sendMessageWithoutPrefix(player, messages.invalid_permission);
			    		return true;
			        }
	            	
	            	if(cmd.cooldown.readyAndResetIfAvailable(player.getUniqueId()) || player.hasPermission(Base.getInstance().getConfigManager().cooldowns.bypass_cooldowns)) {
	            		cmd.onCommand(player, args);
	            	}else {
	            		//timer not ready
	            		MessageUtils.sendMessageWithoutPrefix(player, TimeFormater.parse(cmd.cooldown.getTimer(player.getUniqueId())));
	            	}
	            	
	                return true;
	
	            }
	
	            SubCommand target = this.get(args[0]);
	
	            if (target == null) {
	            	//syntax error
            		MessageUtils.sendMessageWithoutPrefix(player, messages.invalid_syntax);
	                return true;
	            }
	            
			    if(!player.hasPermission(target.permission)) {
		    		//insufficient permissions
            		MessageUtils.sendMessageWithoutPrefix(player, messages.invalid_permission);
		    		return true;
			    }
	
	            List<String> arrayList = new ArrayList<String>();
	
	            arrayList.addAll(Arrays.asList(args));
	
	            arrayList.remove(0);
	            		
	            //execute command if cooldown is 0
	            //reset cooldown if command is ready.
            	if(target.cooldown.readyAndResetIfAvailable(player.getUniqueId()) || player.hasPermission(Base.getInstance().getConfigManager().cooldowns.bypass_cooldowns)) {
            		target.onCommand(player, args);
            	}else {
            		//timer not ready
            		MessageUtils.sendMessageWithoutPrefix(player, TimeFormater.parse(target.cooldown.getTimer(player.getUniqueId())));
            	}
	
	        }


    }catch(Throwable e) {
    	//some error
		MessageUtils.sendMessageWithoutPrefix(player, messages.error);
        e.printStackTrace();
    }

        return true;
    
	}
	
    public SubCommand get(String name) {

        Iterator<SubCommand> subcommands = commands.iterator();

        while (subcommands.hasNext()) {

            SubCommand sc = (SubCommand) subcommands.next();

            if (sc.name.equalsIgnoreCase(name)) {
                return sc;
            }

        }

        return null;

    }
	
	
	public List<SubCommand> getCommands() {
		return commands;
	}

	public void setCommands(List<SubCommand> commands) {
		this.commands = commands;
	}
	
	public Base getBase() {
		return plugin;
	}

	public void setBase(Base base) {
		this.plugin = base;
	}
	
	public SubCommand getSubCommand(String name) {
		return this.commands.stream().filter(i -> i.getMeta().name().equalsIgnoreCase(name)).findFirst().get();
	}

	
}
