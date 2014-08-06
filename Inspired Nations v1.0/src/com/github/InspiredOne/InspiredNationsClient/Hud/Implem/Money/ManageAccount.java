package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.Money;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.Account;
import com.github.InspiredOne.InspiredNations.Economy.AccountCollection;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;

public class ManageAccount extends OptionMenu {

	private Menu previous;
	private Account account;
	private AccountCollection accounts;
	public ManageAccount(PlayerData PDI, Menu previous, Account account, AccountCollection accounts) {
		super(PDI);
		this.account = account;
		this.previous = previous;
		this.accounts = accounts;
	}

	@Override
	public String getPreOptionText() {
		return "";
	}

	@Override
	public String getHeader() {
		return "Manage " + account.getName();
	}

	@Override
	public Menu getPreviousMenu() {
		return new ManageAccounts(PDI, previous, accounts);
	}

	@Override
	public boolean getPassBy() {
		return false;
	}

	@Override
	public Menu getPassTo() {
		return null;
	}

	@Override
	public void addOptions() {
		Debug.print("Inside Add Options 1");
		this.options.add(new ChangeAutoExchangeOption(this, "Toggle Auto-Exchange", account));
		this.options.add(new PromptOption(this, "Pay With " + account.getName(), new PayNav(PDI, new ManageAccount(PDI, previous, account, accounts), account)));
		this.options.add(new RenameAccountOption(this, account, "Rename " + account.getName() + " <Name>"));
		this.options.add(new PromptOption(this, "Transfer Money", new PickAccount(PDI, this, accounts, account)));
		this.options.add(new RemoveAccountOption(new ManageAccounts(PDI, previous, this.accounts), "Remove Account", this.account, this.accounts));
		Debug.print("Inside Add Options 2");
		//PassByOptionMenu menu =  new ShareAccountNav(PDI, new ManageAccount(PDI, previous, account, accounts), this.account);
		//Debug.print("Menu options null? " + (menu.getOptions() == null));
		//if(menu.getOptions().size() != 0) {
		//	this.options.add(new PromptOption(this, "Share Account", menu));
		//}
		Debug.print("Inside Add Options 3");
		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
