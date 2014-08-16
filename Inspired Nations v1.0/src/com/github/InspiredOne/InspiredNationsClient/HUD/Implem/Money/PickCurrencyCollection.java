package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNationsServer.Economy.Payable;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.CurrencyAccountPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;

public class PickCurrencyCollection extends TabSelectOptionMenu<CurrencyAccountPortal> {

	Menu previous;
	AccountPortal account;
	Payable accountFrom;
	public PickCurrencyCollection(PlayerDataPortal PDI, Menu previous, AccountPortal account, Payable accountFrom) throws RemoteException {
		super(PDI);
		this.previous = previous;
		this.account = account;
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
		return "Pick Currency To Transfer Money Into";
	}

	@Override
	public void addTabOptions() throws RemoteException {
		for(int i=0; i<account.getCurrencySize(); i++) {
			this.taboptions.add(account.getCurrency(i));
		}
	}

	@Override
	public void addOptions() {
		if(taboptions.size() > 0) {
			this.options.add(new PayAccountOption(PDI, this, "Transfer Funds <amount>", accountFrom, this.getData(), PDI));
		}
		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}
	
}
