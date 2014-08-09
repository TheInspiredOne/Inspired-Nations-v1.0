package com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging;

import java.rmi.RemoteException;

public interface Notifyable {

	/**
	 * Makes sure this receiver gets this message
	 * @throws RemoteException 
	 */
	public void sendNotification(Alert msg) throws RemoteException;
	
}
