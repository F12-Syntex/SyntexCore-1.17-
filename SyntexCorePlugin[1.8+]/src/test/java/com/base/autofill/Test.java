package com.base.autofill;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
	
		List<String> test = new ArrayList<>();
		test.add("com.syntex.test");
		test.add("com.syntex.core");
		test.add("com.syntex.reload");
		
		Autofill fill = new Autofill(test);
		
		fill.getTab("com").forEach(System.out::println);
		
		
	}

}
