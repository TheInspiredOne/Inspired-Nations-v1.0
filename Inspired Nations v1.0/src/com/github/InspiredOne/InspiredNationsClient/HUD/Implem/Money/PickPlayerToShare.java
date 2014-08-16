package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.InspiredNationsClient;
import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.MenuLoops.FindAddress.PickPlayerGeneral;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;

public class PickPlayerToShare extends PickPlayerGeneral {

	AccountPortal account;
	
	public PickPlayerToShare(PlayerDataPortal PDI, Menu previous, AccountPortal account) throws RemoteException {
		super(PDI, previous);
		this.account = account;
	}

	@Override
	public boolean check(PlayerID player) throws RemoteException {
		if(PDI.getPlayerID().equals(player)) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}

	@Override
	public String getHeader() {
		return "Pick Player To Share Account";
	}

	@Override
	public void addOptions() throws RemoteException {
		this.options.add(new ShareAccountOption(this, "Share Account With " + this.getData(), account, InspiredNationsClient.server.getPlayer(this.getData()).getAccounts()));
		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
