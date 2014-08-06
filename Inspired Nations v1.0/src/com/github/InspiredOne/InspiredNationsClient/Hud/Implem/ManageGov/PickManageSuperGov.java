package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov;

import java.util.Iterator;
import java.util.LinkedHashSet;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PassByOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;

public class PickManageSuperGov extends PassByOptionMenu {

	Class<? extends InspiredGov> GovType;
	InspiredGov supergov;
	public PickManageSuperGov(PlayerData PDI, Class<? extends InspiredGov> GovType, InspiredGov supergov) {
		super(PDI);
		this.GovType = GovType;
		this.supergov = supergov;
	}

	@Override
	public String getPreOptionText() {
		// TODO Auto-generated method stub
		return "";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Menu getPreviousMenu() {
		if(supergov.equals(InspiredNations.global)) {
			return new PickManageSelfType(PDI, (Class<? extends OwnerGov>) GovType);
		}
		else {
			return new PickManageSuperGov(PDI, GovType, supergov.getSuperGovObj());
		}
	}

	@Override
	public String getHeader() {
		return "Pick " + GovFactory.getGovInstance(GovType).getTypeName();
	}

	@Override
	public void addOptions() {
		LinkedHashSet<OwnerGov> options = PDI.getAllSuperGovsBelow(this.GovType, supergov);
		for(Iterator<OwnerGov> iter = options.iterator(); iter.hasNext();) {
			OwnerGov gov = iter.next();
			if(gov.getClass().equals(this.GovType)) {
				this.options.add(new PromptOption((OptionMenu) this, gov.getName(), new ManageGov(PDI, gov)));
			}
			else {
				this.options.add(new PromptOption((OptionMenu) this, gov.getName(), new PickManageSuperGov(PDI, this.GovType, gov)));
			}
		}
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
	}
}
