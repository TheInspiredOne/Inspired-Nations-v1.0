package com.github.InspiredOne.InspiredNationsServer.SerializableIDs;

import java.io.Serializable;

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
}
