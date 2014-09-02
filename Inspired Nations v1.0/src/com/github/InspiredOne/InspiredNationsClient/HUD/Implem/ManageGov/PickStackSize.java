package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.Implem.ItemSellable;
import com.github.InspiredOne.InspiredNations.Governments.Implem.ChestShop;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools.TextColor;
import com.github.InspiredOne.InspiredNationsClient.Hud.InputMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;

public class PickStackSize extends InputMenu {

	Menu previous;
	ItemSellable item;
	ChestShop shop;
	public PickStackSize(PlayerData PDI, Menu previous, ItemSellable item, ChestShop shop) {
		super(PDI);
		this.previous = previous;
		this.item = item;
		this.shop = shop;
	}

	@Override
	public Menu nextMenu() {
		shop.getItems().add(item);
		return previous;
	}

	@Override
	public String validate(String input) {
		try {
			Integer.parseInt(input);
		}
		catch (Exception ex) {
			return MenuError.INVALID_NUMBER_INPUT(PDI);
		}
		return MenuError.NO_ERROR();
	}

	@Override
	public void useInput(String input) {
		Debug.print(input + " = input amount");
		int temp = Integer.parseInt(input);
		this.item.setAmount(temp);
		Debug.print("How much ended up in the stack " + this.item.getItem().getAmount());
	}

	public String getInstructions() {
		return "Type the amount you would like to sell with each sale. Limit "+ TextColor.VALUE(PDI)  + "1 - "
	+ this.item.getItem().getMaxStackSize() + TextColor.INSTRUCTION(PDI) + ".";
	}

	@Override
	public String getHeader() {
		return "Pick Amount";
	}

	@Override
	public Menu getPreviousMenu() {
		return previous;
	}

	@Override
	public boolean getPassBy() {
		return (item.getItem().getMaxStackSize() == 1);
	}

	@Override
	public void addTabOptions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}
}
