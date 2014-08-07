package com.github.InspiredOne.InspiredNationsServer.Remotes;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;

public interface ServerPortalInter extends Remote {
	public void registerClient(ClientID id) throws RemoteException;
	public void unregsiterClient(ClientID id) throws RemoteException;
}
