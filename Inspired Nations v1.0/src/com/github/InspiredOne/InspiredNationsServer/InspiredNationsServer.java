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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.github.InspiredOne.InspiredNationsServer.Economy.MarketPlace;
import com.github.InspiredOne.InspiredNationsServer.Economy.MoneyExchange;
import com.github.InspiredOne.InspiredNationsServer.Economy.Implem.ItemMarketplace;
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
	public static MoneyExchange Exchange = new MoneyExchange();
	public static MultiGovMap regiondata = new MultiGovMap(); 
	public static List<MarketPlace<?>> Markets = new ArrayList<MarketPlace<?>>();
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// Attach a shutdown hook for saving data.
		Runtime.getRuntime().addShutdownHook(new Thread() {
				  @Override
				  public void run() {
						// Saves Data
						try {
							  File theDir = new File(System.getProperty("user.dir") + "/InspiredNations");
							  // if the directory does not exist, create it
							  if (!theDir.exists()) {
								  try{
									  theDir.mkdir();
							     } catch(SecurityException se){
							     }        
							  }
							File regionfile = new File(System.getProperty("user.dir") + "/InspiredNations", "data.yml");
					        FileOutputStream regionOut = new FileOutputStream(regionfile);
					        ObjectOutputStream rout = new ObjectOutputStream(regionOut);
					        rout.writeObject(InspiredNationsServer.regiondata);
					        rout.writeObject(InspiredNationsServer.playerdata);
					        rout.writeObject(InspiredNationsServer.Exchange);
					        //rout.writeObject(InspiredNations.taxTimer);
					        rout.close();
					        regionOut.close();
						}
						catch(Exception ex) {
							ex.printStackTrace();
						}
				  }
			  });
		
		try {
            Registry registry = null;
            try {
                registry = LocateRegistry.createRegistry(port);
            } catch (RemoteException e) {
                registry = LocateRegistry.getRegistry(hostname, port);
                e.printStackTrace();
            }
            
            ServerPortalInter portal = new ServerPortal();
            ServerPortalInter stub = (ServerPortalInter) UnicastRemoteObject.exportObject(portal, 0);
            registry.rebind("portal", stub);
            Log.info("Server Portal Bound");
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
		// Loads Data
		try {
			File regionfile = new File(System.getProperty("user.dir")+ "/InspiredNations", "data.yml");
	        FileInputStream regionIn = new FileInputStream(regionfile);
	        ObjectInputStream rin = new ObjectInputStream(regionIn);
	        InspiredNationsServer.regiondata = (MultiGovMap) rin.readObject();
	        InspiredNationsServer.playerdata = (IndexedMap<PlayerID, PlayerData>) rin.readObject();
	        InspiredNationsServer.Exchange = (MoneyExchange) rin.readObject();
	        //InspiredNations.taxTimer = (TaxTimer) rin.readObject();
	        rin.close();
	        regionIn.close();
		}
		catch(Exception ex) {
			
		}
		if(InspiredNationsServer.Markets.isEmpty()) {
			InspiredNationsServer.Markets.add(new ItemMarketplace());
		}
		
		
		
		  int delay = 1000; //milliseconds

		  TimerTask task = new TimerTask() {

			@Override
			public void run() {
				for(ClientID client:clients) {
					client.getClientPortal();
				}				
			}
			  
		  };
		  Timer timer = new Timer();
		  timer.scheduleAtFixedRate(task, 5000, delay);
		  
	}
}
