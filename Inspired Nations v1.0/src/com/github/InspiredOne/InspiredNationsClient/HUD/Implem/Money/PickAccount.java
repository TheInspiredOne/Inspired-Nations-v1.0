package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.Account;
import com.github.InspiredOne.InspiredNations.Economy.AccountCollection;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNations.ToolBox.Payable;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;

public class PickAccount extends TabSelectOptionMenu<Account> {

	AccountCollection accounts;
	Payable accountFrom;
	Menu previous;
	public PickAccount(PlayerData PDI, Menu previous, AccountCollection collection, Payable accountFrom) {
		super(PDI);
		this.accounts = collection;
		this.previous = previous;
		this.accountFrom = accountFrom;
	}

	@Override
	public Menu getPreviousPrompt() {
		return previous;
	}

	@Override
	public String postTabListPreOptionsText() {
		return MenuTools.oneLineWallet("", PDI, accountFrom);
	}

	@Override
	public String getHeader() {
		return "Pick Account To Transfer Money Into";
	}

	@Override
	public void addTabOptions() {
		for(Account account:accounts) {
			this.taboptions.add(account);
		}
		
	}

	@Override
	public void addOptions() {
		this.options.add(new PromptOption(this, "Pick Currency Account", new PickCurrencyCollection(PDI, this, this.getData(), accountFrom)));
		this.options.add(new PayAccountOption(PDI, this, "Transfer Money <amount>", accountFrom, this.getData(), PDI));		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
	}
}
