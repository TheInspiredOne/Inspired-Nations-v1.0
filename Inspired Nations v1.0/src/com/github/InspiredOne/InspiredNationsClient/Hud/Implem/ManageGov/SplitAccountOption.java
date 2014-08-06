package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov;

import java.util.ArrayList;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.AccountCollection;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class SplitAccountOption extends Option {

	InspiredGov gov;
	PlayerData PDI;
	
	public SplitAccountOption(OptionMenu menu, String label,
			OptionUnavail reason, InspiredGov gov) {
		super(menu, label, reason);
		this.gov = gov;
	}

	public SplitAccountOption(OptionMenu menu, String label, InspiredGov gov) {
		super(menu, label);
		this.gov = gov;
	}

	public SplitAccountOption(OptionMenu menu, String label, String description, InspiredGov gov) {
		super(menu, label, description);
		this.gov = gov;
	}

	@Override
	public Menu response(String input) {
		PDI = menu.PDI;
		Debug.print("inside response of splitaccountoption");
		if(gov.getAccounts().isLinked()) {
			Debug.print("inside response of splitaccountoption 2");
			gov.splitAccount(PDI, new ArrayList<PlayerID>(), new AccountCollection(gov.getName()));
		}
		else {
			Debug.print("inside response of splitaccountoption 3");
			menu.setError(MenuError.ACCOUNT_COLLECTION_NOT_LINKED(menu.PDI));
		}
		return menu;
	}

}
