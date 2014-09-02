package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.HUD.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsServer.Economy.Account;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountCollectionPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;

public class ManageAccounts extends TabSelectOptionMenu<AccountPortal> {

	Menu previous;
	AccountCollectionPortal accounts;
	
	public ManageAccounts(PlayerDataPortal PDI, Menu previous, AccountCollectionPortal accounts) throws RemoteException {
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
	public void addTabOptions() throws RemoteException {
		for(int i = 0; i<accounts.getSizeOf(); i++) {
			this.taboptions.add(accounts.get(i));
		}
	}

	@Override
	public void addOptions() throws RemoteException {
		if(taboptions.size() > 0) {
			this.options.add(new ChangeTabOrderOption<AccountPortal>(new ManageAccounts(PDI, previous, accounts), "Change Account Order <+/->", PDI.getAccounts(), this.getData()));
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
