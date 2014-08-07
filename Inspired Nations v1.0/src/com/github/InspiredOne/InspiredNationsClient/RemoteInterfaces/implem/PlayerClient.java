package com.github.InspiredOne.InspiredNationsClient.RemoteInterfaces.implem;

import java.rmi.RemoteException;

import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNations.ToolBox.Point3D;
import com.github.InspiredOne.InspiredNationsClient.InspiredNationsClient;
import com.github.InspiredOne.InspiredNationsClient.RemoteInterfaces.PlayerClientInter;

public class PlayerClient implements PlayerClientInter {

	private Player player;
	public PlayerClient(Player player) {
		this.player = player;
	}
	public PlayerClient(PlayerID player) {
		this.player = InspiredNationsClient.plugin.getServer().getPlayer(player.getID());
	}
	@Override
	public Point3D getLocation() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
