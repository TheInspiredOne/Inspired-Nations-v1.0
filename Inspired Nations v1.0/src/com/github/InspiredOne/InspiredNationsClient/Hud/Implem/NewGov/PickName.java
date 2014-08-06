package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.NewGov;

import java.util.ArrayList;
import java.util.List;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNationsClient.Hud.InputMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;

public class PickName<T extends OwnerGov> extends InputMenu {

	GovFactory<T> Govf;
	public PickName(PlayerData PDI, GovFactory<T> Govf) {
		super(PDI);
		this.Govf = Govf;
	}

	@Override
	public String validate(String input) {
		
		boolean allowed = true;
		
		for(InspiredGov gov: InspiredNations.regiondata.get(Govf.getGov().getClass())) {
			if(gov.getSuperGovObj().equals(Govf.getGov().getSuperGovObj()) && gov.getName().equalsIgnoreCase(input)) {
				allowed = false;
			}
		}
		if(allowed) {
			return MenuError.NO_ERROR();
		}
		else {
			return MenuError.NAME_ALREADY_TAKEN(Govf.getGov().getClass(), this.getPlayerData());
		}
	}

	@Override
	public void useInput(String input) {
		Govf = Govf.withName(input);
	}

	@Override
	public String getHeader() {
		return "Type Name";
	}

	@Override
	public String getInstructions() {
		return "Type the name that you would like to use for this " + Govf.getGov().getTypeName() + ".";
	}

	@Override
	public Menu nextMenu() {
		this.unloadNonPersist();
		return new PickMoneyName<T>(PDI, Govf);
	}

	@Override
	public Menu getPreviousMenu() {
		return new PickSuperGov<T>(PDI, Govf);
	}

	@Override
	public boolean getPassBy() {
		return false;
	}
	@Override
	public void addTabOptions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}
}
