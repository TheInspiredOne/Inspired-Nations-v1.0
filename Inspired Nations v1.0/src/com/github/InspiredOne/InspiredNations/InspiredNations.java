
package com.github.InspiredOne.InspiredNations;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;

import com.github.InspiredOne.InspiredNations.Economy.Currency;
import com.github.InspiredOne.InspiredNations.Economy.MarketPlace;
import com.github.InspiredOne.InspiredNations.Economy.MoneyExchange;
import com.github.InspiredOne.InspiredNations.Economy.TaxTimer;
import com.github.InspiredOne.InspiredNations.Economy.Implem.ItemMarketplace;
import com.github.InspiredOne.InspiredNations.Governments.GlobalGov;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.RemoteInterfaces.ServerPortalInter;
import com.github.InspiredOne.InspiredNations.RemoteInterfaces.Implem.ServerPortal;
import com.github.InspiredOne.InspiredNations.ToolBox.Config;
import com.github.InspiredOne.InspiredNations.ToolBox.IndexedMap;
import com.github.InspiredOne.InspiredNations.ToolBox.MultiGovMap;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNations.ToolBox.ServerID;
import com.github.InspiredOne.InspiredNationsClient.InspiredNationsClient;
import com.github.InspiredOne.InspiredNationsClient.RemoteInterfaces.ClientPortalInter;

public class InspiredNations {
	
	public static ArrayList<ServerID> clients;
	public static ServerPortal server = new ServerPortal();
	public static InspiredNations plugin = (InspiredNations) Bukkit.getPluginManager().getPlugin("InspiredNations");
	private StartStop SS = new StartStop(this); // Deals with start-up and shut-down
	public static MultiGovMap regiondata = new MultiGovMap(); 
	public static IndexedMap<PlayerID, PlayerData> playerdata = new IndexedMap<PlayerID, PlayerData>();
	public static MoneyExchange Exchange = new MoneyExchange();
	public static List<MarketPlace<?>> Markets = new ArrayList<MarketPlace<?>>(); 
	public static GlobalGov global;
	public static TaxTimer taxTimer;
	public static ArrayList<String> check = new ArrayList<String>();
	
	public static void main(String[] args) {
		try {
            Registry registry = null;
            try {
                registry = LocateRegistry.createRegistry(1099);
            } catch (RemoteException e) {
                registry = LocateRegistry.getRegistry(1099);
                e.printStackTrace();
            }
            
            ServerPortalInter portal = InspiredNations.server;
            ServerPortalInter stub = (ServerPortalInter) UnicastRemoteObject.exportObject(portal, 0);
            registry.rebind("portal", stub);
            System.out.print("Portal Bound");
        } catch (Exception e) {
        	e.printStackTrace();
        }
		ArrayList<ServerID> clients = new ArrayList<ServerID>();
		plugin.onEnable();
	}
	
	public void onEnable() {
		Config.init();
		taxTimer = new TaxTimer();
		SS.Start();
		if(InspiredNations.Markets.isEmpty()) {
			InspiredNations.Markets.add(new ItemMarketplace());
		}
		taxTimer.setCycleLength(Config.taxtimertime);
		InspiredNations.Exchange.registerCurrency(Currency.DEFAULT, new BigDecimal(500));
		global = GovFactory.getGovInstance(GlobalGov.class);

		global.register();
		// if this is first time running plugin, then add the default globalgov to the regiondata
		// else, put the global gov loaded in the region data back into the global variable.
		if(regiondata.get(global.getClass()).isEmpty()) {
			regiondata.putValue(global.getClass(), global);
		}
		else {
			global = (GlobalGov) regiondata.get(global.getClass()).iterator().next();
		}
		global.setName("Global");
		for(List<InspiredGov> set:regiondata.values()) {
			for(Iterator<InspiredGov> iter = set.iterator(); iter.hasNext();){
				InspiredGov gov = iter.next();
				System.out.println(gov.getName());
			}
		}
		for(PlayerID player:playerdata.keySet()) {
			System.out.println(player);
		}

	}
	
	public void onDisable() {
		SS.Stop();
	}
	
	public static PlayerData getTestingPlayer() {
		return InspiredNations.playerdata.get(InspiredNations.playerdata.getIndex(0));
	}
}
