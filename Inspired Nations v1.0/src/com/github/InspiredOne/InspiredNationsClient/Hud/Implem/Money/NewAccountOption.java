package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.Money;

import com.github.InspiredOne.InspiredNations.Economy.Account;
import com.github.InspiredOne.InspiredNations.Economy.AccountCollection;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class NewAccountOption extends Option {

	AccountCollection accounts;
	public NewAccountOption(OptionMenu menu, String label, AccountCollection accounts) {
		super(menu, label);
		this.accounts = accounts;
	}

	@Override
	public Menu response(String input) {
		input = input.trim();
		if(input.isEmpty()) {
			menu.setError(MenuError.EMPTY_INPUT(menu.getPlayerData()));
			return menu.getSelfPersist();
		}
		for(Account account:accounts){
			if(account.getName().equalsIgnoreCase(input)) {
				menu.setError(MenuError.ACCOUNT_NAME_ALREADY_TAKEN(menu.getPlayerData()));
				return menu.getSelfPersist();
			}
		}
		accounts.add(new Account(input));
		return menu.getSelfPersist();
	}

}
