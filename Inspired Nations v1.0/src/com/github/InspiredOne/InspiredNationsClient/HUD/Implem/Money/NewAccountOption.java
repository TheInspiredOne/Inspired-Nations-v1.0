package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.Option;
import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNationsServer.Economy.Account;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountCollectionPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountPortal;


public class NewAccountOption extends Option {

	AccountCollectionPortal accounts;
	public NewAccountOption(OptionMenu menu, String label, AccountCollectionPortal accounts) {
		super(menu, label);
		this.accounts = accounts;
	}

	@Override
	public Menu response(String input) throws RemoteException {
		input = input.trim();
		if(input.isEmpty()) {
			menu.setError(MenuError.EMPTY_INPUT(menu.getPlayerData()));
			return menu.getSelfPersist();
		}
		for(int i=0; i< accounts.getSize(); i++) {
			AccountPortal account = accounts.get(i);
			if(account.getName().equalsIgnoreCase(input)) {
				menu.setError(MenuError.ACCOUNT_NAME_ALREADY_TAKEN(menu.getPlayerData()));
				return menu.getSelfPersist();
			}
		}
		accounts.addAccount(new Account(input));
		return menu.getSelfPersist();
	}

}
