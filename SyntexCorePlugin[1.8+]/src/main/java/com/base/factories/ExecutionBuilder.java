package com.base.factories;

import java.util.HashMap;
import java.util.Map;

public class ExecutionBuilder {

	private Map<String, Runnable> pairs = new HashMap<>();
	
	public ExecutionBuilder(Map<String, Runnable> pairs) {
		this.pairs = pairs;
	}
	
	public static ExecutionBuilder createExecution(String key, Runnable value) {
		Map<String, Runnable> set = new HashMap<>();
		set.put(key, value);
		ExecutionBuilder factory = new ExecutionBuilder(set);
		return factory;
	}
	
	public static ExecutionBuilder createExecution(Map<String, Runnable> pairs) {
		ExecutionBuilder factory = new ExecutionBuilder(pairs);
		return factory;
	}

	public static ExecutionBuilder empty(){
		Map<String, Runnable> empty = new HashMap<>();
		return ExecutionBuilder.createExecution(empty);
	}
	
	public Map<String, Runnable> getPairs() {
		return pairs;
	}

	public void setPairs(Map<String, Runnable> pairs) {
		this.pairs = pairs;
	}
	
	public ExecutionBuilder addExecution(String key, Runnable value) {
		this.pairs.put(key, value);
		return this;
	}
	
	public ExecutionBuilder include(Map<String, Runnable> keys) {
		for(String key : keys.keySet()) {
			this.pairs.put(key, keys.get(key));
		}
		return this;
	}
	
	public Map<String, Runnable> get(){
		return this.pairs;
	}
	
}
