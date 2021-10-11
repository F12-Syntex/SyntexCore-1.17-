package com.base.utilities;

import java.util.Optional;

import org.apache.commons.lang.WordUtils;

public class StringUtils extends WordUtils{
	
	public static String removeLastCharOptional(String s) {
		return removeLastCharOptional(s, 1);
	}
	public static String removeLastCharOptional(String s, int length) {
	    return Optional.ofNullable(s)
	      .filter(str -> str.length() != 0)
	      .map(str -> str.substring(0, str.length() - length))
	      .orElse(s);
	}

}
