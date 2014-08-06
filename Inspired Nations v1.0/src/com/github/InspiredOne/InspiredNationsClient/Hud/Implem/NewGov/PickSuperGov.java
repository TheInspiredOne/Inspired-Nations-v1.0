package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.NewGov;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Exceptions.SuperGovWrongTypeException;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.Governments.OwnerSubjectGov;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PassByOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;

public class PickSuperGov<T extends OwnerGov> extends PassByOptionMenu {

	GovFactory<T> Govf;
	Class<? extends OwnerGov> superGov;
	
	public PickSuperGov(PlayerData PDI, GovFactory<T> Govf, Class<? extends OwnerGov> superGov) {
		super(PDI);
		this.Govf = Govf;
		this.superGov = superGov;
	}
	
	public PickSuperGov(PlayerData PDI, GovFactory<T> Govf) {
		super(PDI);
		this.Govf = Govf;
		this.superGov = InspiredNations.global.getClass();
	}

	@Override
	public String getPreOptionText() {
		return "Pick the " + GovFactory.getGovInstance(Govf.getGov().getSuperGov()).getTypeName()
				+ " that contains this " + Govf.getGov().getTypeName() + ".";
	}

	@Override
	public String getHeader() {
		return "Select Super Gov";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Menu getPreviousMenu() {
		return new PickSelfType<T>(PDI, (Class<T>) Govf.getGov().getGeneralGovType());
	}

	@Override
	public void addOptions() {
		OwnerGov superGovObj = GovFactory.getGovInstance(superGov);
		
		for(Class<? extends OwnerGov> govCheck:superGovObj.getSubGovs()) {
			if(Govf.getGov().isSubOfClass(govCheck)) {
				for(InspiredGov gov:PDI.getCitizenship(govCheck)) {
					if(govCheck.equals(Govf.getGov().getSuperGov())) {
						try {
							this.options.add(new PromptOption(this, gov.getName(), new WarningAlreadyOwnOne<T>(PDI, Govf.withSuperGov(gov))));
						} catch (SuperGovWrongTypeException e) {
							e.printStackTrace();
						}
					}
					else {
						this.options.add(new PromptOption(this, gov.getName(), new PickSuperGov<T>(PDI, Govf, superGov)));
					}
				}
			}
		}
		
		
		for(InspiredGov gov:PDI.getCitizenship(Govf.getGov().getSuperGov())) {
			try {
				this.options.add(new PromptOption(this, gov.getName(), new WarningAlreadyOwnOne<T>(PDI, Govf.withSuperGov(gov))));
			} catch (SuperGovWrongTypeException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}
}
