package com.github.InspiredOne.InspiredNationsClient.Hud.Implem;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Governments.OwnerSubjectGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;

public class SubjectRequests extends TabSelectOptionMenu<OwnerSubjectGov> {

	public SubjectRequests(PlayerData PDI) {
		super(PDI);
	}

	@Override
	public Menu getPreviousPrompt() {
		return new SubjectOffers(PDI);
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}

	@Override
	public String getHeader() {
		return "Subject Requests";
	}
	
	@Override
	public void addTabOptions() {
		for(InspiredGov gov:InspiredNations.regiondata) {
			if(gov instanceof OwnerSubjectGov) {
				if(((OwnerSubjectGov) gov).getSubjectRequests().contains(PDI.getPlayerID())) {
					this.taboptions.add((OwnerSubjectGov) gov);
				}
			}
		}
		
	}

	@Override
	public void addOptions() {
		if(this.taboptions.size() != 0) {
			this.options.add(new IgnoreOfferOption(this, "Remove Request to " + this.getData().getName(), PDI.getPlayerID(), this.getData().getSubjectRequests()));
		}
		PickGovToRequestSubject next = new PickGovToRequestSubject(PDI, this, this);
		next.addTabOptions();
		if(next.getTabOptions().size() > 0) {
			this.options.add(new PromptOption(this, "Add Request", next));
		}
		else {
			this.options.add(new PromptOption(this, "Add Request", next, OptionUnavail.NO_GOVERNMENTS_TO_ADD));
		}
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
