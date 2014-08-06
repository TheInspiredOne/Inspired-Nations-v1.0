package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.Facility;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;

public class ManageFacility extends OptionMenu {

	private Facility fac;
	private Menu previous;
	public ManageFacility(PlayerData PDI, Menu previous, Facility fac) {
		super(PDI);
		this.fac = fac;
		this.previous = previous;
	}

	@Override
	public String getPreOptionText() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getHeader() {
		return "Manage " + fac.getName();
	}

	@Override
	public Menu getPreviousMenu() {
		return previous;
	}

	@Override
	public boolean getPassBy() {
		return !fac.getSuperGovObj().getFacilities().contains(fac);
	}

	@Override
	public Menu getPassTo() {
		return previous;
	}

	@Override
	public void addOptions() {
		this.options.add(new PromptOption(this, "Manage Money", new ManageGovMoney(PDI, this, fac)));
		this.options.add(new PromptOption(this, "Claim Land", new PickClaimType(PDI, this, fac)));
		this.options.add(new PromptOption(this, "Change Protection Level", new ProtectionLevels(PDI, this, fac),
				"(" +fac.getProtectionlevel() +")"));
		this.options.add(new RemoveFacilityOption(this, "Remove Facility", fac));
		this.fac.setFunctionOptions(PDI, this);
		ManageGov.addFacilityOptions(PDI, this, this, this.fac);
	}

	@Override
	public void addActionManagers() {
		
	}

}
