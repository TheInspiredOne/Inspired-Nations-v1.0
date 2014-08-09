package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.ToolBox.Datable;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNations.ToolBox.Payable;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.MenuLoops.FindAddress.PickGovGeneral;

public class PickGovToPay extends PickGovGeneral {

	Payable accounts;
	
	public PickGovToPay(PlayerData PDI, Payable accountsFrom, Menu previous, Menu next) {
		super(PDI, previous, next);
		this.accounts = accountsFrom;
	}
	public PickGovToPay(PlayerData PDI, Payable accountsFrom, Menu previous, Menu next, Datable<InspiredGov> superGov) {
		super(PDI, previous, next, superGov);
		this.accounts = accountsFrom;
	}

	@Override
	public boolean check(InspiredGov gov) {
		return true;
	}
	@Override
	public String postTabListPreOptionsText() {
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
