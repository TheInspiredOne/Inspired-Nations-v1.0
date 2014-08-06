package com.github.InspiredOne.InspiredNationsClient.Hud.Implem;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Governments.OwnerSubjectGov;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;

public class SubjectOffers extends TabSelectOptionMenu<OwnerSubjectGov> {

	public SubjectOffers(PlayerData PDI) {
		super(PDI);
	}

	@Override
	public Menu getPreviousPrompt() {
		return new PlayerCitizenship(PDI);
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}

	@Override
	public String getHeader() {
		return "Offers For Citizenship";
	}

	@Override
	public void addTabOptions() {
		for(InspiredGov gov:InspiredNations.regiondata) {
			if(gov instanceof OwnerSubjectGov) {
				if(((OwnerSubjectGov) gov).getSubjectOffers().contains(PDI.getPlayerID())) {
					this.taboptions.add((OwnerSubjectGov) gov);
				}
			}
		}
		
	}

	@Override
	public void addOptions() {
		if(this.taboptions.size() != 0) {
			this.options.add(new IgnoreOfferOption(this, "Ignore Offer From " + this.getData().getName(), PDI.getPlayerID(), this.getData().getSubjectOffers()));
			this.options.add(new JoinSubjectGovOption(this, "Accept Offer From " + this.getData().getName(), this.getData(), PDI.getPlayerID()));
		}
		this.options.add(new PromptOption(this, "Requests Citizenship", new SubjectRequests(PDI)));
		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
