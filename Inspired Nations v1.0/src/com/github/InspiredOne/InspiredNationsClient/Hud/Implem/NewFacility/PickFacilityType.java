package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.NewFacility;


import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Exceptions.SuperGovWrongTypeException;
import com.github.InspiredOne.InspiredNations.Governments.Facility;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PassByOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;

public class PickFacilityType<T extends Facility> extends PassByOptionMenu {

	Class<T> GovType;
	InspiredGov gov;
	Menu previous;
	
	public PickFacilityType(PlayerData PDI, Menu previous, InspiredGov gov, Class<T> GovType) {
		super(PDI);
		this.GovType = GovType;
		this.gov = gov;
		this.previous = previous;
	}

	@Override
	public String getPreOptionText() {
		return "Pick the type of " + GovFactory.getGovInstance(GovType).getTypeName() + ".";
	}

	@Override
	public String getHeader() {
		return "Select " + GovFactory.getGovInstance(GovType).getTypeName() + " type.";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Menu getPreviousMenu() {
		GovFactory<T> govf = new GovFactory<T>(GovType);
		if(govf.getGov().getGeneralGovType().equals(GovType)) {
			return previous;
		}
		else {
			return new PickFacilityType<T>(PDI, previous, gov, (Class<T>) govf.getGov().getGeneralGovType());
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void addOptions() {
		Debug.print("in init of PickFacilityType 1");
		for(Class<? extends InspiredGov> selfgov:GovFactory.getGovInstance(GovType).getSelfGovs()) {
			Debug.print("in init of PickFacilityType 3");
			GovFactory<T> govf = null;
			try {
				Debug.print("Accounts null in PickFacility Type?" + (gov.getAccounts() == null));
				govf = new GovFactory<T>((Class<T>) selfgov).withSuperGov(gov).withAccountCollection(gov.getAccounts()).withCurrency(gov.getCurrency());
			} catch (SuperGovWrongTypeException e) {
				e.printStackTrace();
			}
			Debug.print("in init of PickFacilityType 4");
			if(govf.getGov().getSelfGovs().size() == 1) {
				Debug.print("in init of PickFacilityType 5");
				if(govf.getGov().getSelfGovs().get(0).equals(govf.getGov().getClass())) {
					Debug.print("in init of PickFacilityType 6");
					this.options.add(new PromptOption(this, govf.getGov().getTypeName(), new PickFacilityName(PDI, previous, this.gov, govf)));
					Debug.print("in init of PickFacilityType 7");
				}
				else {
					Debug.print("in init of PickFacilityType 8");
					this.options.add(new PromptOption(this, govf.getGov().getTypeName(), new PickFacilityType<T>(PDI, previous, this.gov, (Class<T>) (govf.getGov()).getClass())));
					Debug.print("in init of PickFacilityType 9");
				}
			}
			else {
				Debug.print("in init of PickFacilityType 10");
				this.options.add(new PromptOption(this, govf.getGov().getTypeName(), new PickFacilityType<T>(PDI, previous, this.gov, (Class<T>) (govf.getGov()).getClass())));
			}
		}
		Debug.print("in init of PickFacilityType 2");
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}
}
