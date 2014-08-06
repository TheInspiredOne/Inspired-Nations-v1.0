package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov;

import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuAlert;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNations.ToolBox.Relation;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class ChangeRelationOption extends Option {

	Relation re;
	OwnerGov govre;
	OwnerGov selfgov;
	
	public ChangeRelationOption(OptionMenu menu, String label, Relation re, OwnerGov govre, OwnerGov selfgov,
			OptionUnavail reason) {
		super(menu, label, reason);
		this.re = re;
		this.govre = govre;
		this.selfgov = selfgov;
	}

	public ChangeRelationOption(OptionMenu menu, String label, Relation re, OwnerGov govre, OwnerGov selfgov) {
		super(menu, label);
		this.re = re;
		this.govre = govre;
		this.selfgov = selfgov;
	}

	public ChangeRelationOption(OptionMenu menu, String label, Relation re, OwnerGov govre, OwnerGov selfgov,
			String description) {
		super(menu, label, description);
		this.re = re;
		this.govre = govre;
		this.selfgov = selfgov;
	}

	@Override
	public Menu response(String input) {
		if(re.equals(Relation.NEUTRAL)) {
			selfgov.getRelations().remove(govre);
		}
		else if (re.equals(Relation.ENEMY)){
			selfgov.getRelations().put(govre, re);
		}
		else {
			selfgov.getRelations().put(govre, re);
		}
		govre.sendNotification(MenuAlert.GOV_HAS_BEEN_RELATED(re, govre, selfgov));
		selfgov.sendNotification(MenuAlert.GOV_HAS_SUCCEFULLY_RELATED(re, govre, selfgov));
		return menu;
	}

}
