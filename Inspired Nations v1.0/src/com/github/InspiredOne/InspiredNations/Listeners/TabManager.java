package com.github.InspiredOne.InspiredNations.Listeners;

import com.github.InspiredOne.InspiredNations.Hud.ActionMenu;

public class TabManager<T extends ActionMenu> extends ActionManager<T> {

	public String preTabEntry = "";
	
	public TabManager(T menu) {
		super(menu);
	}
	
	public void setPreTabEntry(String entry) {
		this.preTabEntry = entry;
	}
	
	public String getPreTabEntry() {
		return this.preTabEntry;
	}
}
