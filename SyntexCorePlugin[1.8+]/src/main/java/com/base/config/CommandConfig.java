package com.base.config;

public abstract class CommandConfig extends Config{
	public abstract String getName();
	public abstract String getPermission();
	public abstract String getInfo();
	public abstract String getUsage();
	public abstract long getCooldown();
}
