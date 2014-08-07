package com.github.InspiredOne.InspiredNationsServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.github.InspiredOne.InspiredNationsServer.Remotes.ServerPortalInter;
import com.github.InspiredOne.InspiredNationsServer.Remotes.Implem.ServerPortal;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;

public class InspiredNationsServer {

	public static ArrayList<ClientID> clients = new ArrayList<ClientID>();
	public static String hostname = "localhost";
	public static int port = 1099;
	
	public static void main(String[] args) {
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
            System.out.print("Server Portal Bound");
        } catch (Exception e) {
        	e.printStackTrace();
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
		  new Timer().scheduleAtFixedRate(task, 5000, delay);
	}

}
