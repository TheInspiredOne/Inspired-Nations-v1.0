package com.github.InspiredOne.InspiredNationsClient.ToolBox;

import java.rmi.RemoteException;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.github.InspiredOne.InspiredNationsServer.Remotes.TaxTimerPortal;

public class TaxTimerEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private TaxTimerPortal timer;
	public TaxTimerEvent(TaxTimerPortal timer) {
		this.timer = timer;
	}

	public TaxTimerEvent(boolean isAsync) {
		super(isAsync);
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
        return handlers;
    }
	
	public int getTimeRemaining() throws RemoteException {
		return (int) Math.floor(timer.getFractionLeft()*timer.getCycleLength());
	}

}
