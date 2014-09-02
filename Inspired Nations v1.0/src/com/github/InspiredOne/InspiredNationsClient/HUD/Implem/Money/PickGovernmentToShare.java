package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.MenuLoops.FindAddress.PickGovGeneral;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.Datable;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.InspiredGovPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;

public class PickGovernmentToShare extends PickGovGeneral {

	AccountPortal account;
	
	public PickGovernmentToShare(PlayerDataPortal PDI, Menu previous, Menu next, AccountPortal account,
			Datable<InspiredGovPortal> superGov) throws RemoteException {
		super(PDI, previous, next, superGov);
		this.account = account;
	}

	public PickGovernmentToShare(PlayerDataPortal PDI, Menu previous, Menu next, AccountPortal account) {
		super(PDI, previous, next);
		this.account = account;
	}

	@Override
	public boolean check(InspiredGovPortal gov) {
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
	public void addOptions() throws RemoteException {
		this.options.add(new ShareAccountOption(this, "Share Account With " + this.getData().getTypeName(), account, this.getData().getAccounts()));

	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
