package com.github.InspiredOne.InspiredNationsClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.InspiredOne.InspiredNationsServer.Remotes.ServerPortalInter;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;

public class InspiredNationsClient extends JavaPlugin {

	public static ServerPortalInter server;
	public static String hostname = "localhost";
	public static int port = 1099;
	
	public void onEnable() {

		try {
	        String name = "portal";
	        Registry registry;
			registry = LocateRegistry.getRegistry("localhost", 0);
	        InspiredNationsClient.server = (ServerPortalInter) registry.lookup(name);
	        InspiredNationsClient.server.registerClient(new ClientID(hostname, port));
			System.out.print("Inspired Nations Enabled");
		} catch (RemoteException | NotBoundException e) {
			System.out.print("Could not connect to InspiredNations Server. Disabling plugin.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		

	}
	
	public void onDisable() {
		System.out.print("Inspired Nations Disabled");
	}
}
