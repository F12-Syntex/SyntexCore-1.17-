package com.base.gui;

import org.bukkit.inventory.ItemStack;

public class Action {

		private String condition;
		private ItemStack block;
		
		public Action(String condition, ItemStack block) {
			this.condition = condition;
			this.block = block;
		}

		public String getCondition() {
			return condition;
		}

		public void setCondition(String condition) {
			this.condition = condition;
		}

		public ItemStack getBlock() {
			return block;
		}

		public void setBlock(ItemStack block) {
			this.block = block;
		}
		
	}