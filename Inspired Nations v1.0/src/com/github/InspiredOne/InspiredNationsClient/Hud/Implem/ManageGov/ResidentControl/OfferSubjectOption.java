package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov.ResidentControl;

import com.github.InspiredOne.InspiredNations.Governments.OwnerSubjectGov;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuAlert;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class OfferSubjectOption extends Option {

	OwnerSubjectGov gov;
	PlayerID player;
	
	public OfferSubjectOption(OptionMenu menu, String label,
			OptionUnavail reason, OwnerSubjectGov gov, PlayerID player) {
		super(menu, label, reason);
		this.gov = gov;
		this.player = player;
	}

	public OfferSubjectOption(OptionMenu menu, String label, OwnerSubjectGov gov, PlayerID player) {
		super(menu, label);
		this.gov = gov;
		this.player = player;
	}

	public OfferSubjectOption(OptionMenu menu, String label, String description, OwnerSubjectGov gov, PlayerID player) {
		super(menu, label, description);
		this.gov = gov;
		this.player = player;
	}

	@Override
	public Menu response(String input) {
		if(this.gov.getSubjectRequests().contains(player)) {
			this.gov.addSubject(player);
			player.getPDI().sendNotification(MenuAlert.GOV_INVITED_YOU(gov, gov.getSubjectPositionName()));
		}
		else {
			this.gov.getSubjectOffers().add(player);
		}
		return menu;
	}

}
