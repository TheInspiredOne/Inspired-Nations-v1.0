package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.Option;
import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountCollectionPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountPortal;


public class ShareAccountOption extends Option {

	AccountPortal account;
	AccountCollectionPortal accounts;
	
	public ShareAccountOption(OptionMenu menu, String label, AccountPortal account, AccountCollectionPortal accountsto,
			OptionUnavail reason) {
		super(menu, label, reason);
		this.account = account;
		this.accounts = accountsto;
	}

	public ShareAccountOption(OptionMenu menu, String label,  AccountPortal account, AccountCollectionPortal accountsto) {
		super(menu, label);
		this.account = account;
		this.accounts = accountsto;
	}

	public ShareAccountOption(OptionMenu menu, String label, AccountPortal account, AccountCollectionPortal accountsto, String description) {
		super(menu, label, description);
		this.account = account;
		this.accounts = accountsto;
	}

	@Override
	public Menu response(String input) throws RemoteException {
		accounts.add(account);
		return menu;
	}

}
