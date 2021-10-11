package com.base.utilities;

import java.util.concurrent.ThreadLocalRandom;

public class LuckUtils {

	public static boolean chance(double percentage) {
		return ThreadLocalRandom.current().nextDouble() <= percentage;
	}
	
}
