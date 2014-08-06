package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.Money;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Economy.Account;
import com.github.InspiredOne.InspiredNations.Economy.Currency;
import com.github.InspiredOne.InspiredNations.Economy.CurrencyAccount;
import com.github.InspiredOne.InspiredNations.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNations.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class RemoveCurrencyOption extends Option {

	Account account;
	CurrencyAccount curren;
	
	public RemoveCurrencyOption(OptionMenu menu, String label,
			OptionUnavail reason, Account account, CurrencyAccount curren) {
		super(menu, label, reason);
		this.account = account;
		this.curren = curren;
	}

	public RemoveCurrencyOption(OptionMenu menu, String label, Account account, CurrencyAccount curren) {
		super(menu, label);
		this.account = account;
		this.curren = curren;
	}

	public RemoveCurrencyOption(OptionMenu menu, String label,
			String description, Account account, CurrencyAccount curren) {
		super(menu, label, description);
		this.account = account;
		this.curren = curren;
	}

	@Override
	public Menu response(String input) {
		boolean trans = account.isAutoExchange();
		this.account.getMoney().remove(curren);
		account.setAutoExchange(true);
		try {
			curren.transferMoney(curren.getTotalMoney(curren.getCurrency(), InspiredNations.Exchange.mcdown), curren.getCurrency(), account);
		} catch (BalanceOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NegativeMoneyTransferException e) {
		}
		account.setAutoExchange(trans);
		return menu;
	}

}
