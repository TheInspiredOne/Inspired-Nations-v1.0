package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov.ResidentControl;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.OwnerSubjectGov;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.JoinSubjectGovOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.MenuLoops.FindAddress.PickPlayerGeneral;

public class PickPlayerToOfferCitizenship extends PickPlayerGeneral {

	OwnerSubjectGov gov;
	
	public PickPlayerToOfferCitizenship(PlayerData PDI, Menu previous, OwnerSubjectGov gov) {
		super(PDI, previous);
		this.gov = gov;
	}

	@Override
	public boolean check(PlayerID player) {
		if(gov.isSubject(player) || gov.getSubjectOffers().contains(player)) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}

	@Override
	public String getHeader() {
		return "Offer " + gov.getSubjectPositionName();
	}

	@Override
	public void addOptions() {
		if(this.getTabOptions().size() != 0) {
			if(gov.getSubjectRequests().contains(this.getData())) {
				this.options.add(new JoinSubjectGovOption(this, "Accept Request", this.gov, this.getData()));
			}
			else {
				this.options.add(new OfferSubjectOption(this, "Offer " + gov.getSubjectPositionName(), this.gov, this.getData()));
			}
		}
		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
