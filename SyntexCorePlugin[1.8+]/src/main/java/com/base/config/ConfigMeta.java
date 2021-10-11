package com.base.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigMeta {
	String name();
	String version() default "1.0.0";
	Folder folder() default Folder.DEFAULT;
}
