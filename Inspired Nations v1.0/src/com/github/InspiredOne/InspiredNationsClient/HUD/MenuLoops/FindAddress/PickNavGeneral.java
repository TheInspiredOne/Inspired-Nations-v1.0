package com.github.InspiredOne.InspiredNationsClient.HUD.MenuLoops.FindAddress;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.PassByOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.HUD.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.HUD.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;


public abstract class PickNavGeneral extends PassByOptionMenu {

	protected Menu previous;
	
	public PickNavGeneral(PlayerDataPortal PDI, Menu previous) throws RemoteException {
		super(PDI);
		this.previous = previous;
	}

	@Override
	public Menu getPreviousMenu() {
		return previous;
	}

	/**
	 * Gets the text to be used for the government option
	 * @return
	 */
	public abstract String getGovOptionText();
	/**
	 * Gets the text to be used for the player option
	 * @return
	 */
	public abstract String getPlayerOptionText();
	/**
	 * gets the menu to be used for the government option
	 * @return
	 */
	public abstract Menu getGovMenu();
	/**
	 * gets the menu to be used for the Player option
	 * @return
	 * @throws RemoteException 
	 */
	public abstract PickPlayerGeneral getPlayerMenu() throws RemoteException;

	@Override
	public void addOptions() {
		TabSelectOptionMenu<PlayerID> playermenu = this.getPlayerMenu();
		playermenu.Initialize();
		if (!playermenu.getTabOptions().isEmpty()){
			this.options.add(new PromptOption(this, this.getPlayerOptionText(), this.getPlayerMenu()));
		}
		if(!InspiredNations.global.getData().getAllSubGovsAndFacilitiesJustBelow().isEmpty()) {
			this.options.add(new PromptOption(this, this.getGovOptionText(), this.getGovMenu()));
		}
	}



}
