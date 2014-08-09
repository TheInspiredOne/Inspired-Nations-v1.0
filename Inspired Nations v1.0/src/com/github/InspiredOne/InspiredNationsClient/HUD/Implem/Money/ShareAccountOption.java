package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import com.github.InspiredOne.InspiredNations.Economy.Account;
import com.github.InspiredOne.InspiredNations.Economy.AccountCollection;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class ShareAccountOption extends Option {

	Account account;
	AccountCollection accounts;
	
	public ShareAccountOption(OptionMenu menu, String label, Account account, AccountCollection accountsto,
			OptionUnavail reason) {
		super(menu, label, reason);
		this.account = account;
		this.accounts = accountsto;
	}

	public ShareAccountOption(OptionMenu menu, String label,  Account account, AccountCollection accountsto) {
		super(menu, label);
		this.account = account;
		this.accounts = accountsto;
	}

	public ShareAccountOption(OptionMenu menu, String label, Account account, AccountCollection accountsto, String description) {
		super(menu, label, description);
		this.account = account;
		this.accounts = accountsto;
	}

	@Override
	public Menu response(String input) {
		accounts.add(account);
		return menu;
	}

}
