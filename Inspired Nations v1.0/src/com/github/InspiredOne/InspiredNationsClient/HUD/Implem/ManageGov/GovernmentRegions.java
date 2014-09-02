package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov;

import java.rmi.RemoteException;
import java.util.HashSet;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.PassByOptionMenu;
import com.github.InspiredOne.InspiredNationsServer.Remotes.InspiredGovPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;


public class GovernmentRegions extends PassByOptionMenu {

	private InspiredGovPortal gov;
	Menu previous;
	public GovernmentRegions(PlayerDataPortal PDI, Menu previous, InspiredGovPortal gov) throws RemoteException {
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
