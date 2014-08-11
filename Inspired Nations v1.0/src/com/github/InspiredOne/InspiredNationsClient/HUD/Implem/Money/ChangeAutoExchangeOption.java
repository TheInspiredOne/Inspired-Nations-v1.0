package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.Option;
import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountPortal;


public class ChangeAutoExchangeOption extends Option {
	private AccountPortal account;
	public ChangeAutoExchangeOption(OptionMenu menu, String label,
			OptionUnavail reason) {
		super(menu, label, reason);
		
	}

	public ChangeAutoExchangeOption(OptionMenu menu, String label, AccountPortal account) throws RemoteException {
		super(menu, label, ": " + account.isAutoExchange());
		this.account = account;
	}

	public ChangeAutoExchangeOption(OptionMenu menu, String label,
			String description) {
		super(menu, label, description);
	}

	@Override
	public Menu response(String input) throws RemoteException {
		this.account.setAutoExchange(!this.account.isAutoExchange());
		return menu;
	}

}
