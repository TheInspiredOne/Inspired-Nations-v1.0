package com.github.InspiredOne.InspiredNationsServer.SerializableIDs;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.github.InspiredOne.InspiredNationsClient.Remotes.ClientPortalInter;
import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;

public class ClientID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2428295462040911522L;

	private String hostname;
	private int port;
	
	public ClientID(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
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
	public ClientPortalInter getClientPortal() {
		ClientPortalInter output = null;
		try {
	        String name = "portal";
	        Registry registry;
			registry = LocateRegistry.getRegistry(hostname, port);
	        output = (ClientPortalInter) registry.lookup(ClientID.generateRegAddress(hostname, port) + name);
		} catch (RemoteException | NotBoundException e) {
			InspiredNationsServer.clients.remove(this);
			System.out.print("Lost Connection to client: " + ClientID.generateRegAddress(hostname, port));
			e.printStackTrace();
		}
		return output;
	}
	public static String generateRegAddress(String hostname, int port) {
		return hostname + ":" + port + ":"; 
	}
	public String getName() {
		return ClientID.generateRegAddress(hostname, port);
	}
	
	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(hostname).
            append(port).
            toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof ClientID))
            return false;
        ClientID rhs = (ClientID) obj;

        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(hostname, rhs.hostname).
            append(port, rhs.port).
            isEquals();
    }
	
}
