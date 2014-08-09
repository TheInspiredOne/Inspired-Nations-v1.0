package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.Option;
import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountPortalInter;
import com.github.InspiredOne.InspiredNationsServer.Remotes.CurrencyPortalInter;


public class AddCurrencyToAccountOption extends Option {

	AccountPortalInter account;
	CurrencyPortalInter curren;
	public AddCurrencyToAccountOption(OptionMenu menu, String label,
			OptionUnavail reason, AccountPortalInter account, CurrencyPortalInter curren) {
		super(menu, label, reason);
		this.account = account;
		this.curren = curren;
	}

	public AddCurrencyToAccountOption(OptionMenu menu, String label, AccountPortalInter account, CurrencyPortalInter curren) {
		super(menu, label);
		this.account = account;
		this.curren = curren;
	}

	public AddCurrencyToAccountOption(OptionMenu menu, String label,
			String description, AccountPortalInter account, CurrencyPortalInter curren) {
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
