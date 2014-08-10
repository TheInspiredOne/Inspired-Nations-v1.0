package com.github.InspiredOne.InspiredNationsClient.HUD.Implem;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.InspiredNationsClient;
import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.HUD.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataInter;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;

public class PlayerDirectory extends TabSelectOptionMenu<PlayerID> {

	public PlayerDirectory(PlayerDataInter PDI) throws RemoteException {
		super(PDI);
	}

	@Override
	public Menu getPreviousPrompt() throws RemoteException {
		return new MainHud(PDI);
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}

	@Override
	public String getHeader() {
		return "Player Directory";
	}

	@Override
	public void addTabOptions() throws RemoteException {
		for(PlayerID player:InspiredNationsClient.server.getPlayerData().keySet()) {
			this.taboptions.add(player);
		}
	}

	@Override
	public void addOptions() throws RemoteException {
		options.add(new PromptOption(this, "Profile", new PlayerProfile(PDI,InspiredNationsClient.server.getPlayer(this.getData()))));
	}

	@Override
	public void addActionManagers() {
		
	}
}
