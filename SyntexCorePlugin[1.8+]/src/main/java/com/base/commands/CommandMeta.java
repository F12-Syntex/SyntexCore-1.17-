package com.base.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandMeta {
	String name();
	String permission() default "bukkit.command.help";
	String info();	
	String usage();
	String version() default "1.0.0";
	long cooldown() default 0;
}
