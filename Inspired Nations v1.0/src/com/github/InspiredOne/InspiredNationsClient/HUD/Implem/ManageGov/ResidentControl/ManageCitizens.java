package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov.ResidentControl;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.Governments.OwnerSubjectGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNations.ToolBox.ProtectionLevels;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;

public class ManageCitizens extends TabSelectOptionMenu<PlayerID> {
	OwnerGov gov;
	Menu previous;
	public ManageCitizens(PlayerData PDI, Menu previous, OwnerGov gov) {
		super(PDI);
		this.gov = gov;
		this.previous = previous;
	}

	@Override
	public Menu getPreviousPrompt() {
		return previous;
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}
 
	@Override
	public String getHeader() {
		if(gov instanceof OwnerSubjectGov) {
			return "Manage " + ((OwnerSubjectGov) gov).getSubjectPositionName();
		}
		else {
			return "Manage " + gov.getOwnerPositionName();
		}

	}

	@Override
	public void addTabOptions() {
		for(PlayerID subject:InspiredNations.playerdata) {
			if(gov.isSubject(subject)) {
				this.taboptions.add(subject);
			}
		}
	}

	@Override
	public void addOptions() {
		this.options.add(new PromptOption(this, gov.getOwnerPositionName() + " Requests and Offers", new RequestsForOwner(PDI, previous, gov)));
		if(gov instanceof OwnerSubjectGov) {
			this.options.add(new PromptOption(this, ((OwnerSubjectGov) gov).getSubjectPositionName() + " Requests and Offers", new RequestsForSubject(PDI, previous, (OwnerSubjectGov) gov)));
		}
		if(this.taboptions.size() > 0) {
			if(gov.getProtectionlevel() >= ProtectionLevels.IMMIGRATION_CONTROL) { 
				this.options.add(new BanishPlayerOption(this, "Banish Player", this.getData().getPDI(), gov));
			}
			else {
				this.options.add(new BanishPlayerOption(this, "Banish Player", this.getData().getPDI(), gov, OptionUnavail.NEED_HIGHER_PROTECTION));
			}
		}
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
	}
}
