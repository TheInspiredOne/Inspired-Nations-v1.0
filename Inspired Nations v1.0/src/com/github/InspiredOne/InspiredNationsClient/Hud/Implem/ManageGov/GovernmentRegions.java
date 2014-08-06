package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov;

import java.util.HashSet;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.Facility;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PassByOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.NewFacility.PickFacilityType;

public class GovernmentRegions extends PassByOptionMenu {

	private InspiredGov gov;
	Menu previous;
	public GovernmentRegions(PlayerData PDI, Menu previous, InspiredGov gov) {
		super(PDI);
		this.gov = gov;
		this.previous = previous;
	}

	@Override
	public String getPreOptionText() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getHeader() {
		// TODO Auto-generated method stub
		return "Government Regions";
	}

	@Override
	public Menu getPreviousMenu() {
		return previous;
	}

	@Override
	public void addOptions() {
		Debug.print("Inside addOptions() of GovernmentRegions");
		for(Class<? extends Facility> fac: gov.getGovFacilities()) {
			Facility facil = GovFactory.getGovInstance(fac);

			boolean allowed = true;
			for(Facility facility:gov.getFacilities()) {
				if(facility.getClass().equals(fac)) {
					allowed = false;
				}
			}
			if(!facil.isUnique() || allowed) {
				this.options.add(new PromptOption(this, "New "+facil.getTypeName(), new PickFacilityType<>(PDI, this,  gov, fac)));
			}
			Debug.print("Inside addOptions() of GovernmentRegions 2");
		}
		for(Facility fac: gov.getFacilities()) {
			HashSet<Class<? extends Facility>> set = new HashSet<Class<? extends Facility>>();
			if(!set.contains(fac.getClass())) {
				this.options.add(new PromptOption(this, "Manage " + fac.getTypeName(), new PickFacilityToManage<>(PDI,this, gov, fac.getClass())));
			}
			set.add(fac.getClass());
			Debug.print("Inside addOptions() of GovernmentRegions 3");
		}
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}


}
