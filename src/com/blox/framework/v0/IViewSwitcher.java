package com.blox.framework.v0;

public interface IViewSwitcher {
	boolean isSwitching();

	void switchTo(String id, boolean back);

	void render();
	
	void setViewFinder(IViewFinder finder);
}