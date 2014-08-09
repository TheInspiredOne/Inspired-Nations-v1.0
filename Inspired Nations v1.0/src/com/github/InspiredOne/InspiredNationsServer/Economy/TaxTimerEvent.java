package com.github.InspiredOne.InspiredNationsServer.Economy;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TaxTimerEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private TaxTimer timer;
	public TaxTimerEvent(TaxTimer timer) {
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
	
	public int getTimeRemaining() {
		return (int) Math.floor(timer.getFractionLeft()*timer.getCycleLength());
	}

}
