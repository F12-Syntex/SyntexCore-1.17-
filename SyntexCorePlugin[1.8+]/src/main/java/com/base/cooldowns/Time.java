package com.base.cooldowns;

public class Time {
	
	private String shortName;
	private String longName;
	
	private long seconds;

	public Time(String shortName, String longName, long seconds) {
		this.shortName = shortName;
		this.longName = longName;
		this.seconds = seconds;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public long getSeconds() {
		return seconds;
	}

	public void setSeconds(long seconds) {
		this.seconds = seconds;
	}

	
	

}
