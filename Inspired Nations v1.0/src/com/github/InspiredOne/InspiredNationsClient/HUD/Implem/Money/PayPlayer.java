package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsServer.Economy.Payable;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;

public class PayPlayer extends PickPlayerGeneral {

	Payable accounts;
	Menu back;
	public PayPlayer(PlayerDataPortal PDI, Payable accounts, Menu back) {
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
