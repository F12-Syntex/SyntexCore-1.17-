package com.base.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.plugin.Plugin;
import org.reflections8.Reflections;

import com.base.main.Base;

public class EventManager {
	
    public List<Event> events = new ArrayList<>();	
    private Plugin plugin = Base.getInstance();
    
    public EventManager() {}
    
	public void setup() {
		try {
	    	//Dynamically register all events.
	        Reflections reflections = new Reflections(this.getClass().getPackage().getName());    
	        Set<Class<? extends Event>> classes = reflections.getSubTypesOf(Event.class);
	        
	        for(Class<? extends Event> command : classes) {
	        	Event event = (Event) command.getConstructors()[0].newInstance();
				this.events.add(event);
	        }       
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		this.events.forEach(i -> plugin.getServer().getPluginManager().registerEvents(i, plugin));
	}
}
