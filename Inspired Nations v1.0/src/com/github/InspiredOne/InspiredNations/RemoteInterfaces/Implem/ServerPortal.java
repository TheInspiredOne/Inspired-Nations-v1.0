package com.github.InspiredOne.InspiredNations.RemoteInterfaces.Implem;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.RemoteInterfaces.ServerPortalInter;
import com.github.InspiredOne.InspiredNations.ToolBox.ServerID;

public class ServerPortal implements ServerPortalInter {

	@Override
	public void BindClient(ServerID server) throws RemoteException {
		InspiredNations.clients.add(server);

	}

}
