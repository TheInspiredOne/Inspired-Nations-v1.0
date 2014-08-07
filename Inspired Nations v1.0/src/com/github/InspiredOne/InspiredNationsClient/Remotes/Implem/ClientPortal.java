package com.github.InspiredOne.InspiredNationsClient.Remotes.Implem;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.InspiredNationsClient;
import com.github.InspiredOne.InspiredNationsClient.Remotes.ClientPortalInter;

public class ClientPortal implements ClientPortalInter {

	@Override
	public void signal() throws RemoteException {
		InspiredNationsClient.logger.info("Client portal signaled.");
	}

}
