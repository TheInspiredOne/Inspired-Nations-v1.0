package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.HUD.MenuLoops.FindAddress.PickGovGeneral;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.Datable;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNationsServer.Economy.Payable;
import com.github.InspiredOne.InspiredNationsServer.Remotes.InspiredGovPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;


public class PickGovToPay extends PickGovGeneral {

	Payable accounts;
	
	public PickGovToPay(PlayerDataPortal PDI, Payable accountsFrom, Menu previous, Menu next) {
		super(PDI, previous, next);
		this.accounts = accountsFrom;
	}
	public PickGovToPay(PlayerDataPortal PDI, Payable accountsFrom, Menu previous, Menu next, Datable<InspiredGovPortal> superGov) throws RemoteException {
		super(PDI, previous, next, superGov);
		this.accounts = accountsFrom;
	}

	@Override
	public boolean check(InspiredGovPortal gov) {
		return true;
	}
	@Override
	public String postTabListPreOptionsText() throws RemoteException {
		return MenuTools.oneLineWallet("", PDI, accounts);
	}
	@Override
	public String getHeader() {
		return "Select Government To Pay";
	}
	@Override
	public void addOptions() {
		if(this.getData().getAllSubGovsAndFacilitiesJustBelow().size() > 0) {
			this.options.add(new PromptOption(this, "Search Under", new PickGovToPay(PDI, accounts, this, this.next, this.getData())));
		}
		if(PDI.getOwnership(this.getData().getClass()).contains(this.getData())) {
			this.options.add(new PromptOption(this, "Transfer Money", new PickAccount(PDI, this, this.getData().getAccounts(), accounts)));
		}
		else {
			this.options.add(new PayAccountOption(PDI, this, "Pay " + this.getData().getTypeName() + " <amount>", accounts, this.getData(), PDI));
		}
		
	}
	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
