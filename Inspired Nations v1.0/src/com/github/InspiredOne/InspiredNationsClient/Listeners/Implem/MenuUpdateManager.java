package com.github.InspiredOne.InspiredNationsClient.Listeners.Implem;

import com.github.InspiredOne.InspiredNationsClient.Hud.ActionMenu;
import com.github.InspiredOne.InspiredNationsClient.Listeners.ActionManager;

public class MenuUpdateManager<T extends ActionMenu> extends ActionManager<T> {

	public MenuUpdateManager(T menu) {
		super(menu);
		this.listeners.add(new MenuUpdateListener<ActionManager<?>>(this));
	}
}
