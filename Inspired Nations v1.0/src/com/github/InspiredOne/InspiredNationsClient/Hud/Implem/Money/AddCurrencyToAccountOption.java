package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.Money;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.Economy.Account;
import com.github.InspiredOne.InspiredNations.Economy.Currency;
import com.github.InspiredOne.InspiredNations.Economy.CurrencyAccount;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class AddCurrencyToAccountOption extends Option {

	Account account;
	Currency curren;
	public AddCurrencyToAccountOption(OptionMenu menu, String label,
			OptionUnavail reason, Account account, Currency curren) {
		super(menu, label, reason);
		this.account = account;
		this.curren = curren;
	}

	public AddCurrencyToAccountOption(OptionMenu menu, String label, Account account, Currency curren) {
		super(menu, label);
		this.account = account;
		this.curren = curren;
	}

	public AddCurrencyToAccountOption(OptionMenu menu, String label,
			String description, Account account, Currency curren) {
		super(menu, label, description);
		this.account = account;
		this.curren = curren;
	}

	@Override
	public Menu response(String input) {
		if(account.containsCurrency(curren)) {
			menu.setError(MenuError.ACCOUNT_ALREADY_HAS_THAT_CURRENCY(menu.getPlayerData()));
		}
		else {
			this.account.getMoney().add(new CurrencyAccount(curren, BigDecimal.ZERO));	
		}
		return menu;
	}

}
