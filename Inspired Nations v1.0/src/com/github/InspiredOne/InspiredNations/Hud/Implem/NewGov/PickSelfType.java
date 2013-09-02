package com.github.InspiredOne.InspiredNations.Hud.Implem.NewGov;


import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Governments.NoSubjects;
import com.github.InspiredOne.InspiredNations.Hud.Menu;
import com.github.InspiredOne.InspiredNations.Hud.PassByOptionMenu;
import com.github.InspiredOne.InspiredNations.Hud.PromptOption;
import com.github.InspiredOne.InspiredNations.Hud.Implem.MainHud;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;

public class PickSelfType extends PassByOptionMenu {

	Class<? extends NoSubjects> GovType;
	
	public PickSelfType(PlayerData PDI, Class<? extends NoSubjects> GovType) {
		super(PDI);
		this.GovType = GovType;
	}

	@Override
	public String getPreOptionText() {
		return "Pick what kind of " + GovFactory.getGovInstance(GovType).getTypeName()
				+ " you would like to make.";
	}

	@Override
	public String getHeader() {
		return "Select " + GovFactory.getGovInstance(GovType).getTypeName() + " type.";
	}

	@Override
	public Menu PreviousMenu() {
		return new MainHud(PDI);
	}

	@Override
	public void init() {
		for(Class<? extends InspiredGov> gov:GovFactory.getGovInstance(GovType).getSelfGovs()) {
			GovFactory govf = new GovFactory(gov);
			((NoSubjects) govf.getGov()).getOwners().add(PDI.getName());
			this.options.add(new PromptOption(this, govf.getGov().getTypeName(), new PickSuperGov(PDI, govf) , OptionUnavail.NOT_UNAVAILABLE ));
		}
	}

}
