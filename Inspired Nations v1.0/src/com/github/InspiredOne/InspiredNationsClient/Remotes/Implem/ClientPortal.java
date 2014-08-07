package com.github.InspiredOne.InspiredNationsClient.Remotes.Implem;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.InspiredNationsClient;
import com.github.InspiredOne.InspiredNationsClient.Exceptions.PlayerOfflineException;
import com.github.InspiredOne.InspiredNationsClient.Remotes.ClientPlayerDataInter;
import com.github.InspiredOne.InspiredNationsClient.Remotes.ClientPortalInter;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;

public class ClientPortal implements ClientPortalInter {

	@Override
	public void signal() throws RemoteException {
		InspiredNationsClient.logger.info("Client portal signaled.");
	}

	@Override
	public ClientPlayerDataInter getPlayer(PlayerID id) throws PlayerOfflineException, RemoteException {
		ClientPlayerDataInter output = InspiredNationsClient.playerdata.get(id);
		if(output == null) {
			throw new PlayerOfflineException();
		}
		else {
			return output;
		}
	}

}
