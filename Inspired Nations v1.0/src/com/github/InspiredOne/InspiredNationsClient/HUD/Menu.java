package com.github.InspiredOne.InspiredNationsClient.HUD;

import java.rmi.RemoteException;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

import com.github.InspiredOne.InspiredNationsClient.InspiredNationsClient;
import com.github.InspiredOne.InspiredNationsClient.Exceptions.PlayerOfflineException;
import com.github.InspiredOne.InspiredNationsClient.HUD.Implem.MainHud;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataInter;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging.Alert;

public abstract class Menu extends MessagePrompt {

	// Conversation Persistent
	private String footer;
	public PlayerDataInter PDI;
	public InspiredNationsClient plugin;
	// Menu Persistent: Only initialized once for this menu instance.
	private boolean loaded = false;
	public HelpMenu help;
	
	// Non-Persistent: Refreshed for every return back to this menu
	
	
	public Menu(PlayerDataInter PDI) throws RemoteException {
		this.PDI = PDI;
		this.plugin = InspiredNationsClient.plugin;
		this.footer = MenuTools.addDivider("",PDI) + PDI.ENDINSTRU() + "Keywords: exit, chat, say, back, hud.";
	}
	/**
	 * When the menu is initialized, all options, tab options, tab selects, and text is
	 * written up. Menu persistent variables (such as action managers and state data) is loaded
	 * first. Non-persistent variables like options and tab selects and text is loaded second because
	 * they rely on valid persistent variables
	 * @throws RemoteException 
	 */
	public final void Initialize() throws RemoteException {
		// Initialize the Menu Persistent Variables To be used for Prompt Text
		this.loadMenuPersistent();
		// Initialize the Non-Persistent Variables To be used for Prompt Text
		this.loadNonPersistent();

	}
	/**
	 * Loads menu persistent variabls suce as Acton Managers and state data.
	 * @throws RemoteException 
	 */
	private final void loadMenuPersistent() throws RemoteException {
		if(!loaded) {
			//this.PDI.getMsg().clearMenuVisible();
			this.menuPersistent();
			loaded = true;
		}
	}
	private final void unloadMenuPersistent() throws RemoteException {
		this.unloadPersist();
		loaded = false;
	}
	private final void loadNonPersistent() throws RemoteException {
		this.unloadNonPersist();
		this.nonPersistent();
	}
	/**
	 * Used to unload the Menu, clearing all Non-Persistent Variables and unregistering events.
	 * @throws RemoteException 
	 */
	public abstract void unloadNonPersist() throws RemoteException;
	/**
	 * Used to unload the Menu, clearing both Non-Persistent and Persistent Variables. 
	 * @throws RemoteException 
	 */
	public abstract void unloadPersist() throws RemoteException;
	/**
	 * Used to set up menuPersistent variables such as ActionManagers
	 * @throws RemoteException 
	 */
	public abstract void menuPersistent() throws RemoteException;
	/**
	 * Used to set up nonPersistent variables such as options
	 * @throws RemoteException 
	 */
	public abstract void nonPersistent() throws RemoteException;
	
	public Menu getSelfPersist() {

		return this;
	}
	/**
	 * Unloads Menu Persistent Variables so that menu is completely refreshed.
	 * @return
	 * @throws RemoteException 
	 */
/*	public Menu getNewSelf() {
		this.unloadNonPersist();
		this.unloadMenuPersistent();
		return this;
	}*/
	
	public final boolean passBy() throws RemoteException {
		this.Initialize();
		return this.getPassBy();
	}
	
	@Override
	public final boolean blocksForInput(ConversationContext arg0) {
		try {
			return !this.passBy();
		} catch (RemoteException e) {
			// TODO Kill the Plugin
			e.printStackTrace();
			return true;
		}
	}
	
	@Override
	public final String getPromptText(ConversationContext arg0) {
		try {
			this.Initialize();
			return this.getPromptText();
		} catch (RemoteException e) {
			// TODO Kill the plugin
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @return	the <code>String</code> of the prompt text as it would appear exactly
	 * @throws RemoteException 
	 */
	public final String getPromptText() throws RemoteException {
		String space = MenuTools.space();
		String main = MenuTools.header(this.getHeader() + " " /*+ InspiredNations.taxTimer.getTimeLeftReadout()*/, PDI);
		String filler = this.getFiller();
		String end = footer + " {" + PDI.getMsg().getMissedSize() + "}";
		String errmsg = this.PDI.getMsg().pushMessageContent();
		if(!errmsg.isEmpty()) {
			end = end.concat("\n");
		}
		return space + main + filler + end + errmsg;
	}

	@Override
	protected final Prompt getNextPrompt(ConversationContext arg0) {
		try {
			return this.getPassTo();
		} catch (RemoteException e) {
			// TODO Kill Plugin
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public final Prompt acceptInput(ConversationContext arg0, String arg) {
		try {
			if(PDI.getKill()) {
				PDI.setKill(false);
				return Menu.END_OF_CONVERSATION;
			}
			if(arg == null) {
				Menu output = this.getPassTo();
				this.unloadNonPersist();
				return output;
			}
			String[] args = arg.split(" ");
			if (args[0].equalsIgnoreCase("say"))  {
				if(args.length > 1) {
					PDI.getMsg().sendChatMessage(arg.substring(4));
				}
				this.unloadNonPersist();
				return this.getSelfPersist();
			}
			this.PDI.getMsg().clearMenuVisible();
			if (arg.startsWith("/")) {
				arg = arg.substring(1);
			}
			if (arg.equalsIgnoreCase("back")) {
				return this.checkBack();
			}
			if (arg.equalsIgnoreCase("help")) {
				return this.getHelp();
			}
			if (arg.equalsIgnoreCase("hud")) {
				this.unloadNonPersist();
				this.unloadMenuPersistent();
				return new MainHud(PDI);
			}
			if (arg.equalsIgnoreCase("chat")) {
				this.unloadNonPersist();
				this.unloadMenuPersistent();
				return new Chat(PDI, this.getSelfPersist());
			}
			if (arg.equalsIgnoreCase("exit")) {
				this.PDI.getMsg().setMissedSize(0);
				
				try {
					for(Alert alert:PDI.getMsg().getMessages()) {
						this.PDI.sendRawMessage(alert.getDisplayName(PDI.getPlayerID()));
					}
					
					this.PDI.sendRawMessage(MenuTools.space());
				} catch (PlayerOfflineException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.unloadNonPersist();
				this.unloadMenuPersistent();
				return Menu.END_OF_CONVERSATION;
			}
			return this.checkNext(arg);
		}
		catch (RemoteException ex) {
			// Kill the plugin
			ex.printStackTrace();
			return null;
		}
	}
	/**
	 * Looks at previous menu and determines if it should be skipped or not
	 * @return	the actual previous menu rather than just the one before this one
	 * in the menu graph
	 * @throws RemoteException 
	 */
	private final Menu checkBack() throws RemoteException {
		Menu previous = this.getPreviousMenu().getSelfPersist();
		this.unloadMenuPersistent();
		if(!previous.passBy()) {
			//previous.PDI = this.PDI; What is this I don't even.
			return previous;
		}
		else {
			return previous.checkBack();
		}
	}
	/**
	 * Looks at next menu and determines if it should be skipped or not
	 * @param input	the <code>String</code> input by the player
	 * @return		the actual next Menu rather than just the one after this one
	 * in the menu graph
	 * @throws RemoteException 
	 */
	private final Menu checkNext(String input) throws RemoteException {
		Menu next = this.getNextMenu(input);
		this.unloadNonPersist();
		if(this != next) {
			this.unloadMenuPersistent();
		}
		while(next.passBy()) {
			next = (Menu) next.getPassTo();
		}
		return next;
	}
	/**
	 * Returns the prompt to go to when player uses "back"
	 * @return the <code>Prompt</code> that lead to this menu
	 * 
	 */
	public abstract Menu getPreviousMenu();
	/**
	 * 
	 * @param input	the <code>String</code> used to process the next <code>Prompt</code>
	 * @return		the next <code>Prompt</code> to be used in this conversation
	 * @throws RemoteException 
	 */
	public abstract Menu getNextMenu(String input) throws RemoteException;
	/**
	 * Determines if this menu should be passed by or not
	 * @return	<code>true</code> if it will be passed by
	 * @throws RemoteException 
	 */
	public abstract boolean getPassBy() throws RemoteException;
	/**
	 * Get's the menu that will be used if getPassBy() returns <code>true</code>
	 * @return	the menu passed to
	 * @throws RemoteException 
	 */
	public abstract Menu getPassTo() throws RemoteException;

	/**
	 * 
	 * @return	the <code>String</code> to be used for the non-persistent header of the menu.
	 */
	public abstract String getHeader();
	/**
	 * 
	 * @return	the <code>String</code> to be used for the non-persistent filler of the menu
	 * @throws RemoteException 
	 */
	public abstract String getFiller() throws RemoteException;
	
	
	// Shortcut methods. Not essential for Menu, but useful to do things easily.
	/**
	 * 
	 * @param error	the <code>MenuError</code> to be used as the error
	 * @throws RemoteException 
	 */
	public final Menu setError(String error) throws RemoteException {
		this.PDI.getMsg().receiveError(error);
		return this.getSelfPersist();
	}
	public final Menu setAlert(Alert alert) throws RemoteException {
		this.PDI.getMsg().receiveAlert(alert, false);
		return this.getSelfPersist();
	}
/*	*//**
	 * 
	 * @return the <code>ConversationContext</code> of the player using this menu
	 *//*
	public final ConversationContext getContext() {
		return PDI.getPlayerID() getCon().getContext();
	}*/
	
	public final PlayerDataInter getPlayerData() {
		return PDI;
	}
	public HelpMenu getHelp() {
		return help;
	}
}
