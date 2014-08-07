package com.github.InspiredOne.InspiredNationsServer;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.Exceptions.PlayerOfflineException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NameAlreadyTakenException;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataInter;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;

public class PlayerData implements PlayerDataInter{

	private PlayerID id;
	private String name;
	
	public PlayerData(PlayerID id) {
		this.id = id;
		name = id.getName();
	}

	public PlayerID getId() {
		return id;
	}

	@Override
	public String getName() throws RemoteException {
		for(ClientID client:InspiredNationsServer.clients) {
			try {
				name = client.getClientPortal().getPlayer(id).getName();
			} catch (PlayerOfflineException e) {

			}
		}
		return name;
	}

	@Override
	public void setName(String name) throws NameAlreadyTakenException, RemoteException {
		
	}

	@Override
	public String getDisplayName(PlayerID viewer) throws RemoteException {
		return this.getName();
	}


}
