package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov.ResidentControl;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.IgnoreOfferOption;

public class OffersForOwner extends TabSelectOptionMenu<PlayerID> {

	Menu previous;
	OwnerGov gov;
	
	public OffersForOwner(PlayerData PDI, Menu previous, OwnerGov gov) {
		super(PDI);
		this.previous = previous;
		this.gov = gov;
	}

	@Override
	public Menu getPreviousPrompt() {
		return new RequestsForOwner(PDI, previous, gov);
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}

	@Override
	public String getHeader() {
		return gov.getOwnerPositionName() + " Offers";
	}

	@Override
	public void addTabOptions() {
		for(PlayerID player:this.gov.getOwnerOffers()) {
			this.taboptions.add(player);
		}		
	}

	@Override
	public void addOptions() {
		if(taboptions.size() != 0) {
			this.options.add(new IgnoreOfferOption(this, "Remove Offer", this.getData(), this.gov.getOwnerOffers()));
		}
		PickPlayerToOfferOwner next = new PickPlayerToOfferOwner(PDI, this, gov);
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
