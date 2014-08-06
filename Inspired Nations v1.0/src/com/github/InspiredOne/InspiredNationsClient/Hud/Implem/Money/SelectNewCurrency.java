package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.Money;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.Currency;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;

public class SelectNewCurrency extends TabSelectOptionMenu<Currency> {

	Menu previous;
	
	public SelectNewCurrency(PlayerData PDI, Menu previous) {
		super(PDI);
		this.previous = previous;
	}

	@Override
	public Menu getPreviousPrompt() {
		return previous;
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}

	@Override
	public String getHeader() {
		return "Pick Currency";
	}

	@Override
	public void addTabOptions() {
		for(Currency curren:InspiredNations.Exchange.getExchangeMap().keySet()) {
			this.taboptions.add(curren);
		}
		
	}

	@Override
	public void addOptions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}
}
