package com.github.InspiredOne.InspiredNationsClient.Remotes;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.Exceptions.PlayerOfflineException;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Point3D;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.WorldID;

public interface ClientPortalInter extends Remote {
	public void signal() throws RemoteException;
	public ClientPlayerDataInter getPlayer(PlayerID id) throws PlayerOfflineException, RemoteException;
    public ClientWorldPortalInter getWorld(WorldID world) throws RemoteException, NotBoundException;
    public ClientLocationPortalInter getLocation(Point3D point) throws RemoteException, NotBoundException;
    public void triggerTaxTimer() throws RemoteException;
}
