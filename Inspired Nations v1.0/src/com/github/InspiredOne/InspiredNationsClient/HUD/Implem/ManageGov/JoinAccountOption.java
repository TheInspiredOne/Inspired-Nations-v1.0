package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.Option;
import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsServer.Remotes.InspiredGovPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;


public class JoinAccountOption extends Option {

	InspiredGovPortal gov;
	PlayerDataPortal PDI;
	
	public JoinAccountOption(OptionMenu menu, String label,
			OptionUnavail reason, InspiredGovPortal gov) {
		super(menu, label, reason);
		this.gov = gov;
	}

	public JoinAccountOption(OptionMenu menu, String label, InspiredGovPortal gov) {
		super(menu, label);
		this.gov = gov;
	}

	public JoinAccountOption(OptionMenu menu, String label, String description, InspiredGovPortal gov) {
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
