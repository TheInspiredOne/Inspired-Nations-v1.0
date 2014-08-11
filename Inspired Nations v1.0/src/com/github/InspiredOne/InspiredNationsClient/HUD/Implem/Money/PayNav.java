package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsServer.Economy.Payable;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;


public class PayNav extends PickNavGeneral {

	Payable accounts;
	/**
	 * 
	 * @param PDI
	 * @param accounts
	 * @param back	the menu to return to after doing all of the payment stuff.
	 */
	public PayNav(PlayerDataPortal PDI, Menu back, Payable accounts) {
		super(PDI, back);
		this.accounts = accounts;
	}

	@Override
	public String getPreOptionText() {
		return "";
	}

	@Override
	public String getHeader() {
		return "Pay";
	}

	@Override
	public String getGovOptionText() {
		return "Pay Government";
	}

	@Override
	public String getPlayerOptionText() {
		return "Pay Player";
	}

	@Override
	public Menu getGovMenu() {
		return new PickGovToPay(PDI, accounts, this, this);
	}

	@Override
	public PickPlayerGeneral getPlayerMenu() {
		return new PayPlayer(PDI, accounts, previous);
	}

	@Override
	public void addActionManagers() {
		
	}

}
