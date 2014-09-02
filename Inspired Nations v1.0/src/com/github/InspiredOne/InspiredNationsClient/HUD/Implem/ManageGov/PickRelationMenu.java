package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.ToolBox.Datable;
import com.github.InspiredOne.InspiredNations.ToolBox.Relation;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools.TextColor;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.MenuLoops.FindAddress.PickGovGeneral;

public class PickRelationMenu extends PickGovGeneral {

	OwnerGov gov;
	OptionMenu previous;
	
	public PickRelationMenu(PlayerData PDI, OptionMenu previous, Menu next,
			Datable<InspiredGov> superGov, OwnerGov gov) {
		super(PDI, previous, next, superGov);
		this.gov = gov;
		this.previous = previous;
	}

	public PickRelationMenu(PlayerData PDI, Menu previous, Menu next, OwnerGov gov) {
		super(PDI, previous, next);
		this.gov = gov;
	}

	@Override
	public boolean check(InspiredGov govre) {
		if(govre.getTier() == gov.getTier() && gov.isSubOf(govre.getSuperGovObj())
				&& !this.gov.getRelations().containsKey(govre) && this.gov != govre) {
			return true;
		}
		return false;
	}

	@Override
	public String postTabListPreOptionsText() {
		String output = "";
		if(this.taboptions.size() == 0) {
			output = TextColor.INSTRUCTION(PDI) + "There are no other governments to add.";
		}
		return output;
	}

	@Override
	public void addOptions() {
		OptionMenu back = this;
		if(taboptions.size() == 1) {
			back = previous;
		}
		if(!this.taboptions.isEmpty()) {
			this.options.add(new ChangeRelationOption(back, "Ally " + this.getData().getName(),
					Relation.ALLY, (OwnerGov) this.getData(), this.gov));
			this.options.add(new ChangeRelationOption(back, "Enemy " + this.getData().getName(),
					Relation.ENEMY, (OwnerGov) this.getData(), this.gov));
		}
	}

	@Override
	public void addActionManagers() {

	}

	@Override
	public String getHeader() {
		return "Pick Government";
	}

}
