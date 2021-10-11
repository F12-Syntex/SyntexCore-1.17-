package com.base.commands.arguments;

public class BooleanValue {

	private final boolean result;
	private final boolean isNull;
	
	public BooleanValue(boolean result, boolean isNull) {
		this.result = result;
		this.isNull = isNull;
	}
	
	public boolean isResult() {
		return result;
	}
	public boolean isNull() {
		return isNull;
	}
	
}
