package com.github.InspiredOne.InspiredNations.Hud.Implem;

import com.github.InspiredOne.InspiredNations.Governments.OwnerSubjectGov;
import com.github.InspiredOne.InspiredNations.Hud.Menu;
import com.github.InspiredOne.InspiredNations.Hud.Option;
import com.github.InspiredOne.InspiredNations.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;

public class JoinSubjectGovOption extends Option {

	OwnerSubjectGov gov;
	PlayerID player;
	
	public JoinSubjectGovOption(OptionMenu menu, String label, OptionUnavail reason, OwnerSubjectGov gov, PlayerID player) {
		super(menu, label, reason);
		this.gov = gov;
		this.player = player;
	}

	public JoinSubjectGovOption(OptionMenu menu, String label, OwnerSubjectGov gov, PlayerID player) {
		super(menu, label);
		this.gov = gov;
		this.player = player;
	}

	public JoinSubjectGovOption(OptionMenu menu, String label, String description, OwnerSubjectGov gov, PlayerID player) {
		super(menu, label, description);
		this.gov = gov;
		this.player = player;
	}

	@Override
	public Menu response(String input) {
		gov.addSubject(player);
		return menu;
	}

}
