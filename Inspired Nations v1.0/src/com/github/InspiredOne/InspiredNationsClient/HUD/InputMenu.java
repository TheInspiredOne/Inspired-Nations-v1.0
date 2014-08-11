package com.github.InspiredOne.InspiredNationsClient.HUD;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.github.InspiredOne.InspiredNationsClient.Listeners.ActionManager;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;

public abstract class InputMenu extends ActionMenu {

	public List<String> tabOptions = new ArrayList<String>();
	
	public InputMenu(PlayerDataPortal PDI) throws RemoteException {
		super(PDI);
	}
	
	@Override
	public final Menu getNextMenu(String input) throws RemoteException {
		input = input.trim();
		String error = this.validate(input);
		this.setError(error);
		if(error.isEmpty()) {
			this.useInput(input);
			return this.nextMenu();
		}
		else {
			return this.getSelfPersist();
		}
	}

	@Override
	public Menu getPassTo() {
		return this.nextMenu();
	}
	
	@Override
	public String getFiller() throws RemoteException {
		return PDI.INSTRUCTION() + this.getInstructions() + "\n";
	}
	
	@Override
	public void actionResponse() throws RemoteException {
		
	}
	
	/**
	 * 
	 * @return	the menu to go to if passBy() returns true
	 */
	public abstract Menu nextMenu();
	/**
	 * 
	 * @param input	the <code>String</code> that the player input
	 * @return		the <code>MenuError</code> that the input throws. Returns <code>MenuError.NO_ERROR</code> if no error
	 */
	public abstract String validate(String input);
	/**
	 * Conditions and implements the input.
	 * @param input	the <code>String</code> that the player input
	 * @throws RemoteException 
	 */
	public abstract void useInput(String input) throws RemoteException;
	
	public final List<String> getTabOptions() {
		return this.tabOptions;
	}
	public abstract void addTabOptions();
	/**
	 * 
	 * @return	The text that instructs the player what to input
	 * @throws RemoteException 
	 */
	public abstract String getInstructions() throws RemoteException;
	
	// These methods are overridden by all the super classes. I wish there were a better
	// way I could do this. Until then, ctrl-c and ctrl-v.
	@Override
	public void menuPersistent() throws RemoteException {
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.stopListening();
		}
		this.getActionManager().clear();
		this.tabOptions.clear();
//		this.getActionManager().add(new TaxTimerManager<ActionMenu>(this));
//		this.getActionManager().add(new InputManager<InputMenu>(this, this.getTabOptions()));
//		this.getActionManager().add(new MenuUpdateManager<InputMenu>(this));
		this.addActionManagers();
		this.addTabOptions();
	}

	@Override
	public void unloadPersist() throws RemoteException {
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.stopListening();
		}
		tabOptions = new ArrayList<String>();
		this.getActionManager().clear();
	}
	
}
