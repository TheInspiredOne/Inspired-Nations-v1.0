package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNations.Exceptions.InspiredGovTooStrongException;
import com.github.InspiredOne.InspiredNations.Exceptions.InsufficientRefundAccountBalanceException;
import com.github.InspiredOne.InspiredNations.Exceptions.RegionOutOfEncapsulationBoundsException;
import com.github.InspiredOne.InspiredNations.Governments.Facility;
import com.github.InspiredOne.InspiredNations.Regions.nullRegion;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class RemoveFacilityOption extends Option {

	Facility fac;
	
	public RemoveFacilityOption(OptionMenu menu, String label,
			OptionUnavail reason, Facility fac) {
		super(menu, label, reason);
		this.fac = fac;
	}

	public RemoveFacilityOption(OptionMenu menu, String label, Facility fac) {
		super(menu, label);
		this.fac = fac;
	}

	public RemoveFacilityOption(OptionMenu menu, String label,
			String description, Facility fac) {
		super(menu, label, description);
		this.fac = fac;
	}

	@Override
	public Menu response(String input) {
		
		try {
			fac.setLand(new nullRegion());
			InspiredNations.regiondata.removeValue(fac);
			return menu;
		} catch (BalanceOutOfBoundsException e) {
			e.printStackTrace();
		} catch (InspiredGovTooStrongException e) {
			e.printStackTrace();
		} catch (RegionOutOfEncapsulationBoundsException e) {
			e.printStackTrace();
		} catch (InsufficientRefundAccountBalanceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menu;
	}

}
