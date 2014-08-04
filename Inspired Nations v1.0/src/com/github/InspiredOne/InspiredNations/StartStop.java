package com.github.InspiredOne.InspiredNations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.instrument.Instrumentation;
import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.Economy.Currency;
import com.github.InspiredOne.InspiredNations.Economy.MoneyExchange;
import com.github.InspiredOne.InspiredNations.Economy.NPC;
import com.github.InspiredOne.InspiredNations.Economy.TaxTimer;
import com.github.InspiredOne.InspiredNations.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNations.ToolBox.IndexedMap;
import com.github.InspiredOne.InspiredNations.ToolBox.MultiGovMap;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;


public class StartStop {

	// Grabbing the instance of the plugin
	InspiredNations plugin;
	static Instrumentation inst ;
	
	public StartStop(InspiredNations instance) {
		plugin = instance;
	}
	
	// Handles Start-up of plugin
	@SuppressWarnings("unchecked")
	public void Start() {
		

		
		// Loads Data
		try {
			File regionfile = new File(System.getProperty("user.dir"), "regiondata.yml");
	        FileInputStream regionIn = new FileInputStream(regionfile);
	        ObjectInputStream rin = new ObjectInputStream(regionIn);
	        InspiredNations.regiondata = (MultiGovMap) rin.readObject();
	        InspiredNations.playerdata = (IndexedMap<PlayerID, PlayerData>) rin.readObject();
	        InspiredNations.Exchange = (MoneyExchange) rin.readObject();
	        InspiredNations.taxTimer = (TaxTimer) rin.readObject();
	        rin.close();
	        regionIn.close();
		}
		catch(Exception ex) {
			
		}
		
		// Starts up the TaxTimer
		InspiredNations.taxTimer.startTimer();
		
	}
	
	// Handles Shut-down of plugin
	public void Stop() {
		//TODO Delete this. It resets the NPC money every time the server is reloaded
		
		for(PlayerData PDI:InspiredNations.playerdata.values()) {
			for(NPC npc:PDI.npcs) {
				try {
					npc.addMoney(new BigDecimal(100), Currency.DEFAULT);
				} catch (NegativeMoneyTransferException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		// End of things to delete

			
		// Saves Data
		try {
			File regionfile = new File(System.getProperty("user.dir"), "regiondata.yml");
	        FileOutputStream regionOut = new FileOutputStream(regionfile);
	        ObjectOutputStream rout = new ObjectOutputStream(regionOut);
	        rout.writeObject(InspiredNations.regiondata);
	        rout.writeObject(InspiredNations.playerdata);
	        rout.writeObject(InspiredNations.Exchange);
	        rout.writeObject(InspiredNations.taxTimer);
	        rout.close();
	        regionOut.close();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
