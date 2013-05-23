package com.mod12.cabal.common.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

public class Graphics {

	public static void centreWindow(Window frame) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) * 0.5);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) * 0.5);
	    frame.setLocation(x, y);
	}
	
}
