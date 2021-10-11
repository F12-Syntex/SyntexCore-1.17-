package com.base.communication;

@FunctionalInterface
public interface Input {
	public abstract void onRecieve(String message);
}
