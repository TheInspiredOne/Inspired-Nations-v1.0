package com.github.InspiredOne.InspiredNationsClient.Hud;

import org.bukkit.event.EventHandler;

import com.github.InspiredOne.InspiredNations.Economy.TaxTimerEvent;
import com.github.InspiredOne.InspiredNationsClient.Listeners.InspiredListener;

public class TaxTimerListener<T extends TaxTimerManager<?>> extends InspiredListener<T> {

	public TaxTimerListener(T manager) {
		super(manager);
	}
	
	@EventHandler
	public void onTaxTimerEvent(TaxTimerEvent event) {
		if(manager.getActionMenu().PDI.TimerState) {
			this.manager.Update();
		}
	}
}
