package com.base.config;

public enum Folder {

	DEFAULT(""), COMMANDS("commands"), GUI("interfaces");
	
	private String path;
	
	private Folder(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
	
	
}
