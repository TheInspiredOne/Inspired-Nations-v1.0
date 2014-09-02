package com.github.InspiredOne.InspiredNationsClient.HUD.MenuLoops.FindAddress;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.InspiredNationsClient;
import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;


public abstract class PickPlayerGeneral extends TabSelectOptionMenu<PlayerID> {

	protected Menu previous;
	
	public PickPlayerGeneral(PlayerDataPortal PDI, Menu previous) throws RemoteException {
		super(PDI);
		this.previous = previous;
	}

	@Override
	public Menu getPreviousPrompt() throws RemoteException {
		return previous;
	}

	/**
	 * Checks to see if the player should be listed in the tab options list.
	 * @param player
	 * @return
	 * @throws RemoteException 
	 */
	public abstract boolean check(PlayerID player) throws RemoteException; 


	@Override
	public String postTabListPreOptionsText() throws RemoteException {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void addTabOptions() throws RemoteException {
		for(PlayerID player:InspiredNationsClient.server.getPlayerData()) {
			if(check(player)) {
				this.taboptions.add(player);
			}
		}
	}
}
