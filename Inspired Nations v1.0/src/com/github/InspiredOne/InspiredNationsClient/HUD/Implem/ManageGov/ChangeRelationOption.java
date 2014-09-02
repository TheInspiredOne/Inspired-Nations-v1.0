package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov;

import com.github.InspiredOne.InspiredNationsClient.HUD.Option;
import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsServer.Remotes.OwnerGovPortal;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Relation;


public class ChangeRelationOption extends Option {

	Relation re;
	OwnerGovPortal govre;
	OwnerGovPortal selfgov;
	
	public ChangeRelationOption(OptionMenu menu, String label, Relation re, OwnerGovPortal govre, OwnerGovPortal selfgov,
			OptionUnavail reason) {
		super(menu, label, reason);
		this.re = re;
		this.govre = govre;
		this.selfgov = selfgov;
	}

	public ChangeRelationOption(OptionMenu menu, String label, Relation re, OwnerGovPortal govre, OwnerGovPortal selfgov) {
		super(menu, label);
		this.re = re;
		this.govre = govre;
		this.selfgov = selfgov;
	}

	public ChangeRelationOption(OptionMenu menu, String label, Relation re, OwnerGovPortal govre, OwnerGovPortal selfgov,
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
