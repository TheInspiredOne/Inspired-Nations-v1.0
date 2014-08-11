package com.github.InspiredOne.InspiredNationsServer.Remotes;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TaxTimerPortal extends Remote {
	
	public int getCycleLength() throws RemoteException;
	
	public void setCycleLength(int cycle) throws RemoteException;

	public int getSecondsLeft() throws RemoteException;
	
	public String getTimeLeftReadout() throws RemoteException;
	
	public void startTimer() throws RemoteException;
	
	public double getFractionLeft() throws RemoteException;
	
	public void collectTaxes() throws RemoteException;
}
