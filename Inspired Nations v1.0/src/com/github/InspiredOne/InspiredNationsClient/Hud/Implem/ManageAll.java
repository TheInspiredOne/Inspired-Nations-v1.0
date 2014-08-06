package com.github.InspiredOne.InspiredNationsClient.Hud.Implem;

import java.util.List;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov.PickManageSelfType;

public class ManageAll extends OptionMenu {

	private Menu previous;
	
	public ManageAll(PlayerData PDI, Menu previous) {
		super(PDI);
		this.previous = previous;
	}

	@Override
	public String getPreOptionText() {
		// TODO Auto-generated method stub
		return "Pick the gov type you would like to manage.";
	}

	@Override
	public void addOptions() {
		List<Class<? extends OwnerGov>> array = InspiredNations.global.getAllSubGovs();
		array.remove(InspiredNations.global.getClass());
		for(Class<? extends OwnerGov> gov:array) {
			OwnerGov govobj = (OwnerGov) GovFactory.getGovInstance(gov);
			if(!PDI.getOwnership(gov).isEmpty()) {
				this.options.add(new PromptOption(this, "Manage " + govobj.getTypeName(), new PickManageSelfType(PDI, gov)));
			}
		}
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub

	}

	@Override
	public Menu getPreviousMenu() {
		return previous;
	}

	@Override
	public boolean getPassBy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Menu getPassTo() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String getHeader() {
		// TODO Auto-generated method stub
		return "Manage Governments";
	}

}
