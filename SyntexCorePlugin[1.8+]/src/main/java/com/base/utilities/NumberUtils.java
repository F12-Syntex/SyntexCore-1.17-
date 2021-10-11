package com.base.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberUtils {

	public static boolean isNumber(String i) {
		try {
			Integer.parseInt(i);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isDouble(String i) {
		try {
			Double.parseDouble(i);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isLong(String i) {
		try {
			Long.parseLong(i);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public static String prettyIntArray(int[] arr) {
		StringBuilder data = new StringBuilder();
		data.append("{ ");
		for(int i = 0; i < arr.length-1; i++) {
			data.append(arr[i] + ", ");
		}
		data.append(arr[arr.length-1] + " } ");
		return data.toString();
	}
	
	public static int[] buildFromString(String regex) {
		
		if(regex == null || regex.isEmpty()) {
			return new int[0];
		}
		
		String simplify = regex.trim().replace(" ", "");
		
		if(!simplify.contains("-")) {
			return new int[] {Integer.parseInt(simplify)};
		}
		
		int start = Integer.parseInt(simplify.split("-")[0]);
		int end = Integer.parseInt(simplify.split("-")[1]);
		
		List<Integer> arrs = new ArrayList<Integer>();
		
		for(int i = Math.min(start, end); i < Math.max(start, end)+1; i++) {
			arrs.add(i);
		}
		
		return arrs.stream()
				   .mapToInt(Integer::intValue)
				   .toArray();
	}
	
	public static String buildStringFromInt(int[] arr) {
		
		if(arr.length == 0) {
			return "";
		}
		
		if(arr.length == 1) {
			return arr[0] + "";
		}
		
		int min = arr[0];
		int max = arr[0];
		
		for(int i : arr) {
			if(i < min) min = i;	
			if(i > max) max = i;
		}
		
		return min + "-" + max;
	}
	
	public static List<Integer> of(int... i){
		return IntStream.of(i).boxed().collect(Collectors.toList());
	}
	
	public static List<Integer> of(int min, int max){
		return IntStream.range(min, max).boxed().collect(Collectors.toList());
	}
	
	public static int max(int... i) {
		
		int[] numbers = i.clone();
		
		int max = numbers[0];
		
		for(int index = 0; index < numbers.length; index++) {
			if(numbers[index] > max) max = numbers[index];
		}
		
		return max;
		
	}
	
	public static int min(int... i) {
		
		int[] numbers = i.clone();
		
		int min = numbers[0];
		
		for(int index = 0; index < numbers.length; index++) {
			if(numbers[index] < min) min = numbers[index];
		}
		
		return min;
		
	}
	
	
}
