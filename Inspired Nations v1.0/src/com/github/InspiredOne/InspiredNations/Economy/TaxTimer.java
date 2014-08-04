
package com.github.InspiredOne.InspiredNations.Economy;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.ToolBox.Config;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;

public class TaxTimer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7051455502418466458L;
	
	//PlayerData PDI;
	
	private int cycleLength;
	private int countdown;
	public boolean taxreadout = true;

	
	public TaxTimer(){
		cycleLength = Config.taxtimertime;
		countdown = cycleLength;
		
	}
	
	public int getCycleLength() {
		return this.cycleLength;
	}
	
	public void setCycleLength(int cycle) {
		this.cycleLength = cycle;
	}

	public int getSecondsLeft() {
		return (int) Math.floor(this.cycleLength*this.getFractionLeft());
	}
	
	public String getTimeLeftReadout() {
		int secondsLeft = this.getSecondsLeft();
		int hours = secondsLeft/3600;
		secondsLeft = secondsLeft - hours*3600;
		int minutes = secondsLeft/60;
		secondsLeft = secondsLeft - minutes*60;
		int seconds = secondsLeft;
		
		String output = "";
		output = hours + ":" + minutes + ":" + seconds;
		return output;
		
	}
	
	public void startTimer() {
		
		new BukkitRunnable() {

			@Override
			public void run() {
				TaxTimerEvent event = new TaxTimerEvent(InspiredNations.taxTimer);
				if(taxreadout) {
					Bukkit.getServer().getPluginManager().callEvent(event);
				}
				double rand = 100.*Math.random();
				if(rand < Config.npcbuyprob) {
					for(PlayerID PDItemp:InspiredNations.playerdata) {
						try {
							PDItemp.getPDI().getPlayer();
							for(NPC npc:PDItemp.getPDI().npcs) {
								npc.buyOut();
							}	
						}
						catch (Exception ex) {
							
						}
					}
				}
				countdown--;
				if(countdown == 0) {
					countdown = cycleLength;
					collectTaxes();
				}
			}
			
		}.runTaskTimer(InspiredNations.plugin, 0, 20);		
	}
	
	public double getFractionLeft() {
		return ((double) countdown)/((double)cycleLength);
	}
	
	public void collectTaxes() {
		for(Class<? extends InspiredGov> govtype:InspiredNations.global.getSubGovs()) {
			for(InspiredGov gov:InspiredNations.global.getAllSubGovs(govtype)) {
				gov.payTaxes();
			}
		}
	}
}
