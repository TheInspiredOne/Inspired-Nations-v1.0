package com.github.InspiredOne.InspiredNationsClient.RemoteInterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.ToolBox.Point3D;

public interface PlayerClientInter extends Remote {
	public Point3D getLocation() throws RemoteException;
	public boolean isConversing() throws RemoteException;
}
