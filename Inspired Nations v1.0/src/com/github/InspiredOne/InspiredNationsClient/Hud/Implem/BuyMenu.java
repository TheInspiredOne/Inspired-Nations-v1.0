package com.github.InspiredOne.InspiredNationsClient.Hud.Implem;

import java.util.Collections;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.MarketPlace;
import com.github.InspiredOne.InspiredNations.Economy.Sellable;
import com.github.InspiredOne.InspiredNations.ToolBox.SortTool;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;

public class BuyMenu<T extends Sellable> extends TabSelectOptionMenu<T> {

	MarketPlace<T> market;
	SortTool<T> sorter;
	
	public BuyMenu(PlayerData PDI, MarketPlace<T> market) {
		super(PDI);
		this.market = market;
	}

	@Override
	public Menu getPreviousPrompt() {
		return new PickMarketplace(PDI);
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}

	@Override
	public String getHeader() {
		return market.getName() + " Sorted: " + sorter.getDisplayName(PDI);
	}

	@Override
	public void addTabOptions() {
		for(T item:market.getSales(this.PDI)) {
			this.taboptions.add(item);
		}
		Debug.print(sort % market.getComparators(PDI).size());
		sorter = market.getComparators(PDI).get(sort % market.getComparators(PDI).size());
		Collections.sort(taboptions, sorter.getComparator());
		this.linesperitem=2;
	}

	@Override
	public void addOptions() {
		if(this.taboptions.size() > 0) {
			this.options.add(new MakePurchaseOption(this, "Buy", this.getData()));
		}
	}

	@Override
	public void addActionManagers() {
	}

}
