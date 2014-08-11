package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Player;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.HUD.PromptOption;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;


public class ColorMenu extends OptionMenu {

	public ColorMenu(PlayerDataPortal PDI) throws RemoteException {
		
		super(PDI);
		
	}

	@Override
	public Menu getPreviousMenu() throws RemoteException {
		return new SettingsMenu(PDI);
		
	}

	@Override
	public boolean getPassBy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Menu getPassTo() {
		return this.getSelfPersist();
	}

	@Override
	public String getHeader() {
		return "Choose your theme here!";
	}

	@Override
	public String getPreOptionText() {
		// TODO somehow record Custom or Default Theme
		return "";
	}

	@Override
	public void addOptions() throws RemoteException {
		this.options.add(new DefaultTheme(this, "Default"));
		this.options.add(new OceanTheme(this, "Ocean Theme"));
		this.options.add(new PromptOption(this, "Custom Theme", new CustomTheme(PDI)));
	}
	
	@Override
	public void addActionManagers() {
		
	}

}
