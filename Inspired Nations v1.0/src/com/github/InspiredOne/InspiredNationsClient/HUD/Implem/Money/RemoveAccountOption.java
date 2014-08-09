package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Economy.Account;
import com.github.InspiredOne.InspiredNations.Economy.AccountCollection;
import com.github.InspiredOne.InspiredNations.Economy.Currency;
import com.github.InspiredOne.InspiredNations.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNations.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class RemoveAccountOption extends Option {

	Account account;
	AccountCollection superAcc;
	
	public RemoveAccountOption(OptionMenu menu, String label, Account account, AccountCollection superAcc,
			OptionUnavail reason) {
		super(menu, label, reason);
		this.account = account;
		this.superAcc = superAcc;
	}

	public RemoveAccountOption(OptionMenu menu, String label, Account account, AccountCollection superAcc) {
		super(menu, label);
		this.account = account;	
		this.superAcc = superAcc;
	}

	public RemoveAccountOption(OptionMenu menu, String label, Account account, AccountCollection superAcc, String description) {
		super(menu, label, description);
		this.account = account;
		this.superAcc = superAcc;
	}

	@Override
	public Menu response(String input) {
		superAcc.remove(account);
		try {
			account.transferMoney(account.getTotalMoney(Currency.DEFAULT, InspiredNations.Exchange.mcdown), Currency.DEFAULT, superAcc);
		} catch (BalanceOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NegativeMoneyTransferException e) {
			e.printStackTrace();
		}
		return menu;
	}

}
