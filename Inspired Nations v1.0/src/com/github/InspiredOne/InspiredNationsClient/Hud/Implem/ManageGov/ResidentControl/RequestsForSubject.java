package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov.ResidentControl;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.OwnerSubjectGov;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.IgnoreOfferOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.JoinSubjectGovOption;

public class RequestsForSubject extends TabSelectOptionMenu<PlayerID> {

	OwnerSubjectGov gov;
	Menu previous;
	
	public RequestsForSubject(PlayerData PDI, Menu previous, OwnerSubjectGov gov) {
		super(PDI);
		this.gov = gov;
		this.previous = previous;
	}

	@Override
	public Menu getPreviousPrompt() {
		return new ManageCitizens(PDI, previous, gov);
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}

	@Override
	public String getHeader() {
		return this.gov.getSubjectPositionName() + " Requests";
	}

	@Override
	public void addTabOptions() {
		for(PlayerID player:this.gov.getSubjectRequests()) {
			this.taboptions.add(player);
		}
	}

	@Override
	public void addOptions() {
		if(taboptions.size() != 0) {
			this.options.add(new IgnoreOfferOption(this, "Ignore Request", this.getData(), gov.getSubjectRequests()));
			this.options.add(new JoinSubjectGovOption(this, "Accept Request", this.gov, this.getData()));
		}
		this.options.add(new PromptOption(new RequestsForSubject(PDI, previous, gov), gov.getSubjectPositionName() + " Offers",
				new OffersForSubject(PDI, previous, gov)));
		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}
	
}
