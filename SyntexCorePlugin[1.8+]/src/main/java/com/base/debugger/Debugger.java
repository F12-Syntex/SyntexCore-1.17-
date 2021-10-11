package com.base.debugger;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.base.event.custom.LogEvent;
import com.base.main.Base;
import com.base.utilities.MessageUtils;
import com.base.utilities.TimeUtils;

public class Debugger {
	
	private static List<Log> logs = new ArrayList<>();
	private static boolean enabled = true;
	
	public static void error(String message) {
		Debugger.log(message, TYPE.ERROR);
	}
	public static void info(String message) {
		Debugger.log(message, TYPE.INFO);
	}
	public static void notice(String message) {
		Debugger.log(message, TYPE.NOTICE);
	}
	
	public static void log(String message, TYPE type) {	
		
		logs.add(new Log(message, TimeUtils.getCurrentTimeInDate()));
		
		String response = type.getColour() + " " + type.name() + " | &7" + message;
		
		if(enabled || type == TYPE.ERROR) {
			MessageUtils.sendConsoleMessage(response);
		}
		
		
		//fire logEvent
		new BukkitRunnable() {		
			@Override
			public void run() {
			    LogEvent event = new LogEvent(response);
			    Bukkit.getPluginManager().callEvent(event);	
			}
		}.runTask(Base.getInstance());
	}
	
	
	public static void enableDebugger() {
		Debugger.enabled = true;
	}
	public static void disableDebugger() {
		Debugger.enabled = false;
	}

}
