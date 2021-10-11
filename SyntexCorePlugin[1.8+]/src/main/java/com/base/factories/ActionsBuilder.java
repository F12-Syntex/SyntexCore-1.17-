package com.base.factories;

import java.util.HashMap;
import java.util.Map;

import com.base.gui.Condition;

public class ActionsBuilder {

	private Map<String, Condition> pairs = new HashMap<>();
	
	public ActionsBuilder(Map<String, Condition> pairs) {
		this.pairs = pairs;
	}
	
	public static ActionsBuilder createActions(String key, Condition value) {
		Map<String, Condition> set = new HashMap<>();
		set.put(key, value);
		ActionsBuilder factory = new ActionsBuilder(set);
		return factory;
	}
	
	public static ActionsBuilder createActions(Map<String, Condition> pairs) {
		ActionsBuilder factory = new ActionsBuilder(pairs);
		return factory;
	}

	public Map<String, Condition> getPairs() {
		return pairs;
	}

	public void setPairs(Map<String, Condition> pairs) {
		this.pairs = pairs;
	}
	
	public ActionsBuilder addActions(String key, Condition value) {
		this.pairs.put(key, value);
		return this;
	}
	
	public ActionsBuilder include(Map<String, Condition> keys) {
		for(String key : keys.keySet()) {
			this.pairs.put(key, keys.get(key));
		}
		return this;
	}
	
	public Map<String, Condition> get(){
		return this.pairs;
	}
	
	public static  Map<String, Condition> empty(){
		return new HashMap<>();
	}
	
}
