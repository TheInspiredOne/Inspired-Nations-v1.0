package com.github.InspiredOne.InspiredNationsClient.RemoteInterfaces.implem;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNationsClient.RemoteInterfaces.PlayerClientInter;
import com.github.InspiredOne.InspiredNationsClient.RemoteInterfaces.ClientPortalInter;

public class ClientPortal implements ClientPortalInter {

	@Override
	public PlayerClientInter getPlayer(PlayerID player) throws RemoteException {
		return new PlayerClient(player);
	}

}
