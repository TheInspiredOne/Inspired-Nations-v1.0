package com.github.InspiredOne.InspiredNationsServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.github.InspiredOne.InspiredNationsServer.Remotes.ServerPortalInter;
import com.github.InspiredOne.InspiredNationsServer.Remotes.Implem.ServerPortal;

public class InspiredNationsServer {

	public static void main(String[] args) {
		try {
            Registry registry = null;
            try {
                registry = LocateRegistry.createRegistry(1099);
            } catch (RemoteException e) {
                registry = LocateRegistry.getRegistry(1099);
                e.printStackTrace();
            }
            
            ServerPortalInter portal = new ServerPortal();
            ServerPortalInter stub = (ServerPortalInter) UnicastRemoteObject.exportObject(portal, 0);
            registry.rebind("portal", stub);
            System.out.print("Server Portal Bound");
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

}
