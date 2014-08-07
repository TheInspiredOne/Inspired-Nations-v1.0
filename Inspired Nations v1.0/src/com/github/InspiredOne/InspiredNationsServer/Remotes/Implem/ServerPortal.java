package com.github.InspiredOne.InspiredNationsServer.Remotes.Implem;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;
import com.github.InspiredOne.InspiredNationsServer.Remotes.ServerPortalInter;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;

public class ServerPortal implements ServerPortalInter {

	@Override
	public void registerClient(ClientID id) throws RemoteException {
		InspiredNationsServer.clients.add(id);
		System.out.print("Client registered on Server");
	}

	@Override
	public void unregsiterClient(ClientID id) throws RemoteException {
		boolean contains = InspiredNationsServer.clients.remove(id);
		if(contains) {
			System.out.print("Client Removed Successfully");
		}
		else {
			System.out.print("Client does not exist");
		}
	}

}
