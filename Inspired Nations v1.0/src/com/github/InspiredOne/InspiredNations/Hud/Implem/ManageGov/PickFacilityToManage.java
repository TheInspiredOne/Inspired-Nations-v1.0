package com.github.InspiredOne.InspiredNations.Hud.Implem.ManageGov;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.Facility;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Hud.Menu;
import com.github.InspiredOne.InspiredNations.Hud.PromptOption;
import com.github.InspiredOne.InspiredNations.Hud.TabSelectOptionMenu;

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

	@SuppressWarnings("unchecked")
	@Override
	public void Init() {
		for(Facility fac:supergov.getFacilities()) {
			if(fac.getClass().equals(factype)) {
				this.taboptions.add((T) fac);
			}
		}	
		this.options.add(new PromptOption(this, "Manage", new ManageFacility(PDI, this, supergov, this.getSelection())));
	}

	@Override
	public String getHeader() {
		return "Pick " + GovFactory.getGovInstance(factype).getTypeName();
	}
	
	@Override
	public boolean getPassBy() {
		if(this.taboptions.size() == 1) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Menu getPassTo() {
		return this.options.get(0).response("1");
	}


}