package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.Economy.Implem.ItemSellable;
import com.github.InspiredOne.InspiredNations.Governments.Implem.ChestShop;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class PickPriceOption extends Option {

	ItemSellable item;
	ChestShop shop;
	
	public PickPriceOption(OptionMenu menu, String label, OptionUnavail reason, ItemSellable item, ChestShop shop) {
		super(menu, label, reason);
		this.item = item;
		this.shop = shop;
	}

	public PickPriceOption(OptionMenu menu, String label, ItemSellable item, ChestShop shop) {
		super(menu, label);
		this.item = item;
		this.shop = shop;
	}

	public PickPriceOption(OptionMenu menu, String label, String description, ItemSellable item, ChestShop shop) {
		super(menu, label, description);
		this.item = item;
		this.shop = shop;
	}

	@Override
	public Menu response(String input) {
		try{
			BigDecimal price = new BigDecimal(input);
			if(price.compareTo(BigDecimal.ZERO) < 0) {
				menu.setError(MenuError.NEGATIVE_AMOUNTS_NOT_ALLOWED(price.negate(), menu.PDI));
				return menu;
			}
			else {
				this.item.setPrice(price, menu.getPlayerData().getCurrency());
			}
		}
		catch (Exception ex) {
			menu.setError(MenuError.INVALID_NUMBER_INPUT(menu.PDI));
			return menu;
		}
		
		return new PickStackSize(menu.PDI, menu, item, shop);
	}

}
