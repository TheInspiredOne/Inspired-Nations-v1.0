package com.github.InspiredOne.InspiredNations.ToolBox;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.github.InspiredOne.InspiredNationsClient.RemoteInterfaces.ClientPortalInter;

public class ServerID {
	private String hostname;
	private int port;
	
	public ServerID(String hostname, int port) {
		this.setHostname(hostname);
		this.setPort(port);
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public ClientPortalInter getPortal() throws RemoteException, NotBoundException {
        String name = "portal";
        Registry registry = LocateRegistry.getRegistry(this.getHostname(), this.getPort());
        return (ClientPortalInter) registry.lookup(name);
		
	}
}
