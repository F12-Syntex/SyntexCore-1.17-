package com.base.gui;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.reflections8.Reflections;

import com.base.config.ConfigManager;
import com.base.guis.Scanner;
import com.base.main.Base;

public class GUIConfigManager {

	public GUIConfigManager() {
		// TODO Auto-generated constructor stub
	}
	
	public void loadGUIs(ConfigManager configManager) {
		Reflections scanner = Scanner.scanner;
		
		//get all gui classes
		Set<Class<?>> clazzes = scanner.getTypesAnnotatedWith(InventoryMeta.class);
		
		for(Class<?> gui : clazzes) {
			
			try {
				
				com.base.gui.Configurable instance = (com.base.gui.Configurable) gui.getConstructor().newInstance();
				
				GUIConfig config = configManager.loadGUI(instance);
				
				//add the config
				configManager.config.add(config);
				
				//load config
				configManager.configure(Base.getInstance(), config);
				
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
