package com.github.InspiredOne.InspiredNationsServer.Remotes.Implem;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;
import com.github.InspiredOne.InspiredNationsServer.Log;
import com.github.InspiredOne.InspiredNationsServer.PlayerData;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataInter;
import com.github.InspiredOne.InspiredNationsServer.Remotes.ServerPortalInter;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;

public class ServerPortal implements ServerPortalInter {

	@Override
	public void registerClient(ClientID id) throws RemoteException {
		InspiredNationsServer.clients.add(id);
		Log.info("Client has been registered: " + id.getName());
	}

	@Override
	public void unregisterClient(ClientID id) throws RemoteException {
		boolean contains = InspiredNationsServer.clients.remove(id);
		if(contains) {
			Log.info("Client Removed Successfully: ");
		}
		else {
			Log.info("Client Does Not Exist: " + id.getName());
		}
	}

	@Override
	public void registerPlayer(PlayerID id) throws RemoteException {
		if(InspiredNationsServer.playerdata.containsKey(id)) {
			return;
		}
		else {
			PlayerData player = new PlayerData(id);
			Log.info(player.getName() + " has not been added to playerdata yet.");
			InspiredNationsServer.playerdata.put(id, player);
		}
	}

	@Override
	public PlayerDataInter getPlayer(PlayerID id) throws RemoteException {
		return InspiredNationsServer.playerdata.get(id);
	}

}
