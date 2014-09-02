package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNations.Exceptions.NegativeProtectionLevelException;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class SetProtectionLevelOption extends Option {


	private InspiredGov gov;
	
	public SetProtectionLevelOption(OptionMenu menu, String label, OptionUnavail reason, InspiredGov gov) {
		super(menu, label, reason);
		this.gov = gov;
	}

	public SetProtectionLevelOption(OptionMenu menu, String label, InspiredGov gov) {
		super(menu, label);
		this.gov = gov;
	}

	public SetProtectionLevelOption(OptionMenu menu, String label, String description, InspiredGov gov) {
		super(menu, label, description);
		this.gov = gov;
	}

	@Override
	public Menu response(String input) {
		try {
			Debug.print("protection input" + input);
			gov.setProtectionlevel(Integer.parseInt(input));
		} catch (NumberFormatException e) {
			menu.setError(MenuError.INVALID_NUMBER_INPUT(menu.PDI));
		} catch (NegativeProtectionLevelException e) {
			menu.setError(MenuError.NEGATIVE_PROTECTION_LEVEL_NOT_ALLOWED(menu.PDI));
		} catch (BalanceOutOfBoundsException e) {
			menu.setError(MenuError.NOT_ENOUGH_MONEY(menu.PDI));
		}
		return menu;
	}

}
