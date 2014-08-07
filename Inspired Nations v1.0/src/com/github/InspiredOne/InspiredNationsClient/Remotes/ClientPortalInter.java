package com.github.InspiredOne.InspiredNationsClient.Remotes;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.Exceptions.PlayerOfflineException;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;

public interface ClientPortalInter extends Remote {
	public void signal() throws RemoteException;
	public ClientPlayerDataInter getPlayer(PlayerID id) throws PlayerOfflineException, RemoteException;
}
