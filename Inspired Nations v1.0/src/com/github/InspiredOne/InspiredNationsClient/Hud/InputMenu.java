package com.github.InspiredOne.InspiredNationsClient.Hud;

import java.util.ArrayList;
import java.util.List;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools.TextColor;
import com.github.InspiredOne.InspiredNationsClient.Listeners.ActionManager;
import com.github.InspiredOne.InspiredNationsClient.Listeners.Implem.InputManager;
import com.github.InspiredOne.InspiredNationsClient.Listeners.Implem.MenuUpdateManager;

public abstract class InputMenu extends ActionMenu {

	public List<String> tabOptions = new ArrayList<String>();
	
	public InputMenu(PlayerData PDI) {
		super(PDI);
	}
	
	@Override
	public final Menu getNextMenu(String input) {
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
	public String getFiller() {
		return TextColor.INSTRUCTION(PDI) + this.getInstructions() + "\n";
	}
	
	@Override
	public void actionResponse() {
		
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
	 */
	public abstract void useInput(String input);
	
	public final List<String> getTabOptions() {
		return this.tabOptions;
	}
	public abstract void addTabOptions();
	/**
	 * 
	 * @return	The text that instructs the player what to input
	 */
	public abstract String getInstructions();
	
	// These methods are overridden by all the super classes. I wish there were a better
	// way I could do this. Until then, ctrl-c and ctrl-v.
	@Override
	public void menuPersistent() {
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.stopListening();
		}
		this.getActionManager().clear();
		this.tabOptions.clear();
		this.getActionManager().add(new TaxTimerManager<ActionMenu>(this));
		this.getActionManager().add(new InputManager<InputMenu>(this, this.getTabOptions()));
		this.getActionManager().add(new MenuUpdateManager<InputMenu>(this));
		this.addActionManagers();
		this.addTabOptions();
	}

	@Override
	public void unloadPersist() {
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.stopListening();
		}
		tabOptions = new ArrayList<String>();
		this.getActionManager().clear();
	}
	
}
