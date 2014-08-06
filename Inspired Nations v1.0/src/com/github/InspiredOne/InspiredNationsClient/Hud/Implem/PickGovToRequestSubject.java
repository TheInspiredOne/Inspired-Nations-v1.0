package com.github.InspiredOne.InspiredNationsClient.Hud.Implem;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Governments.OwnerSubjectGov;
import com.github.InspiredOne.InspiredNations.ToolBox.Datable;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.Money.PickGovToPay;
import com.github.InspiredOne.InspiredNationsClient.Hud.MenuLoops.FindAddress.PickGovGeneral;

public class PickGovToRequestSubject extends PickGovGeneral {

	public PickGovToRequestSubject(PlayerData PDI, Menu previous, Menu next,
			Datable<InspiredGov> superGov) {
		super(PDI, previous, next, superGov);
	}

	public PickGovToRequestSubject(PlayerData PDI, Menu previous, Menu next) {
		super(PDI, previous, next);
	}

	@Override
	public boolean check(InspiredGov gov) {
		if(!(gov instanceof OwnerSubjectGov)) {
			return false;
		}
		else {
			if(((OwnerSubjectGov)gov).getSubjectRequests().contains(PDI.getPlayerID())) {
				return false;
			}
			else {
				return true;
			}
		}
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}

	@Override
	public String getHeader() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void addOptions() {
		String description = "";
		if(this.taboptions.size() != 0) {
			OwnerSubjectGov gov = ((OwnerSubjectGov) this.getData());
			Debug.print("Is a subject of this gov? " + gov.isSubject(PDI.getPlayerID()));
			if(gov.getAllSubGovsAndFacilitiesJustBelow().size() > 0 && gov.isSubject(PDI.getPlayerID())) {
				this.options.add(new PromptOption(this, "Search Under", new PickGovToRequestSubject(PDI, this, this.next, gov)));
			}
			if(!gov.canAddWithoutConsequence(PDI)) {
				description = "Joining will remove you from your " + gov.getTypeName();
			}
			if(gov.getSubjectOffers().contains(PDI.getPlayerID())) {
				this.options.add(new JoinSubjectGovOption(this, "Accept Offer From " + this.getData().getName(), (OwnerSubjectGov) this.getData(), PDI.getPlayerID()));
			}
			else if (!gov.isSubject(PDI.getPlayerID())) {
				options.add(new RequestSubjectOption(this, "Request " + gov.getSubjectPositionName(), description, gov));
			}
		}
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
