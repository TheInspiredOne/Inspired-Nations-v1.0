package com.github.InspiredOne.InspiredNationsClient.Remotes.Implem;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

import com.github.InspiredOne.InspiredNationsClient.Remotes.ClientLocationPortalInter;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Point3D;

public class ClientLocationPortal extends UnicastRemoteObject implements
		ClientLocationPortalInter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6397579926081281454L;
	private Point3D location;
	
	public ClientLocationPortal(Point3D location) throws RemoteException {
		this.location = location;
	}

}
