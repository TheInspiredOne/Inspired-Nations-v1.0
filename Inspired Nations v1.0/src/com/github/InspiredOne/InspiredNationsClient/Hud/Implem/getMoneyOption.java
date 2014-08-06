package com.github.InspiredOne.InspiredNationsClient.Hud.Implem;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class getMoneyOption extends Option {

	PlayerData PDI;
	
	public getMoneyOption(OptionMenu menu, String label, PlayerData PDI) {
		super(menu, label);
		this.PDI = PDI;
	}

	@Override
	public Menu response(String input) {
		try {
			PDI.getAccounts().addMoney(new BigDecimal(1000), PDI.getCurrency());
		} catch (NegativeMoneyTransferException e) {
			e.printStackTrace();
		}
		return menu;
	}

}
