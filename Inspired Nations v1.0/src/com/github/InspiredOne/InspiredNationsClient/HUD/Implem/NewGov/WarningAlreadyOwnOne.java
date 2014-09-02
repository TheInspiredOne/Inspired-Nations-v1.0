package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.NewGov;

import java.util.ArrayList;
import java.util.List;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.MainHud;

public class WarningAlreadyOwnOne<T extends OwnerGov> extends OptionMenu {

	GovFactory<T> Govf;
	
	public  WarningAlreadyOwnOne(PlayerData PDI, GovFactory<T> Govf) {
		super(PDI);
		this.Govf = Govf;
	}

	@Override
	public String getPreOptionText() {
		String list = "";
		List<OwnerGov> govslost = new ArrayList<OwnerGov>();
			for(OwnerGov gov:PDI.getCitizenship()) {
				
				for(OwnerGov govlost:gov.getGovsLost(Govf.getGov(), PDI.getPlayerID())) {
					if (!govslost.contains(govlost)) {
						govslost.add(govlost);
					}
				}
			}
			for(OwnerGov gov:govslost) {
				list = list.concat(gov.getDisplayName(PDI) + ", ");
			}
			if(list.length() > 0) {
				list = list.substring(0,list.length() - 2);
			}
			
			return "Warning! Continuing will result in the loss of ownership of " + list + ". " +
				"Are you sure you want to continue?";
	}

	@Override
	public String getHeader() {
		return "Warning!";
	}

	@Override
	public Menu getPreviousMenu() {
		return new PickSuperGov<T>(PDI, Govf);
	}

	@Override
	public boolean getPassBy() {
		OwnerGov gov = Govf.getGov();
		return gov.canAddWithoutConsequence(PDI);
	}

	@Override
	public Menu getPassTo() {
		return new PickName<T>(PDI, Govf);
	}

	@Override
	public void addOptions() {
		options.add(new PromptOption(this, "Yes", new PickName<T>(PDI, Govf.withAccountCollection(PDI.getAccounts()))));
		options.add(new PromptOption(this, "No", new MainHud(PDI)));
	}

	@Override
	public void addActionManagers() {
		
	}

}
