package com.mod12.cabal.common.util;

import java.util.LinkedList;
import java.util.List;

public class History<X> {

	private LinkedList<X> history;
	
	public History() {
		history = new LinkedList<X>();
	}
	
	public History(History<X> copyHistory) {
		this();
		while (copyHistory.history.size() > 0) {
			history.add(copyHistory.history.removeFirst());
		}
	}

	public boolean addHistory(X x) {
		return history.add(x);
	}
	
	public List<X> getHistory() {
		return history;
	}
}
