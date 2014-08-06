package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Regions.Region;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PassByOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;

public class PickClaimType extends PassByOptionMenu {

	InspiredGov gov;
	Menu previous;
	
	public PickClaimType(PlayerData PDI, Menu previous, InspiredGov gov) {
		super(PDI);
		this.gov = gov;
		this.previous = previous;
		Debug.print("In PickClaimType constructor");
	}

	@Override
	public String getPreOptionText() {
		
		return "";
	}

	@Override
	public Menu getPreviousMenu() {
		return previous;
	}

	@Override
	public String getHeader() {
		return "Pick the Type of Selection";
	}
	
	@Override
	public void addOptions() {
		Region temp;
		for(Class<? extends Region> regiontype:gov.getRegion().getAllowedForms()) {
			temp = Tools.getInstance(regiontype);
			this.options.add(new PromptOption(this, temp.getTypeName() + ": " + temp.getDescription(), temp.getClaimMenu(PDI, previous, gov)));
		}		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
