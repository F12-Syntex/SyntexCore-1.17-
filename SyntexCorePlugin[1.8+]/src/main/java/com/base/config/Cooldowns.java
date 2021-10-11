package com.base.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import com.base.cooldowns.Time;
import com.base.utilities.ListUtils;

@ConfigMeta(name = "cooldowns.yml", version = "1.0.1")
public class Cooldowns extends Config{

	@Configurable(path = "Cooldown") 
	public String formatting = "&a%time%&7(&6%shortname%&7)";
	
	@Configurable(path = "Cooldown") 
	public int complexity = 3;
	
	@Configurable(path = "Cooldown") 
	public String waiting_message = "%prefix% &cSorry, please wait %cooldown%&7 &cto use that command again!";
	
	@Configurable(path = "Permissions") 
	public String bypass_cooldowns = "base.cooldowns.bypass";
	
	private List<Time> timeSettings = new ArrayList<Time>();
	
	public Cooldowns() {}
	
	
	@Override
	public void defaults() {
		
		 this.items.add(new ConfigItem("Time.seconds.short_name", "s"));
		 this.items.add(new ConfigItem("Time.seconds.seconds", 1));
		 
		 this.items.add(new ConfigItem("Time.minutes.short_name", "m"));
		 this.items.add(new ConfigItem("Time.minutes.seconds", 60));
		 
		 this.items.add(new ConfigItem("Time.hours.short_name", "hr"));
		 this.items.add(new ConfigItem("Time.hours.seconds", 3600));
		 
		 this.items.add(new ConfigItem("Time.days.short_name", "d"));
		 this.items.add(new ConfigItem("Time.days.seconds", 86400));
		 
		 this.items.add(new ConfigItem("Time.weeks.short_name", "w"));
		 this.items.add(new ConfigItem("Time.weeks.seconds", 604800));
		 
		 this.items.add(new ConfigItem("Time.months.short_name", "mm"));
		 this.items.add(new ConfigItem("Time.months.seconds", 2419200));
		
	}
	
	@Override
	public void init() {
		
		ConfigurationSection section = this.getConfiguration().getConfigurationSection("Time");
		
		List<Time> data = new ArrayList<Time>();
		
		for(String name : section.getKeys(false)) {
		
			ConfigurationSection time = section.getConfigurationSection(name);
			
			String shortName = time.getString(".short_name");
			long seconds = time.getLong(".seconds");
			
			Time timeData = new Time(shortName, name, seconds);
			
			data.add(timeData);	
		}
		
		this.timeSettings = data;
	}

	@Override
	public List<String> header() {
		return ListUtils.createLore("formatting: {value}. This is the formatting used in order to display the cooldown delay.",
									"complexity: {number}. This is the number of elements shown in the cooldown timer.",
									"waiting_message: {text}. This is the message sent when the cooldown has not finished.",
									"\n\n",
									"Time.<time name>. this the name of your unit of time ( can be anything )",
									"Time.<time name>.short_name. This is the short name used in the cooldown message.",
									"Time.<time name>.seconds. The amount of seconds used to represent your unit of time.");
	}
	
	
	public List<Time> getTimeSettings() {
		return timeSettings;
	}

	public void setTimeSettings(List<Time> timeSettings) {
		this.timeSettings = timeSettings;
	}


}
