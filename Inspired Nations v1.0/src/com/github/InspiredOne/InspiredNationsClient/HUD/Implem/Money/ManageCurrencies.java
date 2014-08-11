package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.HUD.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountCollectionPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.CurrencyAccountPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;


public class ManageCurrencies extends TabSelectOptionMenu<CurrencyAccountPortal> {

	Menu previous;
	AccountCollectionPortal accounts;
	AccountPortal account;

	public ManageCurrencies(PlayerDataPortal PDI, Menu previous, AccountPortal account, AccountCollectionPortal accounts) throws RemoteException {
		super(PDI);
		this.previous = previous;
		this.account = account;
		this.accounts = accounts;
	}

	@Override
	public Menu getPreviousPrompt() {
		return previous;
	}

	@Override
	public String postTabListPreOptionsText() {
		
		return "";
	}

	@Override
	public String getHeader() {
		return "Manage Currency";
	}

	@Override
	public void addTabOptions() {
		for(CurrencyAccountPortal curren:account.getMoney()) {
			this.taboptions.add(curren);
		}
		
	}

	@Override
	public void addOptions() {
		if(this.taboptions.size() > 0) {
			this.options.add(new PromptOption(this, "Pay with " + this.getData().getName(), new PayNav(PDI, this, this.getData())));
			this.options.add(new ChangeTabOrderOption<>(this, "Change Currency Order <+/->", account.getMoney(), this.getData()));
			this.options.add(new PromptOption(this, "Transfer " + this.getData().getCurrency(), new PickAccount(PDI, this, accounts, this.getData())));
			if(this.taboptions.size() > 1) {
				this.options.add(new RemoveCurrencyOption(this, "Remove " + this.getData().getCurrency(), account, this.getData()));
			}
		}
		PickCurrencyToAdd next = new PickCurrencyToAdd(PDI, this , account);
		next.addTabOptions();
		if(next.getTabOptions().size() > 0) {
			this.options.add(new PromptOption(this, "Add Currency", next));
		}
		else {
			this.options.add(new PromptOption(this, "Add Currency", next, OptionUnavail.NO_CURRENCIES_TO_ADD));
		}
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
