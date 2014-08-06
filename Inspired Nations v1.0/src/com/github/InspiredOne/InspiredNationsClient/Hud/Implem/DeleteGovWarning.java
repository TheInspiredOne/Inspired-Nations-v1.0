package com.github.InspiredOne.InspiredNationsClient.Hud.Implem;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov.ManageGov;

public class DeleteGovWarning extends OptionMenu {
	
	OwnerGov gov;

	public DeleteGovWarning(PlayerData PDI, OwnerGov ownerGov) {
		super(PDI);
		gov = ownerGov;
	}
	
	@Override
	public String getPreOptionText() {
		// TODO Auto-generated method stub
		return "WARNING! This is for FOREVER. Are you sure you want to proceed?";
	}

	@Override
	public void addOptions() {
		
		this.options.add(new DeleteGovOption(new PlayerCitizenship(PDI), "Yes", gov));
		this.options.add(new DoNotDeleteOption(new ManageGov(PDI, gov), "No"));
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Menu getPreviousMenu() {
		// TODO Auto-generated method stub
		return new ManageGov(PDI, gov);
	}

	@Override
	public boolean getPassBy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Menu getPassTo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHeader() {
		// TODO Auto-generated method stub
		return "";
	}
	
	public class DoNotDeleteOption extends Option {
		
		Menu manageGov;
		
		public DoNotDeleteOption(OptionMenu menu, String label) {
			super(menu, label);
			
			manageGov = menu;
		}


		@Override
		public Menu response(String input) {
			
			return manageGov;
		}
		
	}

}
