package com.github.InspiredOne.InspiredNationsClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNationsClient.Exceptions.PlayerOfflineException;
import com.github.InspiredOne.InspiredNationsClient.Listeners.ActionManager;
import com.github.InspiredOne.InspiredNationsClient.Remotes.ClientPlayerDataInter;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Point3D;

public class ClientPlayerData extends UnicastRemoteObject implements ClientPlayerDataInter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -339727338561336697L;
	private PlayerID id;
	public ArrayList<ActionManager<?>> actionmanagers;
	private Conversation con;
	
	public ClientPlayerData(PlayerID id) throws RemoteException, PlayerOfflineException{
		this.id = id;
		try {
			InspiredNationsClient.server.registerPlayer(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public Player getPlayer() throws PlayerOfflineException , RemoteException{
		return InspiredNationsClient.plugin.getServer().getPlayer(id.getID());
	}
	
	@Override
	public String getName() throws PlayerOfflineException, RemoteException {
		Player player = this.getPlayer();
		if(player == null) {
			throw new PlayerOfflineException();
		}
		return player.getName();
	}

	@Override
	public boolean isConversing() throws RemoteException, PlayerOfflineException {
		return this.getPlayer().isConversing();
	}

	@Override
	public void sendMessage(String msg) throws PlayerOfflineException,
			RemoteException {
		this.getPlayer().sendMessage(msg);
	}

	public Conversation getCon() {
		return con;
	}

	public void setCon(Conversation con) {
		this.con = con;
	}

	@Override
	public PlayerID getPlayerID() throws RemoteException {
		return this.id;
	}

	@Override
	public void sendRawMessage(String msg) throws PlayerOfflineException,
			RemoteException {
		InspiredNationsClient.plugin.getServer().getPlayer(id.getID()).sendRawMessage(msg);
	}

	@Override
	public Point3D getLocation() throws RemoteException, PlayerOfflineException {
		return new Point3D(this.getPlayer().getLocation(), InspiredNationsClient.id);
	}
}
