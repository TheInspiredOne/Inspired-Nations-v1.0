package com.github.InspiredOne.InspiredNationsClient.ToolBox;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsServer.Exceptions.NameAlreadyTakenException;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;

/**
 * Used by menus to list a bunch of named objects
 * @author Jedidiah E. Phillips
 *
 */
public interface Nameable extends Remote{
	/**
	 * Used to get the name of the object
	 * @return	the name of the object
	 */
	public String getName() throws RemoteException;
	
	public void setName(String name) throws NameAlreadyTakenException, RemoteException;
	/**
	 * Gets a string that includes the name and more information in-line.
	 * @return
	 */
	public String getDisplayName(PlayerID viewer) throws RemoteException;
}
