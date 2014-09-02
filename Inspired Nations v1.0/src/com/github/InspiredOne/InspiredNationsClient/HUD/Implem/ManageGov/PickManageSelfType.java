package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PassByOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.MainHud;

public class PickManageSelfType extends PassByOptionMenu {

	Class<? extends InspiredGov> GovType;
	
	public PickManageSelfType(PlayerData PDI, Class<? extends InspiredGov> class1) {
		super(PDI);
		this.GovType = class1;
	}

	@Override
	public String getPreOptionText() {
		Debug.print("inside pick manage self type");
		return "Pick the type of " + GovFactory.getGovInstance(GovType).getTypeName() + ".";
	}

	@Override
	public Menu getPreviousMenu() {
		GovFactory<? extends InspiredGov> govf = new GovFactory<>(GovType);
		if(govf.getGov().getGeneralGovType().equals(GovType)) {
			return new MainHud(PDI);
		}
		else {
			return new PickManageSelfType(PDI, govf.getGov().getGeneralGovType());
		}
	}

	@Override
	public String getHeader() {
		return "Select " + GovFactory.getGovInstance(GovType).getTypeName() + " type.";
	}

	@Override
	public void addOptions() {
		for(Class<? extends InspiredGov> gov:GovFactory.getGovInstance(GovType).getSelfGovs()) {
			GovFactory<? extends InspiredGov> govf = new GovFactory<>(gov);
			govf.withOwner(PDI.getPlayerID());
			if(govf.getGov().getSelfGovs().size() == 1) {
				if(govf.getGov().getSelfGovs().get(0).equals(govf.getGov().getClass()) && !PDI.getOwnership(govf.getGov().getClass()).isEmpty()) {
					this.options.add(new PromptOption(this, govf.getGov().getTypeName(), new PickManageSuperGov(PDI, govf.getGov().getClass(), InspiredNations.global)));
				}
				else  if(!PDI.getOwnership(govf.getGov().getClass()).isEmpty()){
					this.options.add(new PromptOption(this, govf.getGov().getTypeName(), this));
				}
			}
			else  if(!PDI.getOwnership(govf.getGov().getClass()).isEmpty()) {
				this.options.add(new PromptOption(this, govf.getGov().getTypeName(), this));
			}
		}
		
	}

	@Override
	public void addActionManagers() {
		
	}
}
