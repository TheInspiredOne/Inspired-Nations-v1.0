package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.NewGov;

import java.util.ArrayList;
import java.util.List;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.Currency;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNationsClient.Hud.InputMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;

public class PickMoneyName<T extends OwnerGov> extends InputMenu{

	GovFactory<T> Govf;
	public PickMoneyName(PlayerData PDI, GovFactory<T> Govf) {
		super(PDI);
		this.Govf = Govf;
	}

	@Override
	public Menu nextMenu() {
		return new PickEconomy<T>(PDI, Govf);
	}

	@Override
	public String validate(String input) {
		for(Currency currency:InspiredNations.Exchange.getExchangeMap().keySet()) {
			if(currency.getName().equalsIgnoreCase(input)) {
				return MenuError.MONEY_NAME_ALREADY_TAKEN(this.getPlayerData());
			}
		}
		return MenuError.NO_ERROR();
	}

	@Override
	public void useInput(String input) {
		Govf = Govf.withMoneyname(input);
	}

	@Override
	public Menu getPreviousMenu() {
		return new PickName<T>(PDI, Govf);
	}

	@Override
	public String getHeader() {
		return "Pick Money Name.";
	}

	@Override
	public String getInstructions() {
		return "Type the name of your currency in plural form.";
	}

	@Override
	public boolean getPassBy() {
		if(Govf.getGov().getCommonEcon().equals(Govf.getGov().getClass())) {
			return false;
		}
		else {
			return true;
		}
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
