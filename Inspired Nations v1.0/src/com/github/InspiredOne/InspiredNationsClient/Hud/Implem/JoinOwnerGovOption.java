package com.github.InspiredOne.InspiredNationsClient.Hud.Implem;

import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class JoinOwnerGovOption extends Option {

	OwnerGov gov;
	PlayerID player;
	
	public JoinOwnerGovOption(OptionMenu menu, String label,
			OptionUnavail reason, OwnerGov gov, PlayerID player) {
		super(menu, label, reason);
		this.gov = gov;
		this.player  = player;
	}

	public JoinOwnerGovOption(OptionMenu menu, String label, OwnerGov gov, PlayerID player) {
		super(menu, label);
		this.gov = gov;
		this.player = player;
	}

	public JoinOwnerGovOption(OptionMenu menu, String label, String description, OwnerGov gov, PlayerID player) {
		super(menu, label, description);
		this.gov = gov;
		this.player = player;
	}

	@Override
	public Menu response(String input) {
		gov.addOwner(player);
		return menu;
	}

}
