package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.Account;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.MenuLoops.FindAddress.PickPlayerGeneral;

public class PickPlayerToShare extends PickPlayerGeneral {

	Account account;
	
	public PickPlayerToShare(PlayerData PDI, Menu previous, Account account) {
		super(PDI, previous);
		this.account = account;
	}

	@Override
	public boolean check(PlayerID player) {
		if(PDI.getPlayerID().equals(player)) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}

	@Override
	public String getHeader() {
		return "Pick Player To Share Account";
	}

	@Override
	public void addOptions() {
		this.options.add(new ShareAccountOption(this, "Share Account With " + this.getData(), account, this.getData().getPDI().getAccounts()));
		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
