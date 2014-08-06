package com.github.InspiredOne.InspiredNationsClient.Hud;

import com.github.InspiredOne.InspiredNationsClient.Listeners.ActionManager;

public class TaxTimerManager<T extends ActionMenu> extends ActionManager<T> {

	public TaxTimerManager(T menu) {
		super(menu);
		this.listeners.add(new TaxTimerListener<TaxTimerManager<?>>(this));
	}

}
