package com.base.cooldowns;

import java.util.Comparator;
import java.util.List;

import com.base.config.Cooldowns;
import com.base.main.Base;

public class TimeFormater {

	public static String parse(long seconds) {
		
		StringBuilder builder = new StringBuilder();
		Cooldowns cooldown = Base.getInstance().getConfigManager().cooldowns;
		
		List<Time> timeData = cooldown.getTimeSettings();
		
		timeData.sort(Comparator.comparingLong(Time::getSeconds).reversed());
		
		String formatting = cooldown.formatting;
		int complexity = cooldown.complexity;
		
		for(Time data : timeData) {
			if(data.getSeconds() <= seconds) {
				builder.append(formatting.replace("%name%", data.getLongName()).replace("%shortname%", data.getShortName()).replace("%time%", seconds/data.getSeconds()+""));
				seconds -= data.getSeconds() * (seconds/data.getSeconds());
				if(complexity == 0) {
					return builder.toString().trim();
				}
				complexity-=1;
			}
		}
	
		String formatted = builder.toString().trim();
		
		String reply = cooldown.waiting_message.replace("%cooldown%", formatted);
		
		return reply.toString().trim();
	
	}
	
}
