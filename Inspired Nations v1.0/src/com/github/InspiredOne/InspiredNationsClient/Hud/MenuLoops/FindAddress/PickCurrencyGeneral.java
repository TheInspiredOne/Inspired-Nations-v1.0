package com.github.InspiredOne.InspiredNationsClient.Hud.MenuLoops.FindAddress;

import java.util.TreeSet;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.Currency;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;

public abstract class PickCurrencyGeneral extends TabSelectOptionMenu<Currency> {

	Menu previous;
	public PickCurrencyGeneral(PlayerData PDI, Menu previous) {
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

	/**
	 * 
	 * @param curren
	 * @return	<code>true</code> if the currency should be added
	 */
	public abstract boolean check(Currency curren);

	@Override
	public String getHeader() {
		return "Select Currency";
	}

	@Override
	public void addTabOptions() {
		TreeSet<Currency> currens = new TreeSet<Currency>(InspiredNations.Exchange.getExchangeMap().keySet());
		for(Currency curren:currens) {
			if(check(curren)) {
				this.taboptions.add(curren);
			}
		}
		
	}
}
