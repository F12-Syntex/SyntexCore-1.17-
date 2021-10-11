package com.base.debugger;

public enum TYPE {
	INFO("&b"), ERROR("&c"), NOTICE("&6");

	private String colour;
	
	private TYPE(String colour) {
		this.colour = colour;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}
	
}
