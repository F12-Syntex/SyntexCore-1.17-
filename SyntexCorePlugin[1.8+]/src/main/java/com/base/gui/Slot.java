package com.base.gui;

public class Slot {

	private final int page;
	private final int slot;

	public Slot(int page, int slot) {
		super();
		this.page = page;
		this.slot = slot;
	}
	
	public int getPage() {
		return page;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public static Slot slot(int pageNumber, int slot) {
		Slot page = new Slot(pageNumber, slot);
		return page;
	}
	
	public boolean isSame(Slot slot) {
		return this.page == slot.getPage() && this.slot == slot.getSlot();
	}
	
}
