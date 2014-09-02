package com.github.InspiredOne.InspiredNationsServer.Remotes;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;

public interface InspiredGovPortal extends Remote, Nameable {

	String getTypeName() throws RemoteException;

	AccountCollectionPortal getAccounts() throws RemoteException;

	void joinAccount(PlayerDataPortal pDI) throws RemoteException;

}
