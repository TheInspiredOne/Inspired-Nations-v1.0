package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.HUD.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNationsServer.Economy.Payable;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountCollectionPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;


public class PickAccount extends TabSelectOptionMenu<AccountPortal> {

	AccountCollectionPortal accounts;
	Payable accountFrom;
	Menu previous;
	public PickAccount(PlayerDataPortal PDI, Menu previous, AccountCollectionPortal collection, Payable accountFrom) throws RemoteException {
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
	public String postTabListPreOptionsText() throws RemoteException {
		return MenuTools.oneLineWallet("", PDI, accountFrom);
	}

	@Override
	public String getHeader() {
		return "Pick Account To Transfer Money Into";
	}

	@Override
	public void addTabOptions() throws RemoteException {
		for(int i = 0; i<accounts.getSize(); i++) {
			this.taboptions.add(accounts.get(i));
		}
		
	}

	@Override
	public void addOptions() throws RemoteException {
		this.options.add(new PromptOption(this, "Pick Currency Account", new PickCurrencyCollection(PDI, this, this.getData(), accountFrom)));
		this.options.add(new PayAccountOption(PDI, this, "Transfer Money <amount>", accountFrom, this.getData(), PDI));		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
	}
}
