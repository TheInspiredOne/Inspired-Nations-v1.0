package com.github.InspiredOne.InspiredNations.Hud.Implem.ClaimAndUnclaimLand;

import java.util.List;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.Sellable;
import com.github.InspiredOne.InspiredNations.Economy.Implem.ItemSellable;
import com.github.InspiredOne.InspiredNations.Governments.Implem.SignShop;
import com.github.InspiredOne.InspiredNations.Hud.Menu;
import com.github.InspiredOne.InspiredNations.Hud.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNations.Listeners.Implem.ClaimSignShopManager;

public class ClaimSignShop extends TabSelectOptionMenu<ItemSellable> {

	private Menu previous;
	private List<ItemSellable> sell;
	public SignShop shop = new SignShop();
	public ClaimSignShop(PlayerData PDI, Menu previous, List<ItemSellable> sell) {
		super(PDI);
		Debug.print("Inside claim sign shop constructor");
		this.linesperitem = 2;
		this.previous = previous;
		this.sell = sell;
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
	public void addTabOptions() {
		Debug.print("Inside add tab options 1");
		this.taboptions = sell;
		Debug.print("Inside add tab options 2");
	}

	@Override
	public void addOptions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addActionManagers() {
		this.managers.add(new ClaimSignShopManager(this));
	}

	@Override
	public String getHeader() {
		return "Claim Sign Shop";
	}

}
