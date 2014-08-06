package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNations.Exceptions.InspiredGovTooStrongException;
import com.github.InspiredOne.InspiredNations.Exceptions.InsufficientRefundAccountBalanceException;
import com.github.InspiredOne.InspiredNations.Exceptions.RegionOutOfEncapsulationBoundsException;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Regions.CummulativeRegion;
import com.github.InspiredOne.InspiredNations.Regions.NonCummulativeRegion;
import com.github.InspiredOne.InspiredNations.Regions.Region;
import com.github.InspiredOne.InspiredNations.Regions.nullRegion;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class UnclaimLandOption extends Option {

	public InspiredGov gov;
	
	
	public UnclaimLandOption(OptionMenu menu, String label, InspiredGov gov) {
		super(menu, label);
		this.gov = gov;
	}

	@Override
	public Menu response(String input) {
		Debug.print("In responce of UnclaimLandOption 1");
		Debug.print("In responce of UnclaimLandOption 2");
		
		try {
			gov.setLand(new nullRegion());
		} catch (BalanceOutOfBoundsException e) {
			e.printStackTrace();
		} catch (InspiredGovTooStrongException e) {
			e.printStackTrace();
		} catch (RegionOutOfEncapsulationBoundsException e) {
			e.printStackTrace();
		} catch (InsufficientRefundAccountBalanceException e) {
			
		}
		
/*		for(InspiredGov subgov:this.gov.getAllSubGovsAndFacilitiesJustBelow()) {
			if(subgov.getRegion().getEncapsulatingRegions().contains(gov.getRegion().getClass())) {
				subgov.getRegion().setRegion(new nullRegion());
			}
		}
		if(region instanceof CummulativeRegion) {
			Debug.print("In responce of UnclaimLandOption 3");
			return ((CummulativeRegion<?>) region).getUnclaimMenu(menu.getPlayerData(), menu, gov);
		}
		else if(region instanceof NonCummulativeRegion) {
			Debug.print("In responce of UnclaimLandOption 5");
			gov.getRegion().setRegion(new nullRegion());
		}*/
		return menu;
	}
}
