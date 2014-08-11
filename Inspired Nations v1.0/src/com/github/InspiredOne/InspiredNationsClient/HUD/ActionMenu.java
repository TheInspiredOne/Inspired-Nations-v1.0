package com.github.InspiredOne.InspiredNationsClient.HUD;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.github.InspiredOne.InspiredNationsClient.InspiredNationsClient;
import com.github.InspiredOne.InspiredNationsClient.Listeners.ActionManager;
import com.github.InspiredOne.InspiredNationsClient.Listeners.Implem.MenuUpdateManager;
import com.github.InspiredOne.InspiredNationsClient.Listeners.Implem.TaxTimerManager;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;

public abstract class ActionMenu extends Menu {

	private String current = "";
	// Menu Persistent
//	protected List<ActionManager<?>> managers = new ArrayList<ActionManager<?>>();
	
	public ActionMenu(PlayerDataPortal PDI) throws RemoteException {
		super(PDI);
	}

	public final void Update() throws RemoteException {
		this.actionResponse();
		if (!current.equals(this.getPromptText())) {
			try {
				InspiredNationsClient.playerdata.get(PDI.getPlayerID()).getCon().outputNextPrompt();
			}
			catch (Exception ex) {
				this.unloadNonPersist();
				//PDI.getCon().acceptInput("exit");
				ex.printStackTrace();
			}
			current = this.getPromptText();
			for(ActionManager<?> mana:this.getActionManager()) {
				mana.textChange();
			}
		}
		else return;
	}
	
/*	@Override
	protected String getError() {
		String output = (String) this.getContext().getSessionData(ContextData.Error);
		return output;
	}*/
	
	
	/**
	 * Gets all the action managers for this menu.
	 * @return	an <code>ArrayList</code> of all the action managers
	 * @throws RemoteException 
	 */
	public final List<ActionManager<?>> getActionManager() throws RemoteException {
		if(InspiredNationsClient.playerdata.get(PDI.getPlayerID()).actionmanagers == null) {
			InspiredNationsClient.playerdata.get(PDI.getPlayerID()).actionmanagers = new ArrayList<ActionManager<?>>();
		}
		return InspiredNationsClient.playerdata.get(PDI.getPlayerID()).actionmanagers;
	}

	/**
	 * Called whenever the menu is updated.
	 * @throws RemoteException 
	 */
	public abstract void actionResponse() throws RemoteException;
	/**
	 * Add all the action managers using this method.
	 * @throws RemoteException 
	 */
	public abstract void addActionManagers() throws RemoteException;
	
	@Override
	public Menu getSelfPersist() {
/*		for(ActionManager<?> manager:this.getActionManager()) {
			manager.stopListening();
		}*/
		return this;
	}

	// These methods are overridden by all the super classes. I wish there were a better
	// way I could do this. Until then, ctrl-c and ctrl-v.
	@Override
	public void menuPersistent() throws RemoteException {
		this.setError(MenuError.NO_ERROR());
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.stopListening();
		}

		this.getActionManager().clear();
		this.getActionManager().add(new TaxTimerManager<ActionMenu>(this));
		this.getActionManager().add(new MenuUpdateManager<ActionMenu>(this));
		this.addActionManagers();
		
	}

	@Override
	public void nonPersistent() throws RemoteException {
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.stopListening();
		}
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.startListening();
		}
	}

	@Override
	public void unloadNonPersist() throws RemoteException {
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.stopListening();
		}
	}

	@Override
	public void unloadPersist() throws RemoteException {
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.stopListening();
		}
		this.getActionManager().clear();
	}
}
