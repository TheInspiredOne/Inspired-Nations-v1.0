package com.github.InspiredOne.InspiredNationsClient.Hud;


import java.util.ArrayList;
import java.util.List;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNationsClient.Listeners.ActionManager;
import com.github.InspiredOne.InspiredNationsClient.Listeners.Implem.MenuUpdateManager;

public abstract class ActionMenu extends Menu {

	private String current = "";
	// Menu Persistent
//	protected List<ActionManager<?>> managers = new ArrayList<ActionManager<?>>();
	
	public ActionMenu(PlayerData PDI) {
		super(PDI);
	}

	public final void Update() {
		this.actionResponse();
		if (!current.equals(this.getPromptText())) {
			try {
				PDI.getCon().outputNextPrompt();
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
	 */
	public final List<ActionManager<?>> getActionManager() {
		if(PDI.actionmanagers == null) {
			PDI.actionmanagers = new ArrayList<ActionManager<?>>();
		}
		return PDI.actionmanagers;
	}

	/**
	 * Called whenever the menu is updated.
	 */
	public abstract void actionResponse();
	/**
	 * Add all the action managers using this method.
	 */
	public abstract void addActionManagers();
	
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
	public void menuPersistent() {
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
	public void nonPersistent() {
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.stopListening();
		}
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.startListening();
		}
	}

	@Override
	public void unloadNonPersist() {
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.stopListening();
		}
	}

	@Override
	public void unloadPersist() {
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.stopListening();
		}
		this.getActionManager().clear();
	}
}
