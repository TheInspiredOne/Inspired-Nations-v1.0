package com.github.InspiredOne.InspiredNationsServer.Remotes;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Calendar;

import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;


public interface AlertPortalInter extends Remote, Nameable, Serializable {

	public boolean expired() throws RemoteException;
	public void setExpired(boolean exp) throws RemoteException;
	public Calendar getCalendar() throws RemoteException;
	public boolean menuVisible() throws RemoteException;
	
	public abstract String getMessage(PlayerDataInter receiver) throws RemoteException;
	
	/**
	 * Returns true if the alert should remain after the player switches to a new menu.
	 * @return
	 */
	public abstract boolean  menuPersistent() throws RemoteException;
	
	public void incrementStack() throws RemoteException;
}
