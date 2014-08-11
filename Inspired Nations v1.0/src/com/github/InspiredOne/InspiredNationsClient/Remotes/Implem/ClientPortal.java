package com.github.InspiredOne.InspiredNationsClient.Remotes.Implem;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.bukkit.Bukkit;

import com.github.InspiredOne.InspiredNationsClient.InspiredNationsClient;
import com.github.InspiredOne.InspiredNationsClient.Exceptions.PlayerOfflineException;
import com.github.InspiredOne.InspiredNationsClient.Remotes.ClientLocationPortalInter;
import com.github.InspiredOne.InspiredNationsClient.Remotes.ClientPlayerDataInter;
import com.github.InspiredOne.InspiredNationsClient.Remotes.ClientPortalInter;
import com.github.InspiredOne.InspiredNationsClient.Remotes.ClientWorldPortalInter;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.TaxTimerEvent;
import com.github.InspiredOne.InspiredNationsServer.Debug;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Point3D;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.WorldID;

public class ClientPortal implements ClientPortalInter {

	@Override
	public void signal() throws RemoteException {
		InspiredNationsClient.logger.info("Client portal signaled.");
	}

	@Override
	public ClientPlayerDataInter getPlayer(PlayerID id) throws PlayerOfflineException, RemoteException {
		ClientPlayerDataInter output = InspiredNationsClient.playerdata.get(id);
		Debug.info("Inside GetPlayer 1 of client portal");
		if(output == null) {
			Debug.info("getPlayer in ClientPortal returned null");
			throw new PlayerOfflineException();
		}
		else {
			return output;
		}
	}

	@Override
	public ClientWorldPortalInter getWorld(WorldID world) throws RemoteException,
			NotBoundException {
		return new ClientWorldPortal(world);
	}

	@Override
	public ClientLocationPortalInter getLocation(Point3D point)
			throws RemoteException, NotBoundException {
		return new ClientLocationPortal(point);
	}

	@Override
	public void triggerTaxTimer() throws RemoteException {
		TaxTimerEvent event = new TaxTimerEvent(InspiredNationsClient.server.getTaxTimer());
		Bukkit.getServer().getPluginManager().callEvent(event);
	}
	
	

}
