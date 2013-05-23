package com.mod12.cabal.common.util;

import java.util.Random;

public class RandomUtil {

	private static Random random = new Random();
	private static int CHANCE_ROLL = 100;
	
	public static int nextInt(int n) {
		return random.nextInt(n);
	}
	
	public static boolean nextBoolean() {
		return random.nextBoolean();
	}

	public static int roll() {
		return nextInt(CHANCE_ROLL);
	}
}
