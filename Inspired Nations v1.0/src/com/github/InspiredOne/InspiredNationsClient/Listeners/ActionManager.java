package com.github.InspiredOne.InspiredNationsClient.Listeners;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.HandlerList;

import com.github.InspiredOne.InspiredNationsClient.InspiredNationsClient;
import com.github.InspiredOne.InspiredNationsClient.HUD.ActionMenu;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataInter;
/**
 * Deals with menu inputs that are actions rather than chat entries. Each
 * <code>ActionManager</code> has a collection of <code>InspiredPlayerListener</code>s
 * that can pass information into the manager, update the menu, and interact with the
 * player. For each <code>ActionMenu</code> there is one <code>ActionManager</code>.
 * @author Jedidiah E. Phillips
 *
 */
public abstract class ActionManager<T extends ActionMenu> {

	private T menu;
	protected List<InspiredListener<?>> listeners = new ArrayList<InspiredListener<?>>();

	public ActionManager(T menu) {
		this.menu = menu;
	}
	/**
	 * Starts the PlayerListener associated with this operation.
	 */
	public void startListening() {

		for(InspiredListener<?> listener:this.getPlayerListener()) {
			InspiredNationsClient.plugin.getServer().getPluginManager().registerEvents(listener, InspiredNationsClient.plugin);
		}
	}
	/**
	 * Shuts down the PlayerListener associated with this operation.
	 */
	public void stopListening() {

		for(InspiredListener<?> listener:this.getPlayerListener()) {
			HandlerList.unregisterAll(listener);
		}
	}
	
	/**
	 * 
	 * @return	a <code>List</code> of <code>InspiredPlayerListener</code>s used by this manager
	 */
	public List<InspiredListener<?>> getPlayerListener() {
		return listeners;
	}
	
	public T getActionMenu() {
		return menu;
	}
	
	public PlayerDataInter getPlayerData() {
		return this.menu.getPlayerData();
	}
	/**
	 * Updates the menu view for the player. 
	 * @throws RemoteException 
	 */
	public void Update() throws RemoteException {
		this.getActionMenu().Update();
	}
	/**
	 * A function that gets run whenever there is a text change in the ActionMenu
	 */
	public void textChange() {
		
	}
}
