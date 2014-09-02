package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.Implem.ItemSellable;
import com.github.InspiredOne.InspiredNations.Governments.Implem.ChestShop;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.IgnoreOfferOption;

public class ManageItemsForSale extends TabSelectOptionMenu<ItemSellable> {

	ChestShop shop;
	Menu previous;
	public ManageItemsForSale(PlayerData PDI, Menu previous, ChestShop shop) {
		super(PDI);
		this.shop = shop;
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
		return "";
	}

	@Override
	public void addTabOptions() {
		for(ItemSellable item:shop.getItems()) {
			this.taboptions.add(item);
		}
		
	}

	@Override
	public void addOptions() {
		this.options.add(new PromptOption(this, "Add Items To Sell", new AddItemSellable(PDI, this, shop)));
		if(this.taboptions.size() > 0) {
			this.options.add(new IgnoreOfferOption(this, "Remove Item", this.getData(), shop.getItems()));
		}
		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
