
package com.mod12.cabal.common.util;

import java.util.LinkedList;
import java.util.List;

public class ListUtil {

	public static <T> List<T> arrayToList(T[] t) {
		LinkedList<T> list = new LinkedList<T>();
		for (int i = 0; i < t.length; i++) {
			list.add(t[i]);
		}
		return list;
	}
	
	public static <T> String printFormatArray(T[] array) {
		String string = "[";
		for (T t : array) {
			string += t.toString() + ",";
		}
		string = string.substring(0, string.length() - 1) + "]";
		return string;
	}
	
}
