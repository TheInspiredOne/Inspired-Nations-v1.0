package com.github.InspiredOne.InspiredNationsClient;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.InspiredOne.InspiredNationsClient.Exceptions.PlayerOfflineException;
import com.github.InspiredOne.InspiredNationsClient.Remotes.ClientPortalInter;
import com.github.InspiredOne.InspiredNationsClient.Remotes.Implem.ClientPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.ServerPortalInter;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.IndexedMap;

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
    
    public TempPlayerListener pl = new TempPlayerListener();
    public TempCommandListener cm = new TempCommandListener();
    public static IndexedMap<PlayerID, ClientPlayerData> playerdata = new IndexedMap<PlayerID, ClientPlayerData>();
	
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
            registry.rebind(id.getName() + "portal", stub);
            logger.info("Client Portal Bound: " + id.getName());
        } catch (Exception e) {
			logger.info("Could not bind client portal: " + id.getName());
			Bukkit.getPluginManager().disablePlugin(this);
        }
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(pl, this);
		this.getCommand("hud").setExecutor(cm);
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
			registry.unbind(id.getName()+"portal");
			UnicastRemoteObject.unexportObject(registry, true);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		try {
			server.unregisterClient(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		logger.info(pdf.getName() + " version " + pdf.getVersion() + " has been Disabled.");
	}
	
	public class TempPlayerListener implements Listener {
		
		@EventHandler
		public void onPlayerJoin(PlayerJoinEvent event) throws PlayerOfflineException {
			PlayerID ID = new PlayerID(event.getPlayer());
			if(!InspiredNationsClient.playerdata.containsKey(ID)) {
				try {
					InspiredNationsClient.playerdata.put(ID, new ClientPlayerData(ID));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public class TempCommandListener implements CommandExecutor {
		
		@Override
		public boolean onCommand(CommandSender sender, Command arg1, String CommandLable,
				String[] arg3) {
			if(!(sender instanceof Player)) {
				InspiredNationsClient.logger.info("HUD cannot be called from console.");
				return false;
			}
			
			PlayerDataPortal PDI = null;
			try {
				PDI = InspiredNationsClient.server.getPlayer(new PlayerID((Player) sender));
			} catch (RemoteException e) {
				// TODO Kill Plugin
				e.printStackTrace();
			}
			if (CommandLable.equalsIgnoreCase("hud")) {
				if(((Player) sender).isConversing()) {
					return false;
				}
				ConversationBuilder convo = new ConversationBuilder(PDI);
				Conversation conversation = null;
				try {
					conversation = convo.HudConvo();
				} catch (RemoteException e) {
					// TODO Kill Plugin
					e.printStackTrace();
				}
				InspiredNationsClient.playerdata.get(new PlayerID((Player) sender)).setCon(conversation);
				conversation.begin();
			}
/*			else if(CommandLable.equalsIgnoreCase("map")) {
				if(((Player) sender).isConversing()) {
					return false;
				}
				ConversationBuilder convo = new ConversationBuilder(PDI);
				Conversation conversation = convo.MapConvo();
				PDI.setCon(conversation);
				conversation.begin();
			}*/
/*			else if(CommandLable.equalsIgnoreCase("npc")) {
				for(NPC npc:PDI.npcs) {
					npc.buyOut();
				}
			}*/
			else return false;
			return false;
		}
		
	}
}
