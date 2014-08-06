package com.github.InspiredOne.InspiredNationsClient.Hud.Implem;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;

public class PlayerDirectory extends TabSelectOptionMenu<PlayerID> {

	public PlayerDirectory(PlayerData PDI) {
		super(PDI);
	}

	@Override
	public Menu getPreviousPrompt() {
		return new MainHud(PDI);
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}

	@Override
	public String getHeader() {
		return "Player Directory";
	}

	@Override
	public void addTabOptions() {
		for(PlayerID player:InspiredNations.playerdata.keySet()) {
			this.taboptions.add(player);
		}
	}

	@Override
	public void addOptions() {
		options.add(new PromptOption(this, "Profile", new PlayerProfile(PDI,this.getData().getPDI())));
	}

	@Override
	public void addActionManagers() {
		
	}
}
