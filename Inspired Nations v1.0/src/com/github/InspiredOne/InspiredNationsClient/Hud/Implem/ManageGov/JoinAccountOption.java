package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class JoinAccountOption extends Option {

	InspiredGov gov;
	PlayerData PDI;
	
	public JoinAccountOption(OptionMenu menu, String label,
			OptionUnavail reason, InspiredGov gov) {
		super(menu, label, reason);
		this.gov = gov;
	}

	public JoinAccountOption(OptionMenu menu, String label, InspiredGov gov) {
		super(menu, label);
		this.gov = gov;
	}

	public JoinAccountOption(OptionMenu menu, String label, String description, InspiredGov gov) {
		super(menu, label, description);
		this.gov = gov;
	}

	@Override
	public Menu response(String input) {
		PDI = menu.PDI;
		if(!gov.getAccounts().isLinked()) {
			gov.joinAccount(PDI);
		}
		else {
			menu.setError(MenuError.ACCOUNT_ALREADY_LINKED(this.PDI));
		}
		return menu;
	}

}
