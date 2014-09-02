package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.NewGov;


import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PassByOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.MainHud;

public class PickSelfType<T extends OwnerGov> extends PassByOptionMenu {

	Class<T> GovType;
	
	
	public PickSelfType(PlayerData PDI, Class<T> GovType) {
		super(PDI);
		this.GovType = GovType;
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
			return new MainHud(PDI);
		}
		else {
			return new PickSelfType<T>(PDI, (Class<T>) govf.getGov().getGeneralGovType());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addOptions() {
		for(Class<? extends InspiredGov> gov:GovFactory.getGovInstance(GovType).getSelfGovs()) {
			GovFactory<T> govf = new GovFactory<T>((Class<T>) gov);
			govf.withOwner(PDI.getPlayerID());
			Debug.print(govf.getGov().isOwner(PDI.getPlayerID()));
			if(govf.getGov().getSelfGovs().size() == 1) {
				if(govf.getGov().getSelfGovs().get(0).equals(govf.getGov().getClass())) {
					this.options.add(new PromptOption(this, govf.getGov().getTypeName(), new PickSuperGov<T>(PDI, govf)));
				}
				else {
					this.options.add(new PromptOption(this, govf.getGov().getTypeName(), new PickSelfType<T>(PDI, (Class<T>) govf.getGov().getClass())));
				}
			}
			else {
				this.options.add(new PromptOption(this, govf.getGov().getTypeName(), new PickSelfType<T>(PDI, (Class<T>) govf.getGov().getClass())));
			}
		}
	}

	@Override
	public void addActionManagers() {
		
	}
}
