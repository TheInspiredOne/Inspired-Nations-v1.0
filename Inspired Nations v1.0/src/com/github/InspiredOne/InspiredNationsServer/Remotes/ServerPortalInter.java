package com.github.InspiredOne.InspiredNationsServer.Remotes;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;

public interface ServerPortalInter extends Remote {
	public void registerClient(ClientID id) throws RemoteException;
	public void unregisterClient(ClientID id) throws RemoteException;
	public void registerPlayer(PlayerID id) throws RemoteException;
	public PlayerDataInter getPlayer(PlayerID id) throws RemoteException;
}
