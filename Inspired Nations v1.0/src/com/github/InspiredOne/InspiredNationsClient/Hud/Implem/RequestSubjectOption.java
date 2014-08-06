package com.github.InspiredOne.InspiredNationsClient.Hud.Implem;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.Governments.OwnerSubjectGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class RequestSubjectOption extends Option {

	OwnerSubjectGov gov;
	
	public RequestSubjectOption(OptionMenu menu, String label,
			OptionUnavail reason, OwnerSubjectGov gov) {
		super(menu, label, reason);
		this.gov = gov;
	}

	public RequestSubjectOption(OptionMenu menu, String label, OwnerSubjectGov gov) {
		super(menu, label);
		this.gov = gov;
	}

	public RequestSubjectOption(OptionMenu menu, String label,
			String description, OwnerSubjectGov gov) {
		super(menu, label, description);
		this.gov = gov;
	}

	@Override
	public Menu response(String input) {
		Debug.print("here 1");
		gov.getSubjectRequests().add(menu.getPlayerData().getPlayerID());
		Debug.print("here 1");
		return menu;
	}

}
