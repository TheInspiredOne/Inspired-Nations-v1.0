package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov.ResidentControl;

import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuAlert;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class OfferOwnerOption extends Option {

	OwnerGov gov;
	PlayerID player;
	
	public OfferOwnerOption(OptionMenu menu, String label, OptionUnavail reason, OwnerGov gov, PlayerID player) {
		super(menu, label, reason);
		this.gov = gov;
		this.player = player;
	}

	public OfferOwnerOption(OptionMenu menu, String label, OwnerGov gov, PlayerID player) {
		super(menu, label);
		this.gov = gov;
		this.player = player;
	}

	public OfferOwnerOption(OptionMenu menu, String label, String description, OwnerGov gov, PlayerID player) {
		super(menu, label, description);
		this.gov = gov;
		this.player = player;
	}

	@Override
	public Menu response(String input) {
		if(!this.gov.getOwnerRequests().contains(player)) {
			this.gov.getOwnerOffers().add(player);
			player.getPDI().sendNotification(MenuAlert.GOV_INVITED_YOU(gov, gov.getOwnerPositionName()));
		}
		else {
			this.gov.addOwner(player);
		}
		return menu;
	}

}
