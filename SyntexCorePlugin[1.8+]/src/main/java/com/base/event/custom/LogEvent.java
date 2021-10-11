package com.base.event.custom;

public class LogEvent extends CustomEvent {

	private final String message;
	

	public LogEvent(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {}

}
