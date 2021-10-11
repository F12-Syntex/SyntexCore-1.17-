package com.base.dependencies;

public class Dependency {

	private final String name;
	private final String fileName;
	private final String downloadURL;
	
	public Dependency(String name, String fileName, String downloadURL) {
		this.name = name;
		this.fileName = fileName;
		this.downloadURL = downloadURL;
	}
	
	public String getName() {
		return name;
	}
	public String getFileName() {
		return fileName;
	}
	public String getDownloadURL() {
		return downloadURL;
	}
	
	
	
}
