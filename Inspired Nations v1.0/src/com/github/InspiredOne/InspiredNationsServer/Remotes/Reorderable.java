package com.github.InspiredOne.InspiredNationsServer.Remotes;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Reorderable extends Remote {
	/**
	 * moves the option up
	 * @param portal
	 * @return
	 * @throws RemoteException
	 */
	public int moveUp(int start) throws RemoteException;
	/**
	 * moves the option down
	 * @param portal
	 * @return
	 * @throws RemoteException
	 */
	public int moveDown(int start) throws RemoteException;
}
