package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.Account;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.MenuLoops.FindAddress.PickNavGeneral;
import com.github.InspiredOne.InspiredNationsClient.Hud.MenuLoops.FindAddress.PickPlayerGeneral;

public class ShareAccountNav extends PickNavGeneral {

	Account account;
	public ShareAccountNav(PlayerData PDI, Menu previous, Account account) {
		super(PDI, previous);
		this.account = account;
	}

	@Override
	public String getGovOptionText() {
		return "Pick Government";
	}

	@Override
	public String getPlayerOptionText() {
		return "Pick Player";
	}

	@Override
	public Menu getGovMenu() {
		return new PickGovernmentToShare(PDI, this, this, account, InspiredNations.global);
	}

	@Override
	public PickPlayerGeneral getPlayerMenu() {
		return new PickPlayerToShare(PDI, this, account);
	}

	@Override
	public String getPreOptionText() {
		return "";
	}

	@Override
	public String getHeader() {
		return "Pick What You Would Like To Share With";
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
