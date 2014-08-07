
package com.github.InspiredOne.InspiredNationsClient;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNationsClient.StartStop;
import com.github.InspiredOne.InspiredNationsClient.RemoteInterfaces.ClientPortalInter;
import com.github.InspiredOne.InspiredNationsClient.RemoteInterfaces.implem.ClientPortal;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.Currency;
import com.github.InspiredOne.InspiredNations.Economy.MarketPlace;
import com.github.InspiredOne.InspiredNations.Economy.MoneyExchange;
import com.github.InspiredOne.InspiredNations.Economy.NPC;
import com.github.InspiredOne.InspiredNations.Economy.TaxTimer;
import com.github.InspiredOne.InspiredNations.Economy.Implem.ItemMarketplace;
import com.github.InspiredOne.InspiredNations.Economy.Implem.ItemSellable;
import com.github.InspiredOne.InspiredNations.Exceptions.PlayerOfflineException;
import com.github.InspiredOne.InspiredNations.Governments.GlobalGov;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Governments.Implem.ChestShop;
import com.github.InspiredOne.InspiredNations.Regions.Implem.ShopRegion;
import com.github.InspiredOne.InspiredNations.RemoteInterfaces.ServerPortalInter;
import com.github.InspiredOne.InspiredNations.ToolBox.IndexedMap;
import com.github.InspiredOne.InspiredNations.ToolBox.MultiGovMap;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNations.ToolBox.Point3D;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuAlert;

public class InspiredNationsClient extends JavaPlugin {

	public static ClientPortal client = new ClientPortal();
	public static ServerPortalInter server;
	public static InspiredNationsClient plugin = (InspiredNationsClient) Bukkit.getPluginManager().getPlugin("InspiredNations");
	public Logger logger = Logger.getLogger("Minecraft"); // Variable to communicate with console
	private StartStop SS = new StartStop(this); // Deals with start-up and shut-down
	public static MultiGovMap regiondata = new MultiGovMap(); 
	public static IndexedMap<PlayerID, PlayerData> playerdata = new IndexedMap<PlayerID, PlayerData>();
	public static MoneyExchange Exchange = new MoneyExchange();
	public static List<MarketPlace<?>> Markets = new ArrayList<MarketPlace<?>>(); 
	public static GlobalGov global;
	public static TaxTimer taxTimer;
	public TempCommandListener CM = new TempCommandListener(this);
	public TempPlayerListener PL = new TempPlayerListener(this);
	public static ArrayList<String> check = new ArrayList<String>();
	
	public void onEnable() {

		InspiredNationsClient.plugin = this;
        String name = "portal";
        Registry registry;
		try {
			registry = LocateRegistry.getRegistry("localhost", 0);
	        InspiredNationsClient.server = (ServerPortalInter) registry.lookup(name);
		} catch (RemoteException | NotBoundException e) {
			System.out.print("Could not connect to InspiredNations Server. Disabling plugin.");
			Bukkit.getPluginManager().disablePlugin(this);
			e.printStackTrace();
		}

        
        
		taxTimer = new TaxTimer();
		PluginManager pm = this.getServer().getPluginManager();
		SS.Start();
		if(InspiredNationsClient.Markets.isEmpty()) {
			InspiredNationsClient.Markets.add(new ItemMarketplace());
		}
		taxTimer.setCycleLength(InspiredNationsClient.plugin.getConfig().getInt("tax_cycle_length"));
		InspiredNationsClient.Exchange.registerCurrency(Currency.DEFAULT, new BigDecimal(500));
		pm.registerEvents(PL, this);
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
		this.getCommand("hud").setExecutor(CM);
		this.getCommand("map").setExecutor(CM);
		this.getCommand("npc").setExecutor(CM);
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
		for(RegisteredListener handl:HandlerList.getRegisteredListeners(InspiredNationsClient.plugin)) {
			Debug.print(handl.getListener());
		}
		Debug.print(HandlerList.getRegisteredListeners(InspiredNationsClient.plugin).size() + "= handlerlist size");
		SS.Stop();
	}
	
	public class TempPlayerListener implements Listener {
		
		InspiredNationsClient plugin;
		
		public TempPlayerListener(InspiredNationsClient instance) {
			plugin = instance;
		}
		
		@EventHandler
		public void onPlayerJoin(PlayerJoinEvent event) {
			if(!InspiredNationsClient.playerdata.containsKey(new PlayerID(event.getPlayer()))) {
				PlayerID ID = new PlayerID(event.getPlayer());
				InspiredNationsClient.playerdata.put(ID, new PlayerData(ID));
				System.out.println("Player has not been added to playerdata yet");
			}
		}
		@EventHandler
		public void onPlayerLeave(PlayerKickEvent event) {
			PlayerID id = new PlayerID(event.getPlayer());
			PlayerData.unRegister(id);
		}
		@EventHandler
		public void onPlayerLeave(PlayerQuitEvent event) {
			PlayerID id = new PlayerID(event.getPlayer());
			PlayerData.unRegister(id);
		}
		@EventHandler
		public void onPlayerChat(AsyncPlayerChatEvent event) {
			String msg = event.getMessage();
			event.setCancelled(true);
			InspiredNationsClient.playerdata.get(new PlayerID(event.getPlayer())).getMsg().sendChatMessage(msg);;
		}
		@EventHandler
		public void onPlayerMove(PlayerMoveEvent event) {
			PlayerData PDI = InspiredNationsClient.playerdata.get(new PlayerID(event.getPlayer()));
			PDI.setLocation(event.getTo());
			if(!event.getTo().getBlock().equals(event.getFrom())) {
				for(InspiredGov gov:InspiredNationsClient.regiondata) {
					if(gov.contains(event.getTo()) && !gov.contains(event.getFrom())) {
						PDI.sendNotification(MenuAlert.WELCOME_TO_GOV(gov));
					}
				}
				for(InspiredGov gov:InspiredNationsClient.regiondata) {
					if(!gov.contains(event.getTo()) && gov.contains(event.getFrom())) {
						PDI.sendNotification(MenuAlert.GOODBYE_TO_GOV(gov));
					}
				}
			}
		}
		@EventHandler
		public void onPlayerBreakBlock(BlockBreakEvent event) {
			Debug.print("Player is breaking block.");
			PlayerData player = InspiredNationsClient.playerdata.get(new PlayerID(event.getPlayer()));
			if(!player.getAllowedInteract(event.getBlock().getLocation())) {
				Debug.print(player.getName() + " is trying to break a block s/he is not allowed to.");
				event.setCancelled(true);
			}
		}
		@EventHandler
		public void onPlayerHurtPlayer(EntityDamageByEntityEvent event) {
			if(event.getDamager() instanceof Player) { 
				if(event.getEntity() instanceof Player) {
					if(!new PlayerID((Player) event.getDamager()).getPDI().getAllowedHurt(new PlayerID((Player) event.getEntity()).getPDI())) {
						event.setCancelled(true);
					}
				}
			}
		}
		@EventHandler
		public void onPlayerInteractEvent(PlayerInteractEvent event) {
			PlayerData player = InspiredNationsClient.playerdata.get(new PlayerID(event.getPlayer()));
			if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if(!player.getAllowedInteract(event.getClickedBlock().getLocation())) {
					Debug.print(player.getName() + " is trying to break a block s/he is not allowed to.");
					event.setCancelled(true);
				}
			}
		}
		private HashMap<PlayerID, ChestShop> eventChestShop = new HashMap<PlayerID, ChestShop>();
		private HashMap<PlayerID, Chest> eventChestHash = new HashMap<PlayerID, Chest>();
		private HashMap<PlayerID, Location> eventLocationHash = new HashMap<PlayerID, Location>();
		private HashMap<PlayerID, Inventory> eventInvHash = new HashMap<PlayerID, Inventory>();
		private HashMap<PlayerID, ItemStack[]> contentsHash = new HashMap<PlayerID, ItemStack[]>();
		private HashMap<Point3D, Boolean> chestCheck = new HashMap<Point3D, Boolean>();
		private HashMap<PlayerID, Point3D> eventPoint3D = new HashMap<PlayerID, Point3D>();

		public Inventory getSellablesInv(Inventory inv, ChestShop chest,
				PlayerID player1) {
			System.out.println("Test");
			Inventory tempInv = inv;
			tempInv.clear();
			for (ItemSellable item1 : chest.getItems()) {
				ItemStack item = item1.getItem();
				System.out.println("This loop works");
				Debug.print("3");
				ItemMeta itemMeta = item.getItemMeta();
				itemMeta.setDisplayName(ChatColor.DARK_GREEN
						+ item1.getName());
				item.setItemMeta(itemMeta);
				tempInv.addItem(item);

			}
			return tempInv;
		}

		@EventHandler
		public void onOpenInventoryEvent(PlayerInteractEvent event) {
			Player player1 = (Player) event.getPlayer();
			PlayerID player = new PlayerID(event.getPlayer());
			if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
					&& event.getClickedBlock().getType().equals(Material.CHEST)) {
				for (InspiredGov gov : InspiredNationsClient.regiondata
						.get(ChestShop.class)) {
					Chest chest = (Chest) event.getClickedBlock().getState();
					if (chest.getBlock().getType().equals(Material.CHEST)) {
						Location chestLocation = event.getClickedBlock()
								.getLocation();
						if (gov.getRegion().getRegion().contains(chestLocation)) {
							eventChestHash.put(player, chest);
							eventLocationHash.put(player, chestLocation);
							ShopRegion region = ((ShopRegion) gov.getRegion()
									.getRegion());
							Point3D chest1 = region.one;
							if (!chestCheck.containsKey(chest1)) {
								eventPoint3D.put(player, chest1);
								event.setCancelled(true);
								ChestShop chestShop = (ChestShop) gov;
								eventChestShop.put(player, chestShop);
								Inventory inv = (Inventory) ((Chest) chest1
										.getLocation().getBlock().getState())
										.getInventory();
								InventoryHolder ih = chest.getInventory()
										.getHolder();
								if (ih instanceof Chest) {
									Inventory tempInv = Bukkit.createInventory(
											player1, 27, ChatColor.DARK_RED
													+ gov.getName());
									contentsHash.put(player, inv.getContents());

									chestCheck.put(chest1, true);
									for (PlayerID checkPlayer : chestShop
											.getOwners()) {
										if (player.equals(checkPlayer)) {
											tempInv.setContents(contentsHash.get(player));
											eventInvHash.put(player, tempInv);
											player1.openInventory(tempInv);
											break;
										} else {
											tempInv.setContents(this.getSellablesInv(inv,
													chestShop, player).getContents());
											eventInvHash.put(player, tempInv);
											player1.openInventory(tempInv);
										}
									}
									System.out.println("THIS BETTER WORK");

									break;
								} else if (ih instanceof DoubleChest) {
									DoubleChest dc = (DoubleChest) ih;
									Inventory tempInv = Bukkit.createInventory(
											player1, 54, ChatColor.DARK_GREEN
													+ gov.getName());
									contentsHash.put(player, dc.getInventory()
											.getContents());
									tempInv.setContents(contentsHash
											.get(player));
									eventInvHash.put(player, tempInv);
									player1.openInventory(tempInv);
									break;

								}
							} else {
								player1.sendMessage("A Player is already accessing this chest!");
								event.setCancelled(true);
								break;
							}
						}
					}
				}
			}
		}

		@EventHandler
		public void onPlayerCloseInventoryEvent(InventoryCloseEvent event) {
			PlayerID player = new PlayerID((Player) event.getPlayer());
			if (event.getInventory().equals(eventInvHash.get(player))) {
				for (PlayerID checkPlayer : eventChestShop.get(player)
						.getOwners()) {
					if (player.equals(checkPlayer)) {
						contentsHash.put(player, eventInvHash.get(player)
								.getContents());
						eventChestHash.get(player).getInventory()
								.setContents(contentsHash.get(player));
						chestCheck.remove(eventPoint3D.get(player));
					} else {
						chestCheck.remove(eventPoint3D.get(player));					}
				}
			}
		}
	}
	
	
	public class TempCommandListener implements CommandExecutor {

		InspiredNationsClient plugin;
		
		public TempCommandListener(InspiredNationsClient instance) {
			plugin = instance;
		}
		
		@Override
		public boolean onCommand(CommandSender sender, Command arg1, String CommandLable,
				String[] arg3) {
			if(!(sender instanceof Player)) {
				plugin.logger.info("HUD cannot be called from console.");
				return false;
			}
			
			PlayerData PDI = InspiredNationsClient.playerdata.get(new PlayerID((Player) sender));
			if (CommandLable.equalsIgnoreCase("hud")) {
				if(((Player) sender).isConversing()) {
					return false;
				}
				ConversationBuilder convo = new ConversationBuilder(PDI);
				Conversation conversation = convo.HudConvo();
				PDI.setCon(conversation);
				conversation.begin();
			}
			else if(CommandLable.equalsIgnoreCase("map")) {
				if(((Player) sender).isConversing()) {
					return false;
				}
				ConversationBuilder convo = new ConversationBuilder(PDI);
				Conversation conversation = convo.MapConvo();
				PDI.setCon(conversation);
				conversation.begin();
			}
			else if(CommandLable.equalsIgnoreCase("npc")) {
				for(NPC npc:PDI.npcs) {
					npc.buyOut();
				}
			}
			else return false;
			return false;
		}
		
	}
	
	public static PlayerData getTestingPlayer() {
		return InspiredNationsClient.playerdata.get(InspiredNationsClient.playerdata.getIndex(0));
	}
}
