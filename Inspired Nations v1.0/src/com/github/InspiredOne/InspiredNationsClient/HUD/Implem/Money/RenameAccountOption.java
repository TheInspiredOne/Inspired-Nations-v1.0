package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import com.github.InspiredOne.InspiredNations.Economy.Account;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNations.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.RenameNameableOption;

public class RenameAccountOption extends RenameNameableOption {

	public RenameAccountOption(OptionMenu menu, Nameable nameholder, String label,
			OptionUnavail reason) {
		super(menu, nameholder, label, reason);
	}

	public RenameAccountOption(OptionMenu menu, Nameable nameholder, String label) {
		super(menu, nameholder, label);
	}

	public RenameAccountOption(OptionMenu menu, Nameable nameholder, String label,
			String description) {
		super(menu, nameholder, label, description);
	}

	@Override
	public String validate(String input) {
		for(Account account:menu.PDI.getAccounts()) {
			
			if(account.getName().equalsIgnoreCase(input) && account != nameholder) {
				return MenuError.ACCOUNT_NAME_ALREADY_TAKEN(menu.PDI);
			}
		}
		return MenuError.NO_ERROR();
	}

}
