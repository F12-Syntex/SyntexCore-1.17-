package com.base.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TabFill {
	
	private List<Queue<String>> autofill;
	private List<String> entries;
	
	public TabFill() {
		this.autofill = new ArrayList<>();
		this.entries = new ArrayList<>();
	}

	public void createEntry(String entry) {
		this.entries.add(entry);
	}
	
	public void loadEntries() {
		
		this.autofill.clear();
		
		for(String i : entries) {
			
			Queue<String> autofilldata = new LinkedList<String>();
			
			for(String node : i.split("[.]")) {
				autofilldata.add(node);
			}
			
			this.autofill.add(autofilldata);
			
		}
	}
	
	public List<String> getTab(String query) {
		
		List<String> results = new ArrayList<>();
		
		for(Queue<String> queue : this.autofill) {
			
			String[] check = query.split("[.]");
			
			for(int i = 0; i < check.length; i++) {
			
				String node = queue.poll();
				
				if(node == null || !node.equalsIgnoreCase(check[i])) {
					break;
				}
	
				if((i+1) == check.length) {
					String result = queue.poll();
					if(result == null) break;
					results.add(result);
				}
			}
		}
		
		return results;
	}
	
	

}
