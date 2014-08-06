package com.github.InspiredOne.InspiredNationsClient;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.RMI.Config.ConfigInter;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNationsClient.ConfigPorts.Implem.Config;

public class StartStop {

	// Grabbing the instance of the plugin
	InspiredNationsClient plugin;
	
	public StartStop(InspiredNationsClient instance) {
		plugin = instance;
	}
	
	public void Start() {
		// Start This RMI server
        try {
            Registry registry = null;
            try {
                registry = LocateRegistry.createRegistry(1099);
            } catch (RemoteException e) {
                registry = LocateRegistry.getRegistry(1099);
                e.printStackTrace();
            }
            
            ConfigInter config = new Config();
            ConfigInter stub = (ConfigInter) UnicastRemoteObject.exportObject(config, 0);
            registry.rebind(Config.name, stub);
            plugin.getLogger().info("Config Bound");
            
        } catch (Exception e) {
        	plugin.getLogger().info("Config Exception");
            e.printStackTrace();
        }
		
		
		
		
		
		PluginDescriptionFile pdf = plugin.getDescription();
		plugin.getLogger().info(pdf.getName() + " version " + pdf.getVersion() + " has been enabled.");
		
		// Handles config.yml
		FileConfiguration config = plugin.getConfig(); 
		config.options().copyDefaults(true);
		if (!(new File(plugin.getDataFolder() + config.getName()).exists())) {
			plugin.saveDefaultConfig();
		}
		
		// Handles online players
		for(Player player:plugin.getServer().getOnlinePlayers()) {
			PlayerID ID = new PlayerID(player);
			if(!InspiredNationsClient.playerdata.containsKey(ID)) {
				InspiredNationsClient.playerdata.put(ID, new PlayerData(ID));
			}
		}
	}
	
	public void Stop() {
		
		try {
			Collection<? extends Player> online = plugin.getServer().getOnlinePlayers();
			for (Player player:online) {
				PlayerID onlineP = new PlayerID(player);
				PlayerData.unRegister(onlineP);
			}
		}
		catch (Exception ex) {	
		}
		
		PluginDescriptionFile pdf = plugin.getDescription();
		plugin.getLogger().info(pdf.getName() + " version " + pdf.getVersion() + " has been disabled.");
	}
}
