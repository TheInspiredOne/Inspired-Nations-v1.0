package com.github.InspiredOne.InspiredNationsClient.HUD;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;

public class MenuUpdateEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private PlayerID target;
	
	public MenuUpdateEvent(PlayerID player) {
		target = player;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
        return handlers;
    }

	public PlayerID getTarget() {
		return target;
	}

	public void setTarget(PlayerID target) {
		this.target = target;
	}
	
}
