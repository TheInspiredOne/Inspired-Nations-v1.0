package com.github.InspiredOne.InspiredNationsClient.Hud.Implem;

import java.util.List;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.NewGov.PickSelfType;

public class NewAll extends OptionMenu {

	private Menu previous;
	public NewAll(PlayerData PDI, Menu previous) {
		super(PDI);
		this.previous = previous;
	}

	@Override
	public String getPreOptionText() {
		// TODO Auto-generated method stub
		return "Pick the government you would like to create.";
	}

	@Override
	public void addOptions() {
		List<Class<? extends OwnerGov>> array = InspiredNations.global.getAllSubGovs();
		array.remove(InspiredNations.global.getClass());
		for(Class<? extends OwnerGov> gov:array) {
			OwnerGov govobj = (OwnerGov) GovFactory.getGovInstance(gov);
			if(!PDI.getCitizenship(govobj.getSuperGov()).isEmpty()) {
				this.options.add(new PromptOption(this, "New " + govobj.getTypeName(), new PickSelfType<>(PDI, gov)));
			}
		}
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub

	}

	@Override
	public Menu getPreviousMenu() {
		// TODO Auto-generated method stub
		return this.previous;
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
		return "New Government";
	}

}
