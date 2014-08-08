package com.github.InspiredOne.InspiredNationsClient.Remotes;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.Exceptions.PlayerOfflineException;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;

public interface ClientPlayerDataInter extends Remote {
	public String getName() throws PlayerOfflineException, RemoteException;
	public boolean isConversing() throws PlayerOfflineException, RemoteException;
	public void sendMessage(String msg) throws PlayerOfflineException, RemoteException;
	public void sendRawMessage(String msg) throws PlayerOfflineException, RemoteException;
	public PlayerID getPlayerID() throws RemoteException;
}
