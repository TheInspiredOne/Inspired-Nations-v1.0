package com.github.InspiredOne.InspiredNationsClient.Hud.MenuLoops.FindAddress;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;

public abstract class PickPlayerGeneral extends TabSelectOptionMenu<PlayerID> {

	protected Menu previous;
	
	public PickPlayerGeneral(PlayerData PDI, Menu previous) {
		super(PDI);
		this.previous = previous;
	}

	@Override
	public Menu getPreviousPrompt() {
		return previous;
	}

	/**
	 * Checks to see if the player should be listed in the tab options list.
	 * @param player
	 * @return
	 */
	public abstract boolean check(PlayerID player); 


	@Override
	public String postTabListPreOptionsText() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void addTabOptions() {
		for(PlayerID player:InspiredNations.playerdata.keySet()) {
			if(check(player)) {
				this.taboptions.add(player);
			}
		}
	}
}
