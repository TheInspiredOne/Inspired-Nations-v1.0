package com.github.InspiredOne.InspiredNationsClient.Remotes;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientPortalInter extends Remote {
	public void signal() throws RemoteException;
}
