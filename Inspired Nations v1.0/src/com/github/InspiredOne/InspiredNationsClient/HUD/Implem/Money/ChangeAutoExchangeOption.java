package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import com.github.InspiredOne.InspiredNations.Economy.Account;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class ChangeAutoExchangeOption extends Option {
	private Account account;
	public ChangeAutoExchangeOption(OptionMenu menu, String label,
			OptionUnavail reason) {
		super(menu, label, reason);
		
	}

	public ChangeAutoExchangeOption(OptionMenu menu, String label, Account account) {
		super(menu, label, ": " + account.isAutoExchange());
		this.account = account;
	}

	public ChangeAutoExchangeOption(OptionMenu menu, String label,
			String description) {
		super(menu, label, description);
	}

	@Override
	public Menu response(String input) {
		this.account.setAutoExchange(!this.account.isAutoExchange());
		return menu;
	}

}
