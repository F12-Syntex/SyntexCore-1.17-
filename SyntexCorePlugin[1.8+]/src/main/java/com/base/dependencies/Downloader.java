package com.base.dependencies;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.base.debugger.Debugger;
import com.base.main.Base;

public class Downloader {
	
	private final String name;
	private final String url;
	
	public Downloader(String name, String url) {
		this.name = name;
		this.url = url;
	}

	public File install() {

		AtomicInteger totalBytesRead = new AtomicInteger(0);
		
		Debugger.info(" &bInstalling &6" + name);
		
		File output = new File(Base.getInstance().getDataFolder().getParentFile(), name);
		
		try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
			FileOutputStream fileOutputStream = new FileOutputStream(output)) {
		  
			byte dataBuffer[] = new byte[1024];
		    int bytesRead;
		    
		    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		    
		    Runnable logger = new Runnable() {
		        public void run() { 
		        	Debugger.info(" &bInstalling &6" + name + "&b " + totalBytesRead.get()/100000.0  + "(mb)");
		        }
		    };
		    
		    scheduler.scheduleAtFixedRate(logger, 0, 100, TimeUnit.MILLISECONDS);
		    
		    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
		        fileOutputStream.write(dataBuffer, 0, bytesRead);
		        totalBytesRead.addAndGet(bytesRead);
		    }
		    
		    Debugger.info(" &bInstallation of &6" + name + "&b is completed.");
		   
		    fileOutputStream.close();
		    scheduler.shutdown();
		    return output;
		    
		} catch (IOException e) {
		   e.printStackTrace();
		   Debugger.error("Couldnt install dependency &6" + name);
		}
		
		return null;
	}
	
}
