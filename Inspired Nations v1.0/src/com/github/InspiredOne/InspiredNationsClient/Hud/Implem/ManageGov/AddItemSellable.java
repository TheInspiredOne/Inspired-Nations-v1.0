package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov;

import org.bukkit.inventory.ItemStack;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.Implem.ItemSellable;
import com.github.InspiredOne.InspiredNations.Exceptions.NoShopRegionException;
import com.github.InspiredOne.InspiredNations.Governments.Implem.ChestShop;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;

public class AddItemSellable extends TabSelectOptionMenu<ItemSellable> {

	ChestShop shop;
	Menu previous;
	
	public AddItemSellable(PlayerData PDI, Menu previous, ChestShop shop) {
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
		return "Put the items you would like to sell in the chest.";
	}

	@Override
	public String getHeader() {
		return "Pick Item To Sell";
	}

	@Override
	public void addTabOptions() {
		try {
			for(ItemStack stack:shop.getInventory()) {
				if(stack != null ) {
					stack = stack.clone();
					stack.setAmount(1);
					if(!this.taboptions.contains(new ItemSellable(stack, shop)) && !(new ItemSellable(stack, shop)).isForSale()) {
						this.taboptions.add(new ItemSellable(stack, shop));
					}
				}
			}
		} catch (NoShopRegionException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void addOptions() {
		if(this.taboptions.size() > 0) {
			this.options.add(new PickPriceOption(this, "Sell for <price in " + PDI.getCurrency() + ">", this.getData(), shop));
		}
		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
