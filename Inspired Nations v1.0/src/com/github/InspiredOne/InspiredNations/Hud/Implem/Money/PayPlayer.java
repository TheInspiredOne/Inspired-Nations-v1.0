package com.github.InspiredOne.InspiredNations.Hud.Implem.Money;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Hud.Menu;
import com.github.InspiredOne.InspiredNations.Hud.PromptOption;
import com.github.InspiredOne.InspiredNations.Hud.MenuLoops.FindAddress.PickPlayerGeneral;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNations.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNations.ToolBox.Payable;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;

public class PayPlayer extends PickPlayerGeneral {

	Payable accounts;
	Menu back;
	public PayPlayer(PlayerData PDI, Payable accounts, Menu back) {
		super(PDI, back);
		this.accounts = accounts;
		this.back = back;
	}

	@Override
	public Menu getPreviousPrompt() {
		return new PayNav(PDI, back, accounts);
	}

	@Override
	public String postTabListPreOptionsText() {
		return MenuTools.oneLineWallet("", PDI, accounts);
	}

	@Override
	public String getHeader() {
		return "Select a player to pay";
	}

	@Override
	public boolean check(PlayerID player) {
		return true;
	}

	@Override
	public void addOptions() {
		if(this.getData().equals(PDI.getPlayerID())) {
			this.options.add(new PromptOption(this, "Transfer Money", new PickAccount(PDI, this, PDI.getAccounts(), accounts)));
		}
		else {
			this.options.add(new PayAccountOption(PDI, this, "Pay Player <amount>", accounts, this.getData().getPDI(), PDI));
		}
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
