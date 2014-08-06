package com.github.InspiredOne.InspiredNationsClient.Hud.Implem;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class ToggleTimerOption extends Option {
	
	PlayerData PDI;

	public ToggleTimerOption(OptionMenu menu, String label, String description) {
		super(menu, label, description);
		this.PDI = menu.getPlayerData();
	}

	@Override
	public Menu response(String input) {
		//InspiredNations.taxTimer.taxreadout = !InspiredNations.taxTimer.taxreadout;
		PDI.TimerState = !PDI.TimerState;
		return menu;
	}

}
