package com.github.InspiredOne.InspiredNationsClient.Remotes;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.Exceptions.PlayerOfflineException;

public interface ClientPlayerDataInter extends Remote {
	public String getName() throws PlayerOfflineException, RemoteException;
}
