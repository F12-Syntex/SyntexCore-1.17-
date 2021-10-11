package com.base.dependencies;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;

import com.base.debugger.Debugger;
import com.base.main.Base;

public class Dependencies {
	
	private List<Dependency> dependencies = new ArrayList<>();
	
	public Dependencies() {
		//this.dependencies.add(new Dependency("WolfyUtilities", "wolfyutilities.jar", "https://dev.bukkit.org/projects/wolfyutilities/files/3429071/download"));
	}
	
	public void syncInstall() {
		
		List<Dependency> requiredToInstall = new ArrayList<>();
		
			for(Dependency o : this.dependencies) {
				boolean flag = false;
				for(Plugin i : Bukkit.getPluginManager().getPlugins()) {
					if(o.getName().equalsIgnoreCase(i.getName())) {
						flag = true;
						break;
					}
				}
				if(!flag) {
					requiredToInstall.add(o);
				}
				
			}

		if(requiredToInstall.isEmpty()) return;
			
		for(Dependency key : requiredToInstall) {
			Downloader downloader = new Downloader(key.getFileName(), key.getDownloadURL());
			File plugin = downloader.install();
			
			try {
				Bukkit.getPluginManager().loadPlugin(plugin);
			} catch (UnknownDependencyException | InvalidPluginException | InvalidDescriptionException e) {
				e.printStackTrace();
			}
		}
		
		Debugger.notice("&cPlease reload the server for the downloaded dependencies to initialize.");
		Bukkit.getPluginManager().disablePlugin(Base.getInstance());

	}

}
