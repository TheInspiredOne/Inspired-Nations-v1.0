package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov;

import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov.ChangeTaxRateMenu.TaxRateOption;

public class ChangeTaxOption extends Option {

	OwnerGov gov;
	TaxRateOption option;
	public ChangeTaxOption(OptionMenu menu, String label, TaxRateOption option, OwnerGov gov) {
		super(menu, label);
		this.option = option;
		this.gov = gov;
	}

	@Override
	public Menu response(String input) {
		try {
			Double newrate = Double.parseDouble(input);
			gov.getTaxrates().put(option.gov, newrate);
		}
		catch (Exception ex) {
			menu.setError(MenuError.INVALID_NUMBER_INPUT(menu.PDI));
		}
		return menu;
	}

}
