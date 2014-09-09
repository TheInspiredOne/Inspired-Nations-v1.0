package com.github.InspiredOne.InspiredNationsServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.github.InspiredOne.InspiredNationsServer.Economy.Currency;
import com.github.InspiredOne.InspiredNationsServer.Economy.MarketPlace;
import com.github.InspiredOne.InspiredNationsServer.Economy.MoneyExchange;
import com.github.InspiredOne.InspiredNationsServer.Economy.TaxTimer;
import com.github.InspiredOne.InspiredNationsServer.Economy.Implem.ItemMarketplace;
import com.github.InspiredOne.InspiredNationsServer.Governments.GlobalGov;
import com.github.InspiredOne.InspiredNationsServer.Governments.GovFactory;
import com.github.InspiredOne.InspiredNationsServer.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNationsServer.Remotes.ServerPortalInter;
import com.github.InspiredOne.InspiredNationsServer.Remotes.Implem.ServerPortal;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.IndexedMap;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.MultiGovMap;

public class InspiredNationsServer {

	public static ArrayList<ClientID> clients = new ArrayList<ClientID>();
	public static String hostname = "localhost";
	public static int port = 1099;

	public static IndexedMap<PlayerID, PlayerData> playerdata = new IndexedMap<PlayerID, PlayerData>();
	public static MoneyExchange Exchange;
	public static GlobalGov global;
	public static MultiGovMap regiondata = new MultiGovMap();
	public static TaxTimer taxTimer;
	public static List<MarketPlace<?>> Markets = new ArrayList<MarketPlace<?>>();

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws RemoteException {
		// Attach a shutdown hook for saving data.
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// Saves Data
				try {
					File theDir = new File(System.getProperty("user.dir")
							+ "/InspiredNations");
					// if the directory does not exist, create it
					if (!theDir.exists()) {
						try {
							theDir.mkdir();
						} catch (SecurityException se) {
						}
					}
					File regionfile = new File(System.getProperty("user.dir")
							+ "/InspiredNations", "data.yml");
					FileOutputStream regionOut = new FileOutputStream(
							regionfile);
					ObjectOutputStream rout = new ObjectOutputStream(regionOut);
					rout.writeObject(InspiredNationsServer.regiondata);
					rout.writeObject(InspiredNationsServer.playerdata);
					rout.writeObject(InspiredNationsServer.Exchange);
					rout.writeObject(InspiredNationsServer.taxTimer);
					rout.close();
					regionOut.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		// Stores the ServerPortal in the registry
		try {
			Registry registry = null;
			try {
				registry = LocateRegistry.createRegistry(port);
			} catch (RemoteException e) {
				registry = LocateRegistry.getRegistry(hostname, port);
				e.printStackTrace();
			}

			ServerPortalInter portal = new ServerPortal();
			ServerPortalInter stub = (ServerPortalInter) UnicastRemoteObject
					.exportObject(portal, port);
			registry.rebind("portal", stub);
			Log.info("Server Portal Bound");
		} catch (Exception e) {
			e.printStackTrace();
		}
		taxTimer = new TaxTimer();
		Exchange = new MoneyExchange();
		// Loads Data
		try {
			File regionfile = new File(System.getProperty("user.dir")
					+ "/InspiredNations", "data.yml");
			FileInputStream regionIn = new FileInputStream(regionfile);
			ObjectInputStream rin = new ObjectInputStream(regionIn);
			InspiredNationsServer.regiondata = (MultiGovMap) rin.readObject();
			InspiredNationsServer.playerdata = (IndexedMap<PlayerID, PlayerData>) rin
					.readObject();
			InspiredNationsServer.Exchange = (MoneyExchange) rin.readObject();
			InspiredNationsServer.taxTimer = (TaxTimer) rin.readObject();
			rin.close();
			regionIn.close();
		} catch (Exception ex) {

		}
		if (Currency.DEFAULT == null) {
			Currency.DEFAULT = new Currency("Coin");
		}
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
		System.out.println(">>>> Governments <<<<");
		for(List<InspiredGov> set:regiondata.values()) {
			for(Iterator<InspiredGov> iter = set.iterator(); iter.hasNext();){
				InspiredGov gov = iter.next();
				System.out.println(gov.getName());
			}
		}
		System.out.println(">>>> Players <<<<");
		for(PlayerID player:playerdata.keySet()) {
			System.out.println(player);
		}
		
		taxTimer.startTimer();
		if (InspiredNationsServer.Markets.isEmpty()) {
			InspiredNationsServer.Markets.add(new ItemMarketplace());
		}
		int delay = 1000; // milliseconds

		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				for (ClientID client : clients) {
					client.getClientPortal();

				}
			}

		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 5000, delay);

	}
}
