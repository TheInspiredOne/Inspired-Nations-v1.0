package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.Money;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.Account;
import com.github.InspiredOne.InspiredNations.Economy.AccountCollection;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;

public class ManageAccounts extends TabSelectOptionMenu<Account> {

	Menu previous;
	AccountCollection accounts;
	
	public ManageAccounts(PlayerData PDI, Menu previous, AccountCollection accounts) {
		super(PDI);
		this.previous = previous;
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
		return "Manage Accounts";
	}
	@Override
	public void addTabOptions() {
		for(Account account:accounts) {
			this.taboptions.add(account);
		}
	}

	@Override
	public void addOptions() {
		if(taboptions.size() > 0) {
			this.options.add(new ChangeTabOrderOption<>(new ManageAccounts(PDI, previous, accounts), "Change Account Order <+/->", PDI.getAccounts(), this.getData()));
			this.options.add(new PromptOption(this, "Manage " + this.getData().getName() + " account", new ManageAccount(PDI, previous, this.getData(), accounts)));
			this.options.add(new PromptOption(this, "Pay With " + this.getData().getName(), new PayNav(PDI, this, this.getData())));
			this.options.add(new PromptOption(this, "Manage Currencies In " +this.getData().getName(), new ManageCurrencies(PDI, this,this.getData(), accounts)));
		}
		if(taboptions.size() > 1) {
			this.options.add(new RemoveAccountOption(this, "Remove Account", this.getData(), this.accounts));
		}
		this.options.add(new NewAccountOption(this, "New Account <Name>", accounts));

		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
	}

}
