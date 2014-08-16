package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.HUD.RenameNameableOption;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountCollectionPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountPortal;

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
	public String validate(String input) throws RemoteException {
		AccountCollectionPortal accounts = menu.PDI.getAccounts();
		for(int i = 0; i < accounts.getSize(); i++) {
			AccountPortal account = accounts.get(i);
			if(account.getName().equalsIgnoreCase(input) && account != nameholder) {
				return MenuError.ACCOUNT_NAME_ALREADY_TAKEN(menu.PDI);
			}
		}
		return MenuError.NO_ERROR();
	}

}
