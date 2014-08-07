package com.github.InspiredOne.InspiredNationsClient;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.InspiredOne.InspiredNationsClient.Remotes.ClientPortalInter;
import com.github.InspiredOne.InspiredNationsClient.Remotes.Implem.ClientPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.ServerPortalInter;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;

public class InspiredNationsClient extends JavaPlugin {

	public static ServerPortalInter server;
	public static String hostname = "localhost";
	public static int port = 1100;
    private Registry registry = null;
    public static ClientID id = new ClientID(hostname, port);
    private ClientPortalInter portal;
	
	public static InspiredNationsClient plugin = (InspiredNationsClient) Bukkit.getPluginManager().getPlugin("InspiredNations");
	public static Logger logger; // Variable to communicate with console
    PluginDescriptionFile pdf;
	
	public void onEnable() {
		InspiredNationsClient.plugin = this;
		logger = plugin.getLogger();
		portal = new ClientPortal();
		pdf = plugin.getDescription();
		// Grabbing the InspiredNations Server Portal
		try {
	        String name = "portal";	
	        Registry registry;
			registry = LocateRegistry.getRegistry("localhost", 0);
	        InspiredNationsClient.server = (ServerPortalInter) registry.lookup(name);
	        InspiredNationsClient.server.registerClient(id);
	        
	        // Notify Console that plugin has been enabled successfully.

			logger.info(pdf.getName() + " version " + pdf.getVersion() + " has been enabled.");
		} catch (RemoteException | NotBoundException e) {
			logger.info("Could not connect to InspiredNations Server. Disabling plugin.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		// Binding the InspiredNations Client Portal
		try {

            try {
                registry = LocateRegistry.createRegistry(port);
            } catch (RemoteException e) {
                registry = LocateRegistry.getRegistry(hostname, port);
                //e.printStackTrace();
            }
            ClientPortalInter stub = (ClientPortalInter) UnicastRemoteObject.exportObject(portal, port);
            registry.rebind(ClientID.generateRegAddress(hostname, port) + "portal", stub);
            logger.info("Client Portal Bound: " + ClientID.generateRegAddress(hostname, port));
        } catch (Exception e) {
			e.printStackTrace();
			logger.info("Could not bind client portal.");
			//Bukkit.getPluginManager().disablePlugin(this);

        }
		

	}
	
	public void onDisable() {
		// Unexport the object
		try {
			UnicastRemoteObject.unexportObject(portal, true);
		} catch (NoSuchObjectException e1) {
			e1.printStackTrace();
		}
		// Unbind the Client portal and unexport the registry
		try {
			registry.unbind(ClientID.generateRegAddress(hostname, port)+"portal");
			UnicastRemoteObject.unexportObject(registry, true);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		try {
			server.unregsiterClient(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		logger.info(pdf.getName() + " version " + pdf.getVersion() + " has been Disabled.");
	}
}
