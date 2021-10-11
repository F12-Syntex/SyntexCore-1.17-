package com.base.gui;

@FunctionalInterface
public interface Condition {
	public abstract boolean valid(Configurable gui);
}
