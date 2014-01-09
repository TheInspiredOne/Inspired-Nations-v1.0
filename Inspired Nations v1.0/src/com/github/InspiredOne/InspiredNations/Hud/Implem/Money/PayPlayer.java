package com.github.InspiredOne.InspiredNations.Hud.Implem.Money;

import java.util.List;
import java.util.Set;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.Account;
import com.github.InspiredOne.InspiredNations.Hud.Menu;
import com.github.InspiredOne.InspiredNations.Hud.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;

public class PayPlayer extends TabSelectOptionMenu<PlayerID> {

	Set<Account> accounts;
	public PayPlayer(PlayerData PDI, Set<Account> accounts) {
		super(PDI);
		this.accounts = accounts;
	}

	@Override
	public Menu getPreviousPrompt() {
		return new PayNav(PDI, accounts);
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}

	@Override
	public void Init() {
		for(PlayerID player:InspiredNations.playerdata.keySet()) {
			this.taboptions.add(player);
		}
		this.options.add(e)
	}

	@Override
	public String getHeader() {
		return "Select a player to pay";
	}

}