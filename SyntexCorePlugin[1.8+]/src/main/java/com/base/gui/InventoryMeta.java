package com.base.gui;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface InventoryMeta {
	String name();
	String permission() default "bukkit.command.help";
	int rows();
	boolean takeItems() default false;
	String version() default "1.0.0";
	String config();
}
