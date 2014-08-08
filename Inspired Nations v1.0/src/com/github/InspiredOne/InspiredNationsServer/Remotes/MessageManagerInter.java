package com.github.InspiredOne.InspiredNationsServer.Remotes;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging.Alert;

public interface MessageManagerInter extends Remote {

	public int getMissedSize() throws RemoteException;
	public void setMissedSize(int size) throws RemoteException;
	public ArrayList<Alert> getMessages() throws RemoteException;
	public String pushMessageContent() throws RemoteException;
	public void sendChatMessage(String msg) throws RemoteException;
	public void clearMenuVisible() throws RemoteException;
	public void receiveError(String msg) throws RemoteException;
	public void receiveAlert(Alert alert, boolean refresh) throws RemoteException;
	
}
