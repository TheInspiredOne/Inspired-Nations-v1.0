package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Player;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.HUD.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.HUD.Implem.PlayerDirectory;
import com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ToggleTimerOption;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;

public class SettingsMenu extends OptionMenu {

	public SettingsMenu(PlayerDataPortal PDI) throws RemoteException {
		super(PDI);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getPreOptionText() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void addOptions() throws RemoteException {
		String state;
		if (PDI.isTimerState()) {
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
	public Menu getPreviousMenu() throws RemoteException {
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
