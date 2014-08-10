package com.github.InspiredOne.InspiredNationsClient.Listeners.Implem;

import java.rmi.RemoteException;

import org.bukkit.event.EventHandler;

import com.github.InspiredOne.InspiredNationsClient.HUD.MenuUpdateEvent;
import com.github.InspiredOne.InspiredNationsClient.Listeners.ActionManager;
import com.github.InspiredOne.InspiredNationsClient.Listeners.InspiredListener;

public class MenuUpdateListener<T extends ActionManager<?>> extends InspiredListener<T> {

	public MenuUpdateListener(T manager) {
		super(manager);
	}

	@EventHandler
	public void onMenuUpdate(MenuUpdateEvent event) throws RemoteException {
		if(event.getTarget().equals(manager.getPlayerData().getPlayerID())) {
			this.getManager().Update();;
		}
	}
}
