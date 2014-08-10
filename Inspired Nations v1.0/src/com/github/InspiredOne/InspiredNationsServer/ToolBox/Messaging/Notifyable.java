package com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsServer.Remotes.AlertPortalInter;

public interface Notifyable {

	/**
	 * Makes sure this receiver gets this message
	 * @throws RemoteException 
	 */
	public void sendNotification(AlertPortalInter msg) throws RemoteException;
	
}
