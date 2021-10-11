package com.base.commands.arguments;

public class ArgumentBuilder {
	
	private final String[] args;
	private int index = 0;
	
	public ArgumentBuilder(String[] args) {
		this.args = args;
	}
	
	public static ArgumentBuilder instance(String[] args) {
		ArgumentBuilder builder = new ArgumentBuilder(args);
		return builder;
	}
	
	public ArgumentProperty indexOf(int index) {
		this.index = index;
		return new ArgumentProperty(this);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String[] getArgs() {
		return args;
	}
	
	
	

}
