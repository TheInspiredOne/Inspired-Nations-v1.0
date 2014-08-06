package com.github.InspiredOne.InspiredNationsClient.Listeners.Implem;

import com.github.InspiredOne.InspiredNationsClient.Hud.ActionMenu;
import com.github.InspiredOne.InspiredNationsClient.Listeners.ActionManager;

public class GovSelectTabManager<T extends ActionMenu> extends ActionManager<T> {

	public String token = "";
	
	public GovSelectTabManager(T menu) {
		super(menu);
		this.listeners.add(new GovSelectTabListeners<GovSelectTabManager<T>>(this));
	}

}
