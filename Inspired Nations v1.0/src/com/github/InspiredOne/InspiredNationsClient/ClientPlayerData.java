package com.github.InspiredOne.InspiredNationsClient;

import java.rmi.RemoteException;

import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNationsClient.Exceptions.PlayerOfflineException;
import com.github.InspiredOne.InspiredNationsClient.Remotes.ClientPlayerDataInter;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;

public class ClientPlayerData implements ClientPlayerDataInter {

	private PlayerID id;
	public ClientPlayerData(PlayerID id) {
		this.id = id;
		try {
			InspiredNationsClient.server.registerPlayer(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getName() throws PlayerOfflineException, RemoteException {
		Player player = InspiredNationsClient.plugin.getServer().getPlayer(id.getID());
		if(player == null) {
			throw new PlayerOfflineException();
		}
		return player.getName();
	}
}
