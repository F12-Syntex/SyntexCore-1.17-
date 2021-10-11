package com.base.factories;

import java.util.HashMap;
import java.util.Map;

public class ExecutionFactory {

	private Map<String, Runnable> pairs = new HashMap<>();
	
	public ExecutionFactory(Map<String, Runnable> pairs) {
		this.pairs = pairs;
	}
	
	public static ExecutionFactory createExecution(String key, Runnable value) {
		Map<String, Runnable> set = new HashMap<>();
		set.put(key, value);
		ExecutionFactory factory = new ExecutionFactory(set);
		return factory;
	}
	
	public static ExecutionFactory createExecution(Map<String, Runnable> pairs) {
		ExecutionFactory factory = new ExecutionFactory(pairs);
		return factory;
	}

	public Map<String, Runnable> getPairs() {
		return pairs;
	}

	public void setPairs(Map<String, Runnable> pairs) {
		this.pairs = pairs;
	}
	
	public ExecutionFactory addExecution(String key, Runnable value) {
		this.pairs.put(key, value);
		return this;
	}
	
	public ExecutionFactory include(Map<String, Runnable> keys) {
		for(String key : keys.keySet()) {
			this.pairs.put(key, keys.get(key));
		}
		return this;
	}
	
	public Map<String, Runnable> get(){
		return this.pairs;
	}
	
}
