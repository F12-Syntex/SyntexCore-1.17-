package com.base.gui;

public class InteractionSounds {
	
	private Sound open;
	private Sound close;
	
	public InteractionSounds(Sound open, Sound close) {
		this.open = open;
		this.close = close;
	}
	
	public Sound getOpen() {
		return open;
	}
	public void setOpen(Sound open) {
		this.open = open;
	}
	public Sound getClose() {
		return close;
	}
	public void setClose(Sound close) {
		this.close = close;
	}

}
