package com.github.InspiredOne.InspiredNationsClient.RemoteInterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNations.ToolBox.Point3D;

public interface WorldClientInter extends Remote {
	
	public LocationClientInter getLocation(Point3D point) throws RemoteException;
}
