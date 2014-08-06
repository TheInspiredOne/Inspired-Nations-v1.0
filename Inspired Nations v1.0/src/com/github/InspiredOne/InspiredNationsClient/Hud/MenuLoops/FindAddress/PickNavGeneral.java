package com.github.InspiredOne.InspiredNationsClient.Hud.MenuLoops.FindAddress;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PassByOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;

public abstract class PickNavGeneral extends PassByOptionMenu {

	protected Menu previous;
	
	public PickNavGeneral(PlayerData PDI, Menu previous) {
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
	 */
	public abstract PickPlayerGeneral getPlayerMenu();

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
