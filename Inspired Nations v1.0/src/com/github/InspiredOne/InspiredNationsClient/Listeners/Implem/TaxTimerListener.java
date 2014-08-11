package com.github.InspiredOne.InspiredNationsClient.Listeners.Implem;

import java.rmi.RemoteException;

import org.bukkit.event.EventHandler;

import com.github.InspiredOne.InspiredNationsClient.Listeners.InspiredListener;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.TaxTimerEvent;

public class TaxTimerListener<T extends TaxTimerManager<?>> extends InspiredListener<T> {

	public TaxTimerListener(T manager) {
		super(manager);
	}
	
	@EventHandler
	public void onTaxTimerEvent(TaxTimerEvent event) throws RemoteException {
		if(manager.getActionMenu().PDI.getTimerState()) {
			this.manager.Update();
		}
	}
}
