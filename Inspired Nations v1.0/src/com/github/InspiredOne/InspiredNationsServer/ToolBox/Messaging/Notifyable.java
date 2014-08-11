package com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsServer.Remotes.AlertPortal;

public interface Notifyable {

	/**
	 * Makes sure this receiver gets this message
	 * @throws RemoteException 
	 */
	public void sendNotification(AlertPortal msg) throws RemoteException;
	
}
