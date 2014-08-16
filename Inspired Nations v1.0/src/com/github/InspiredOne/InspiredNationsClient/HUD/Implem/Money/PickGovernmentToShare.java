package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import com.github.InspiredOne.InspiredNationsClient.HUD.MenuLoops.FindAddress.PickGovGeneral;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;

public class PickGovernmentToShare extends PickGovGeneral {

	AccountPortal account;
	
	public PickGovernmentToShare(PlayerDataPortal PDI, Menu previous, Menu next, AccountPortal account,
			Datable<InspiredGov> superGov) {
		super(PDI, previous, next, superGov);
		this.account = account;
	}

	public PickGovernmentToShare(PlayerDataPortal PDI, Menu previous, Menu next, AccountPortal account) {
		super(PDI, previous, next);
		this.account = account;
	}

	@Override
	public boolean check(InspiredGov gov) {
		return true;
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}

	@Override
	public String getHeader() {
		return "Pick Government To Share Account";
	}

	@Override
	public void addOptions() {
		this.options.add(new ShareAccountOption(this, "Share Account With " + this.getData().getTypeName(), account, this.getData().getAccounts()));

	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
