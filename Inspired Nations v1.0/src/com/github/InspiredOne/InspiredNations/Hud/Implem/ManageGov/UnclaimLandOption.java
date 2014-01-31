package com.github.InspiredOne.InspiredNations.Hud.Implem.ManageGov;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Hud.Menu;
import com.github.InspiredOne.InspiredNations.Hud.Option;
import com.github.InspiredOne.InspiredNations.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNations.Regions.CummulativeRegion;
import com.github.InspiredOne.InspiredNations.Regions.NonCummulativeRegion;
import com.github.InspiredOne.InspiredNations.Regions.Region;
import com.github.InspiredOne.InspiredNations.Regions.nullRegion;

public class UnclaimLandOption extends Option {

	public InspiredGov gov;
	
	
	public UnclaimLandOption(OptionMenu menu, String label, InspiredGov gov) {
		super(menu, label);
		this.gov = gov;
	}

	@Override
	public Menu response(String input) {
		Debug.print("In responce of UnclaimLandOption");
		Region region = gov.getRegion().getRegion();
		if(region instanceof CummulativeRegion) {
			return ((CummulativeRegion<?>) region).getUnclaimMenu(menu.getPlayerData(), menu, gov);
		}
		else if(region instanceof NonCummulativeRegion){
			gov.getRegion().setRegion(new nullRegion());
		}
		return menu;
	}

}
