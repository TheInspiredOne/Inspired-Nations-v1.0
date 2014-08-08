package com.github.InspiredOne.InspiredNationsClient.Listeners;

import org.bukkit.event.Listener;

import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataInter;

public class InspiredListener<T extends ActionManager<?>> implements Listener {

	protected T manager;
	
	public InspiredListener(T manager) {
		this.setManager(manager);
	}
	
	public PlayerDataInter getPlayerData() {
		return this.manager.getPlayerData();
	}

	public T getManager() {
		return manager;
	}

	public void setManager(T manager) {
		this.manager = manager;
	}

}
