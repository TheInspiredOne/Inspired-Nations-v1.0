package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.Option;
import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsServer.Economy.CurrencyAccount;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.CurrencyPortal;


public class AddCurrencyToAccountOption extends Option {

	AccountPortal account;
	CurrencyPortal curren;
	public AddCurrencyToAccountOption(OptionMenu menu, String label,
			OptionUnavail reason, AccountPortal account, CurrencyPortal curren) {
		super(menu, label, reason);
		this.account = account;
		this.curren = curren;
	}

	public AddCurrencyToAccountOption(OptionMenu menu, String label, AccountPortal account, CurrencyPortal curren) {
		super(menu, label);
		this.account = account;
		this.curren = curren;
	}

	public AddCurrencyToAccountOption(OptionMenu menu, String label,
			String description, AccountPortal account, CurrencyPortal curren) {
		super(menu, label, description);
		this.account = account;
		this.curren = curren;
	}

	@Override
	public Menu response(String input) throws RemoteException {
		if(account.containsCurrency(curren)) {
			menu.setError(MenuError.ACCOUNT_ALREADY_HAS_THAT_CURRENCY(menu.getPlayerData()));
		}
		else {
			this.account.addCurrencyAccount(new CurrencyAccount(curren, BigDecimal.ZERO));	
		}
		return menu;
	}

}
