package com.base.gui;

import java.util.ArrayList;
import java.util.List;

public class Actions {
	
	private List<Action> actions;
	
	public Actions() {
		this.actions = new ArrayList<>();
	}
	
	public void addAction(Action action) {
		this.actions.add(action);
	}
	
	public Action getAction(String condition) {
		return this.actions.stream().filter(i -> i.getCondition().equals(condition)).findFirst().get();
	}
	
	public boolean isEmpty() {
		return this.actions.isEmpty();
	}
	
	public List<Action> getActions(){
		return this.actions;
	}
		
}
