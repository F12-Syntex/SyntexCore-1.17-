package com.base.autofill;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Autofill {
	
	private List<Queue<String>> autofill;
	
	public Autofill(List<String> autofill) {
		
		this.autofill = new ArrayList<>();

		for(String i : autofill) {
			
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
