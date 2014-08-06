package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.NewGov;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuAlert;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNationsClient.Hud.InputMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.MainHud;

public class PickEconomy<T extends OwnerGov> extends InputMenu{

	public GovFactory<T> Govf;
	
	public PickEconomy(PlayerData PDI, GovFactory<T> Govf) {
		super(PDI);
		this.Govf = Govf;
	}

	@Override
	public Menu nextMenu() {
		Govf.registerGov();
		PDI.setCurrency(Govf.getGov().getCurrency());
		PDI.sendNotification(MenuAlert.GOV_MADE_SUCCESSFULLY(Govf.getGov()));
		return new MainHud(PDI);
	}

	@Override
	public String validate(String input) {
		try {
			BigDecimal answer = new BigDecimal(input);
			if(answer.compareTo(new BigDecimal(10000)) > 0) {
				return MenuError.MONEY_MULTIPLYER_TOO_LARGE(this.getPlayerData());
			}
			else if(answer.compareTo(new BigDecimal(50)) < 0) {
				return MenuError.MONEY_MULTIPLYER_TOO_SMALL(this.getPlayerData());
			}
			return MenuError.NO_ERROR();
		}
		
		catch (Exception ex) {
			return MenuError.INVALID_NUMBER_INPUT(this.getPlayerData());
		}
	}

	@Override
	public void useInput(String input) {
		Govf = Govf.withDiamondValue(new BigDecimal(input));
	}

	@Override
	public Menu getPreviousMenu() {
		return new PickMoneyName<T>(PDI, Govf);
	}

	@Override
	public String getHeader() {
		return "Set Up Economy";
	}

	@Override
	public String getInstructions() {
		return "Type the price of the diamond in " + Govf.getGov().getCurrency().getName() + ".";
	}

	@Override
	public boolean getPassBy() {
		if(Govf.getGov().getCommonEcon().equals(Govf.getGov().getClass())) {
			return false;
		}
		else return true;
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
