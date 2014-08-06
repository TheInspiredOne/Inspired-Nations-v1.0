package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov.ResidentControl;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.OwnerSubjectGov;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.IgnoreOfferOption;

public class OffersForSubject extends TabSelectOptionMenu<PlayerID> {

	OwnerSubjectGov gov;
	Menu previous;
	
	public OffersForSubject(PlayerData PDI, Menu previous, OwnerSubjectGov gov) {
		super(PDI);
		this.gov = gov;
		this.previous = previous;
	}

	@Override
	public Menu getPreviousPrompt() {
		return new RequestsForSubject(PDI, previous, gov);
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}

	@Override
	public String getHeader() {
		return gov.getSubjectPositionName() + " Offers";
	}

	@Override
	public void addTabOptions() {
		for(PlayerID player:gov.getSubjectOffers()) {
			this.taboptions.add(player);
		}
		
	}

	@Override
	public void addOptions() {
		if(this.taboptions.size() != 0) {
			this.options.add(new IgnoreOfferOption(this, "Remove Offer", this.getData(), gov.getSubjectOffers()));
		}
		PickPlayerToOfferCitizenship next = new PickPlayerToOfferCitizenship(PDI, this, gov);
		next.addTabOptions();
		if(next.getTabOptions().size() > 0) {
			this.options.add(new PromptOption(this, "Add Offer", next));
		}
		else {
			this.options.add(new PromptOption(this, "Add Offer", next, OptionUnavail.NO_PEOPLE_TO_ADD));
		}

		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
