package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.Option;
import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;
import com.github.InspiredOne.InspiredNationsServer.Economy.MoneyExchange;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.CurrencyAccountPortal;

public class RemoveCurrencyOption extends Option {

	AccountPortal account;
	CurrencyAccountPortal curren;
	
	public RemoveCurrencyOption(OptionMenu menu, String label,
			OptionUnavail reason, AccountPortal account, CurrencyAccountPortal curren) {
		super(menu, label, reason);
		this.account = account;
		this.curren = curren;
	}

	public RemoveCurrencyOption(OptionMenu menu, String label, AccountPortal account, CurrencyAccountPortal curren) {
		super(menu, label);
		this.account = account;
		this.curren = curren;
	}

	public RemoveCurrencyOption(OptionMenu menu, String label,
			String description, AccountPortal account, CurrencyAccountPortal curren) {
		super(menu, label, description);
		this.account = account;
		this.curren = curren;
	}

	@Override
	public Menu response(String input) throws RemoteException {
		boolean trans = account.isAutoExchange();
		this.account.removeCurrency(curren);
		account.setAutoExchange(true);
		try {
			curren.transferMoney(curren.getTotalMoney(curren.getCurrency(), MoneyExchange.mcdown), curren.getCurrency(), account);
		} catch (BalanceOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NegativeMoneyTransferException e) {
		}
		account.setAutoExchange(trans);
		return menu;
	}

}
