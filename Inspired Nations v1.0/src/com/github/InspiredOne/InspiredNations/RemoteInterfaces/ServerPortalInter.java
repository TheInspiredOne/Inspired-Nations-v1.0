package com.github.InspiredOne.InspiredNations.RemoteInterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNations.ToolBox.ServerID;

public interface ServerPortalInter extends Remote {
	public void BindClient(ServerID server) throws RemoteException;
}
