package com.github.InspiredOne.InspiredNationsClient.RemoteInterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNations.ToolBox.WorldID;

public interface ClientPortalInter extends Remote {

	public PlayerClientInter getPlayer(PlayerID player) throws RemoteException;
	public WorldClientInter getWorld(WorldID world) throws RemoteException;
}
