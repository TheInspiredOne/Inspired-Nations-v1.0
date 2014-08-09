package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.Account;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.ToolBox.Datable;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.MenuLoops.FindAddress.PickGovGeneral;

public class PickGovernmentToShare extends PickGovGeneral {

	Account account;
	
	public PickGovernmentToShare(PlayerData PDI, Menu previous, Menu next, Account account,
			Datable<InspiredGov> superGov) {
		super(PDI, previous, next, superGov);
		this.account = account;
	}

	public PickGovernmentToShare(PlayerData PDI, Menu previous, Menu next, Account account) {
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
