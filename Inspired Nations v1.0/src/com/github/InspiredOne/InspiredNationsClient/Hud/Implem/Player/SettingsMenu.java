package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.Player;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.PlayerDirectory;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ToggleTimerOption;

public class SettingsMenu extends OptionMenu {

	public SettingsMenu(PlayerData PDI) {
		super(PDI);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getPreOptionText() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void addOptions() {
		String state;
		if (PDI.TimerState) {
			state = "On";
		} else {
			state = "Off";
		}
		this.options.add(new ToggleTimerOption(this, "Toggle Timer", "(" + state + ")"));
		this.options.add(new PromptOption(this, "Set Menu Colors", new ColorMenu(PDI)));
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Menu getPreviousMenu() {
		// TODO Auto-generated method stub
		return new PlayerDirectory(PDI);
	}

	@Override
	public boolean getPassBy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Menu getPassTo() {
		// TODO Auto-generated method stub
		return this.getSelfPersist();
	}

	@Override
	public String getHeader() {
		// TODO Auto-generated method stub
		return "Change your settings here:";
	}
	

}
