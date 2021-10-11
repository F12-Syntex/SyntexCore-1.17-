package com.base.gui;

import java.util.ArrayList;
import java.util.List;

public class SlotMappingHandler {

	private List<SlotMapping> mappings = new ArrayList<>();
	
	public SlotMappingHandler() {
	
	}

	public List<SlotMapping> getMappings() {
		return mappings;
	}

	public void setMappings(List<SlotMapping> mappings) {
		this.mappings = mappings;
	}
	
	public SlotMapping getItem(Slot page) {
		return this.mappings.stream().filter(i -> i.getPage().isSame(page)).findFirst().get();
	}
	
	public boolean hasItem(Slot page) {
		return this.mappings.stream().filter(i -> i.getPage().isSame(page)).count() > 0;
	}
	
	public void clear() {
		this.mappings.clear();
	}
	
	public void put(Slot slot, GenericGuiItem item) {
		SlotMapping mapping = new SlotMapping(slot, item);
		
		if(this.hasItem(slot)) {
			this.remove(slot);
		}
		
		this.mappings.add(mapping);
	}
	
	public void remove(Slot slot) {
		int indexToRemove = -1;
		for(int i = 0; i < this.mappings.size(); i++) {
			if(this.mappings.get(i).getPage().isSame(slot)) {
				indexToRemove = i;
				break;
			}
		}
		if(indexToRemove != -1) {
			this.mappings.remove(indexToRemove);
		}
	}
	
	public class SlotMapping {
		
		private Slot page;
		private GenericGuiItem item;
	
		public SlotMapping(Slot page, GenericGuiItem item) {
			this.page = page;
			this.item = item;
		}
		
		public Slot getPage() {
			return page;
		}
		public void setPage(Slot page) {
			this.page = page;
		}
		public GenericGuiItem getItem() {
			return item;
		}
		public void setItem(GenericGuiItem item) {
			this.item = item;
		}
		
		
	}
	
}
