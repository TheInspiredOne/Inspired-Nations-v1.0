package com.github.InspiredOne.InspiredNations.Hud.ManageGov;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.NoSubjects;
import com.github.InspiredOne.InspiredNations.Hud.Menu;
import com.github.InspiredOne.InspiredNations.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNations.Hud.PromptOption;
import com.github.InspiredOne.InspiredNations.Hud.Implem.MainHud;
import com.github.InspiredOne.InspiredNations.Hud.Implem.NewGov.PickSuperGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.ContextData;

public class ManageGov extends OptionMenu {

	NoSubjects gov;
	public ManageGov(PlayerData PDI, NoSubjects gov) {
		super(PDI);
		this.gov = gov;

	}

	@Override
	public String getPreOptionText() {
		return "";
	}

	@Override
	public Menu PreviousMenu() {
		return new PickManageSuperGov(PDI, gov.getClass(), gov.getSuperGovObj());
	}

	@Override
	public String getHeader() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public boolean getPassBy() {
		return false;
	}

	@Override
	public Menu getPassTo() {
		return null;
	}

	@Override
	public void init() {
		this.options.add(new PromptOption(this, "option 1", new MainHud(PDI)));
	}

}
