package com.github.InspiredOne.InspiredNations.Listeners;

import org.bukkit.event.Listener;

import com.github.InspiredOne.InspiredNations.PlayerData;

public class InspiredListener<T extends ActionManager<?>> implements Listener {

	protected T manager;
	
	public InspiredListener(T manager) {
		this.setManager(manager);
	}
	
	public PlayerData getPlayerData() {
		return this.manager.getPlayerData();
	}

	public T getManager() {
		return manager;
	}

	public void setManager(T manager) {
		this.manager = manager;
	}

}
