package com.base.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
* Annotation to identify varaibles that need to be dynamically registered and initialized in a config.
* Currently only supports primitive values, non primitive data types should be registered/initialized manually.
*/

@Retention(RetentionPolicy.RUNTIME)
public @interface Configurable {
	String path() default ""; // configuration path for registering/initializing 
	Inclusive child() default Inclusive.INCLUSIVE; // by default, include the variable as a child node.
}
