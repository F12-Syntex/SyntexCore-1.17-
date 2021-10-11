package com.base.factories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceholderFactory {

	private Map<String, String> pairs = new HashMap<>();
	
	public PlaceholderFactory(Map<String, String> pairs) {
		this.pairs = pairs;
	}
	
	public static PlaceholderFactory createPlaceholder(String key, String value) {
		Map<String, String> set = new HashMap<>();
		set.put(key, value);
		PlaceholderFactory factory = new PlaceholderFactory(set);
		return factory;
	}
	
	public static PlaceholderFactory empty(){
		Map<String, String> map = new HashMap<>();
		return PlaceholderFactory.createPlaceholder(map);
	}
	
	public static PlaceholderFactory createPlaceholder(Map<String, String> pairs) {
		PlaceholderFactory factory = new PlaceholderFactory(pairs);
		return factory;
	}

	public Map<String, String> getPairs() {
		return pairs;
	}

	public void setPairs(Map<String, String> pairs) {
		this.pairs = pairs;
	}
	
	public PlaceholderFactory addPlaceholder(String key, String value) {
		this.pairs.put(key, value);
		return this;
	}
	
	public PlaceholderFactory addPlaceholder(Map<String, String> pairs) {
		for(String key : pairs.keySet()) {
			this.pairs.put(key, pairs.get(key));
		}
		return this;
	}
	
	public Map<String, String> get(){
		return this.pairs;
	}
	
	public String applyPlaceHolders(String data) {
		for(String key : this.pairs.keySet()) {
			data = data.replace(key, this.pairs.get(key));
		}
		return data;
	}
	
	public List<String> applyPlaceHolders(List<String> data) {
		List<String> result = new ArrayList<>();
		for(String line : data) {
			String contents = line;
			for(String key : this.pairs.keySet()) {
				contents = contents.replace(key, this.pairs.get(key));
			}
			result.add(contents);
		}
		return result;
	}
	
}
