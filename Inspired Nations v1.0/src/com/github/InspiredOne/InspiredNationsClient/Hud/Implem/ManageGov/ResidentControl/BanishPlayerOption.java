package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov.ResidentControl;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.Governments.OwnerSubjectGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.MainHud;

public class BanishPlayerOption extends Option {

	PlayerData PDI;
	OwnerGov gov;
	
	public BanishPlayerOption(OptionMenu menu, String label, PlayerData player, OwnerGov gov,
			OptionUnavail reason) {
		super(menu, label, reason);
		this.PDI = player;
		this.gov = gov;
	}

	public BanishPlayerOption(OptionMenu menu, String label,PlayerData player, OwnerGov gov) {
		super(menu, label);
		this.PDI = player;
		this.gov = gov;
	}

	public BanishPlayerOption(OptionMenu menu, String label, PlayerData player, OwnerGov gov, String description) {
		super(menu, label, description);
		this.PDI = player;
		this.gov = gov;
	}

	@Override
	public Menu response(String input) {
		if(gov instanceof OwnerSubjectGov) {
			((OwnerSubjectGov) gov).removeSubject(PDI.getPlayerID());
		}
		else {
			gov.removeOwner(PDI.getPlayerID());
		}
		if(PDI.getPlayerID().equals(menu.PDI.getPlayerID())) {
			return new MainHud(menu.PDI);
		}
		else {
			return menu;
		}
	}

}
