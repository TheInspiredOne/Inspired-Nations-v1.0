package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov;

import com.github.InspiredOne.InspiredNations.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNations.Exceptions.NegativeMilitaryLevelExecption;
import com.github.InspiredOne.InspiredNations.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNations.Governments.OwnerSubjectGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class SetMilitaryLevelOption extends Option {

	OwnerSubjectGov gov;
	
	public SetMilitaryLevelOption(OptionMenu menu, String label,
			OptionUnavail reason, OwnerSubjectGov gov) {
		super(menu, label, reason);
		this.gov = gov;
	}

	public SetMilitaryLevelOption(OptionMenu menu, String label, OwnerSubjectGov gov) {
		super(menu, label);
		this.gov = gov;
	}

	public SetMilitaryLevelOption(OptionMenu menu, String label,
			String description, OwnerSubjectGov gov) {
		super(menu, label, description);
		this.gov = gov;
	}

	@Override
	public Menu response(String input) {
		try {
			this.gov.paySuper(gov.getAdditionalCost(Integer.parseInt(input), gov.getCurrency()),gov.getCurrency());
			this.gov.setMilitaryLevel(Integer.parseInt(input));
		} catch (NumberFormatException e) {
			this.menu.setError(MenuError.INVALID_NUMBER_INPUT(menu.PDI));
		} catch (NegativeMilitaryLevelExecption e) {
			this.menu.setError(MenuError.NEGATIVE_MILITARY_LEVEL_NOT_ALLOWED(menu.PDI));
		} catch (BalanceOutOfBoundsException e) {
			this.menu.setError(MenuError.NOT_ENOUGH_MONEY(menu.PDI));
		} catch (NegativeMoneyTransferException e) {
			e.printStackTrace();
		} 
		return menu;
	}

}
