package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.Player;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.ToolBox.Theme;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;

public class ColorMenu extends OptionMenu {

	public ColorMenu(PlayerData PDI) {
		
		super(PDI);
		
	}

	@Override
	public Menu getPreviousMenu() {
		return new SettingsMenu(PDI);
		
	}

	@Override
	public boolean getPassBy() {
		// TODO Auto-generated method stub
		Debug.print("gPB");
		return false;
	}

	@Override
	public Menu getPassTo() {
		return this.getSelfPersist();
	}

	@Override
	public String getHeader() {
		Debug.print("get Header");
		return "Choose your theme here!";
	}

	@Override
	public String getPreOptionText() {
		// TODO somehow record Custom or Default Theme
		return "";
	}

	@Override
	public void addOptions() {
		Debug.print("options for Color Menu");
		this.options.add(new DefaultTheme(this, "Default"));
		this.options.add(new OceanTheme(this, "Ocean Theme"));
		this.options.add(new PromptOption(this, "Custom Theme", new CustomTheme(PDI)));
	}
	
	@Override
	public void addActionManagers() {
		
	}

}
