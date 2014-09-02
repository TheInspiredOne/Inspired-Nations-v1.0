package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Economy.Currency;
import com.github.InspiredOne.InspiredNations.Exceptions.NameAlreadyTakenException;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class RenameMoneyOption extends Option {

	InspiredGov gov;
	
	public RenameMoneyOption(OptionMenu menu, String label, OptionUnavail reason, InspiredGov gov) {
		super(menu, label, reason);
		this.gov = gov;
	}

	public RenameMoneyOption(OptionMenu menu, String label, InspiredGov gov) {
		super(menu, label);
		this.gov = gov;
	}

	public RenameMoneyOption(OptionMenu menu, String label, String description, InspiredGov gov) {
		super(menu, label, description);
		this.gov = gov;
	}

	@Override
	public Menu response(String input) {
		try {
			for(Currency curren:InspiredNations.Exchange.getExchangeMap().keySet()) {
				Debug.print(curren.getName() + " == " + gov.getCurrency().getName() + "? " + (curren == gov.getCurrency()) );
				
			}
			this.gov.getCurrency().setName(input);
			for(Currency curren:InspiredNations.Exchange.getExchangeMap().keySet()) {
				Debug.print(curren.getName() + " == " + gov.getCurrency().getName() + "? " + (curren == gov.getCurrency()) );
				
			}
			
		} catch (NameAlreadyTakenException e) {
			menu.setError(MenuError.MONEY_NAME_ALREADY_TAKEN(menu.PDI));
		}
		return menu;
	}

}
