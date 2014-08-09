package com.github.InspiredOne.InspiredNationsClient.Remotes.Implem;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.github.InspiredOne.InspiredNationsClient.Remotes.ClientWorldPortalInter;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.WorldID;

public class ClientWorldPortal extends UnicastRemoteObject implements ClientWorldPortalInter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3660388116824906864L;
	private WorldID world;
	
	
	protected ClientWorldPortal(WorldID world) throws RemoteException {
		super();
		this.world = world;
	}
}
