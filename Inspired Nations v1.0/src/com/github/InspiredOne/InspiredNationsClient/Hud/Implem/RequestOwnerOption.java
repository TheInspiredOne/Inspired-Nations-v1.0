package com.github.InspiredOne.InspiredNationsClient.Hud.Implem;

import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class RequestOwnerOption extends Option {

	OwnerGov gov;
	
	public RequestOwnerOption(OptionMenu menu, String label,
			OptionUnavail reason, OwnerGov gov) {
		super(menu, label, reason);
		this.gov = gov;
	}

	public RequestOwnerOption(OptionMenu menu, String label, OwnerGov gov) {
		super(menu, label);
		this.gov = gov;
	}

	public RequestOwnerOption(OptionMenu menu, String label, String description, OwnerGov gov) {
		super(menu, label, description);
		this.gov = gov;
	}

	@Override
	public Menu response(String input) {
		gov.getOwnerRequests().add(menu.getPlayerData().getPlayerID());
		return menu;
	}

}
