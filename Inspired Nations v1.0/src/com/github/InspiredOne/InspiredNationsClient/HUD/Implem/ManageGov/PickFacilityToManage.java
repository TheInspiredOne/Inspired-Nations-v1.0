package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.Facility;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;

public class PickFacilityToManage<T extends Facility> extends TabSelectOptionMenu<T> {

	Class<T> factype;
	Menu previous;
	InspiredGov supergov;
	
	public PickFacilityToManage(PlayerData PDI, Menu previous, InspiredGov supergov, Class<T> factype) {
		super(PDI);
		this.factype = factype;
		this.previous = previous;
		this.supergov = supergov;
	}

	@Override
	public Menu getPreviousPrompt() {
		return previous;
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}

	@Override
	public String getHeader() {
		return "Pick " + GovFactory.getGovInstance(factype).getTypeName();
	}
	
	@Override
	public boolean getPassBy() {
		Debug.print("In getPassBy();");
		if(this.taboptions.size() <= 1) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Menu getPassTo() {
		Debug.print("In getPassTo();");
		return new ManageFacility(PDI, previous, this.getData());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addTabOptions() {
		for(Facility fac:supergov.getFacilities()) {
			if(fac.getClass().equals(factype)) {
				this.taboptions.add((T) fac);
			}
		}		
	}

	@Override
	public void addOptions() {
		if(this.taboptions.size() != 0) {
			this.options.add(new PromptOption(this, "Manage", new ManageFacility(PDI, this, this.getData())));	
		}
		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}


}
