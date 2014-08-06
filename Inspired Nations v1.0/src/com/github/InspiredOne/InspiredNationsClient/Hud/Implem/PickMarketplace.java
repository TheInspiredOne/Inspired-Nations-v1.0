package com.github.InspiredOne.InspiredNationsClient.Hud.Implem;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.MarketPlace;
import com.github.InspiredOne.InspiredNations.Economy.Sellable;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PassByOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;

public class PickMarketplace extends PassByOptionMenu {

	public PickMarketplace(PlayerData PDI) {
		super(PDI);
	}

	@Override
	public String getPreOptionText() {
		return "";
	}

	@Override
	public String getHeader() {
		return "Pick A Market";
	}

	@Override
	public Menu getPreviousMenu() {
		return new MainHud(PDI);
	}

	@Override
	public void addOptions() {
		for(MarketPlace<?> market:InspiredNations.Markets) {
			this.options.add(new PromptOption(this, market.getName(), new BuyMenu(PDI, market)));
		}
	}

	@Override
	public void addActionManagers() {
		
	}

}
