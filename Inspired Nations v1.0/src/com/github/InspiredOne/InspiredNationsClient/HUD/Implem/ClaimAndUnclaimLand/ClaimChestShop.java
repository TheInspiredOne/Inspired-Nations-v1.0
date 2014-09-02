package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ClaimAndUnclaimLand;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNations.Exceptions.InspiredGovTooStrongException;
import com.github.InspiredOne.InspiredNations.Exceptions.InsufficientRefundAccountBalanceException;
import com.github.InspiredOne.InspiredNations.Exceptions.RegionOutOfEncapsulationBoundsException;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools.TextColor;
import com.github.InspiredOne.InspiredNationsClient.Hud.InputMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Listeners.Implem.ClaimChestShopManager;
import com.github.InspiredOne.InspiredNationsClient.Listeners.Implem.MapManager;

public class ClaimChestShop extends InputMenu {

	public InspiredGov gov;
	Menu previous;
	private ClaimChestShopManager manager;
	private MapManager<ClaimChestShop> mapmanager;

	public ClaimChestShop(PlayerData PDI, Menu previous, InspiredGov gov) {
		super(PDI);
		this.gov = gov;
		this.previous = previous;
		manager = new ClaimChestShopManager(this);
	}

	@Override
	public Menu nextMenu() {
		return previous;
	}

	@Override
	public String validate(String input) {
		if(input.equalsIgnoreCase("finish")) {
			try {
				gov.setLand(manager.region);
				return MenuError.NO_ERROR();
			} catch (BalanceOutOfBoundsException e) {
				return MenuError.NOT_ENOUGH_MONEY(this.PDI);
			} catch (InspiredGovTooStrongException e) {
				return MenuError.GOV_TOO_STRONG(e.gov,this.PDI);
			} catch (RegionOutOfEncapsulationBoundsException e) {
				return MenuError.CLAIM_OUT_OF_BOUNDS(e.gov,this.PDI);
			} catch (InsufficientRefundAccountBalanceException e) {
				e.printStackTrace();
			}
			return MenuError.NO_ERROR();
		}
		else {
			return MenuError.NOT_AN_OPTION(this.PDI);
		}
	}

	@Override
	public void useInput(String input) {

	}

	@Override
	public String getInstructions() {
		String output = mapmanager.drawMap(4);
		output = MenuTools.addDivider(output, this.PDI);
		output = output.concat(TextColor.INSTRUCTION(this.PDI) + "Left click on the chest that you would like to claim. Type '"
				+ TextColor.VALUE(this.PDI) + "finish" + TextColor.INSTRUCTION(this.PDI) +"' when you are done.\n");
		output = output.concat(TextColor.LABEL(this.PDI) + "Selected: " + TextColor.VALUE(this.PDI));
		if(manager.region.volume() == 0) {
			output = output.concat("No Chest Selected");
		}
		else if(manager.region.volume() == 1) {
			output = output.concat("Single Chest Selected");
		}
		else {
			output = output.concat("Double Chest Selected");
		}
		output = output.concat("\n");
		return output;
	}

	@Override
	public String getHeader() {
		return "Claim Chest Shop: " + gov.getName();
	}

	@Override
	public Menu getPreviousMenu() {
		return previous;
	}

	@Override
	public boolean getPassBy() {
		return false;
	}

	@Override
	public void addTabOptions() {
		
	}

	@Override
	public void addActionManagers() {
		this.getActionManager().add(manager);
		if(gov.getSuperGovObj().getTier() != 0) {
			this.mapmanager = new MapManager<ClaimChestShop>(this, gov.getSuperGovObj().getTier(), 1);
		}
		else {
			this.mapmanager = new MapManager<ClaimChestShop>(this, 1, 1);
		}
		this.getActionManager().add(this.mapmanager);
		
	}
}
