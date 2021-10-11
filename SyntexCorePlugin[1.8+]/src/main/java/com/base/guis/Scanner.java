package com.base.guis;

import org.reflections8.Reflections;
import org.reflections8.scanners.FieldAnnotationsScanner;
import org.reflections8.scanners.SubTypesScanner;
import org.reflections8.scanners.TypeAnnotationsScanner;
import org.reflections8.util.ClasspathHelper;
import org.reflections8.util.ConfigurationBuilder;

public class Scanner {

	public static Reflections scanner = new Reflections(new ConfigurationBuilder()
			  .setUrls(ClasspathHelper.forPackage(Scanner.class.getPackageName()))
			  .setScanners(new FieldAnnotationsScanner(),
					  	   new SubTypesScanner(),
					  	   new TypeAnnotationsScanner()));
	
}
