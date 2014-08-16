package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.Option;
import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountCollectionPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountPortal;

public class RemoveAccountOption extends Option {

	AccountPortal account;
	AccountCollectionPortal superAcc;
	
	public RemoveAccountOption(OptionMenu menu, String label, AccountPortal account, AccountCollectionPortal superAcc,
			OptionUnavail reason) {
		super(menu, label, reason);
		this.account = account;
		this.superAcc = superAcc;
	}

	public RemoveAccountOption(OptionMenu menu, String label, AccountPortal account, AccountCollectionPortal superAcc) {
		super(menu, label);
		this.account = account;	
		this.superAcc = superAcc;
	}

	public RemoveAccountOption(OptionMenu menu, String label, AccountPortal account, AccountCollectionPortal superAcc, String description) {
		super(menu, label, description);
		this.account = account;
		this.superAcc = superAcc;
	}

	@Override
	public Menu response(String input) {
		superAcc.remove(account);
		try {
			account.transferMoney(account.getTotalMoney(Currency.DEFAULT, InspiredNationsServer.Exchange.mcdown), Currency.DEFAULT, superAcc);
		} catch (BalanceOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NegativeMoneyTransferException e) {
			e.printStackTrace();
		}
		return menu;
	}

}
