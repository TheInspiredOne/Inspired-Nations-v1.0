package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.Option;
import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov.ChangeTaxRateMenu.TaxRateOption;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNationsServer.Remotes.OwnerGovPortal;


public class ChangeTaxOption extends Option {

	OwnerGovPortal gov;
	TaxRateOption option;
	public ChangeTaxOption(OptionMenu menu, String label, TaxRateOption option, OwnerGovPortal gov) {
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
