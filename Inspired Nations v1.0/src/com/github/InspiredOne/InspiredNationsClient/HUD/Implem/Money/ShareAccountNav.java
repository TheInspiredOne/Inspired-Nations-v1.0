package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.MenuLoops.FindAddress.PickNavGeneral;
import com.github.InspiredOne.InspiredNationsClient.HUD.MenuLoops.FindAddress.PickPlayerGeneral;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;

public class ShareAccountNav extends PickNavGeneral {

	AccountPortal account;
	public ShareAccountNav(PlayerDataPortal PDI, Menu previous, AccountPortal account) {
		super(PDI, previous);
		this.account = account;
	}

	@Override
	public String getGovOptionText() {
		return "Pick Government";
	}

	@Override
	public String getPlayerOptionText() {
		return "Pick Player";
	}

	@Override
	public Menu getGovMenu() {
		return new PickGovernmentToShare(PDI, this, this, account, InspiredNations.global);
	}

	@Override
	public PickPlayerGeneral getPlayerMenu() throws RemoteException {
		return new PickPlayerToShare(PDI, this, account);
	}

	@Override
	public String getPreOptionText() {
		return "";
	}

	@Override
	public String getHeader() {
		return "Pick What You Would Like To Share With";
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
