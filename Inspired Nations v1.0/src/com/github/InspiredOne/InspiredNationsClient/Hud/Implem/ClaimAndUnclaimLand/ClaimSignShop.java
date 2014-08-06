package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ClaimAndUnclaimLand;

import java.util.ArrayList;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.Implem.ItemSellable;
import com.github.InspiredOne.InspiredNations.Governments.Implem.ChestShop;
import com.github.InspiredOne.InspiredNations.Governments.Implem.SignShop;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Listeners.Implem.ClaimSignShopManager;

public class ClaimSignShop extends TabSelectOptionMenu<ItemSellable> {

	private Menu previous;
	private ArrayList<ItemSellable> sell;
	public SignShop shop;
	public ClaimSignShop(PlayerData PDI, Menu previous, SignShop signshop) {
		super(PDI);
		Debug.print("Inside claim sign shop constructor");
		this.linesperitem = 2;
		this.previous = previous;
		this.sell = ((ChestShop) signshop.getSuperGovObj()).getItems();
		shop = signshop;
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
		Debug.print("Inside AddActionManagers of ClaimSignShop");
		this.getActionManager().add(new ClaimSignShopManager(this));
	}

	@Override
	public String getHeader() {
		return "Claim Sign Shop";
	}

}
