package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class RenameGovOption extends Option {

	InspiredGov gov;
	
	public RenameGovOption(OptionMenu menu, String label, OptionUnavail reason, InspiredGov gov) {
		super(menu, label, reason);
		this.gov = gov;
	}

	public RenameGovOption(OptionMenu menu, String label, InspiredGov gov) {
		super(menu, label);
		this.gov = gov;
	}

	public RenameGovOption(OptionMenu menu, String label, String description, InspiredGov gov) {
		super(menu, label, description);
		this.gov = gov;
	}

	@Override
	public Menu response(String input) {
		
		for(InspiredGov govtest:InspiredNations.regiondata) {
			if(govtest.getName().equalsIgnoreCase(input)) {
				this.menu.setError(MenuError.NAME_ALREADY_TAKEN(gov.getClass(), menu.getPlayerData()));
				return menu;
			}
		}
		
		this.gov.setName(input);
		return menu;
	}
}
