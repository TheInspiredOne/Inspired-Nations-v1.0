package com.github.InspiredOne.InspiredNationsClient.HUD.Implem;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.Option;
import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataInter;

public class ToggleTimerOption extends Option {
	
	PlayerDataInter PDI;

	public ToggleTimerOption(OptionMenu menu, String label, String description) {
		super(menu, label, description);
		this.PDI = menu.getPlayerData();
	}

	@Override
	public Menu response(String input) throws RemoteException {
		//InspiredNations.taxTimer.taxreadout = !InspiredNations.taxTimer.taxreadout;
		PDI.setTimerState(!PDI.isTimerState());
		return menu;
	}

}
