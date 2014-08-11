package com.github.InspiredOne.InspiredNationsServer.Economy;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.InspiredOne.InspiredNationsServer.Config;
import com.github.InspiredOne.InspiredNationsServer.Debug;
import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;
import com.github.InspiredOne.InspiredNationsServer.PlayerData;
import com.github.InspiredOne.InspiredNationsServer.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNationsServer.Remotes.TaxTimerPortal;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging.Alert;

public class TaxTimer implements TaxTimerPortal, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7051455502418466458L;
	
	//PlayerData PDI;
	
	private int cycleLength;
	private int countdown;
	public boolean taxreadout = true;

	
	public TaxTimer() throws RemoteException {
		cycleLength = Config.taxtimertime;
		countdown = cycleLength;
		UnicastRemoteObject.exportObject(this, InspiredNationsServer.port);
		
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
		
		   final TimerTask timer = new TimerTask() {
			   public void run() { 
				   Debug.info("Printing out tax timer info: " + InspiredNationsServer.taxTimer.getFractionLeft());
				   for(ClientID client:InspiredNationsServer.clients) {
					   try {
						client.getClientPortal().triggerTaxTimer();
					} catch (RemoteException e) {
						// TODO Kill The Plugin?
						e.printStackTrace();
					}
				   }
				   double rand = 100.*Math.random();
					if(rand < Config.npcbuyprob) {
						for(PlayerData PDItemp:InspiredNationsServer.playerdata.values()) {
							try {
								PDItemp.getPlayer();
								for(NPC npc:PDItemp.npcs) {
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
	//					collectTaxes();
					}
				   
			   }
		   };
		   
		  Alert.scheduler.scheduleAtFixedRate(timer, 0, 1, TimeUnit.SECONDS);
	}
	
	public double getFractionLeft() {
		return ((double) countdown)/((double)cycleLength);
	}
	
	public void collectTaxes() {
		for(Class<? extends InspiredGov> govtype:InspiredNationsServer.global.getSubGovs()) {
			for(InspiredGov gov:InspiredNationsServer.global.getAllSubGovs(govtype)) {
				gov.payTaxes();
			}
		}
	}
}
