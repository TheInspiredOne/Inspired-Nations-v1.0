package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ClaimAndUnclaimLand;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNations.Exceptions.CuboidNotCompletedException;
import com.github.InspiredOne.InspiredNations.Exceptions.InspiredGovTooStrongException;
import com.github.InspiredOne.InspiredNations.Exceptions.InsufficientRefundAccountBalanceException;
import com.github.InspiredOne.InspiredNations.Exceptions.RegionOutOfEncapsulationBoundsException;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Regions.nullRegion;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools.TextColor;
import com.github.InspiredOne.InspiredNationsClient.Hud.InputMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Listeners.Implem.ClaimCuboidManager;
import com.github.InspiredOne.InspiredNationsClient.Listeners.Implem.MapManager;

public class ClaimCuboid extends InputMenu {

	public InspiredGov gov;
	public Menu previous;
	private MapManager<ClaimCuboid> mapmanager;
	private ClaimCuboidManager<ClaimCuboid> manager;
	
	
	public ClaimCuboid(PlayerData PDI, Menu previous, InspiredGov gov) {
		super(PDI);
		this.gov = gov;
		this.previous = previous;
	}

	@Override
	public void actionResponse() {
	}

	@Override
	public String getHeader() {
			return "Claim Cuboid 1:" + (int) Math.pow(2, mapmanager.zoom);
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
	public Menu getPassTo() {
		return null;
	}

	@Override
	public Menu nextMenu() {
		this.manager.reset();
		return previous;
	}

	@Override
	public String validate(String input) {
		if(input.equalsIgnoreCase("finish")) {
			try {
				gov.setLand(this.manager.getCuboid());
				this.manager.reset();
				return MenuError.NO_ERROR();
				
			} catch (BalanceOutOfBoundsException e) {
				this.manager.reset();
				return MenuError.NOT_ENOUGH_MONEY(this.PDI);
			} catch (InspiredGovTooStrongException e) {
				this.manager.reset();
				return MenuError.GOV_TOO_STRONG(e.gov,this.PDI);
			} catch (RegionOutOfEncapsulationBoundsException e) {
				this.manager.reset();
				return MenuError.CLAIM_OUT_OF_BOUNDS(e.gov,this.PDI);
			} catch (InsufficientRefundAccountBalanceException e) {
				// TODO figure out what to do here.
				return MenuError.MONEY_NAME_ALREADY_TAKEN(this.PDI);
			} catch (CuboidNotCompletedException e) {
				return MenuError.CUBOID_NOT_FULLY_SELECTED(this.PDI);
			}
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
		
		// Tax paid that is independent of chunks
		BigDecimal zero = gov.taxValue(new nullRegion(), InspiredNations.taxTimer.getFractionLeft(), gov.getProtectionlevel(), PDI.getCurrency());
		// Total tax from selection
		BigDecimal totalcost = Tools.cut(BigDecimal.ZERO);
		try {
			totalcost = Tools.cut(gov.taxValue(this.manager.getCuboid(), 1, gov.getProtectionlevel(), PDI.getCurrency()).subtract(zero));
		} catch (CuboidNotCompletedException e1) {
		}
		// Current cost of selection at the current moment
		BigDecimal currentcost = Tools.cut(BigDecimal.ZERO);
		try {
			currentcost = Tools.cut(gov.taxValue(this.manager.getCuboid(), InspiredNations.taxTimer.getFractionLeft()
					, gov.getProtectionlevel(), PDI.getCurrency()).subtract(gov.taxValue(new nullRegion(), InspiredNations.taxTimer.getFractionLeft()
							, gov.getProtectionlevel(), PDI.getCurrency())));
		} catch (CuboidNotCompletedException e1) {
		}
		
		String output = this.mapmanager.drawMap(3);
		output = MenuTools.addDivider(output, this.PDI);
		output = output.concat(TextColor.INSTRUCTION(this.PDI) + "Left Click for one corner of the cuboid and Right Click for the other corner. Type "
				+ TextColor.VALUE(this.PDI) + "'Finish'" + TextColor.INSTRUCTION(this.PDI) + " when you are done.\n");
		output = MenuTools.oneLineWallet(output, PDI, gov.getAccounts());
		try {
			output = output.concat(TextColor.VALUEDESCRI(this.PDI) + "Low Point: " + TextColor.VALUE(this.PDI) + manager.getCuboid().getPointMin() + "\n");
		} catch (CuboidNotCompletedException e) {
			try {
				output = output.concat(TextColor.VALUEDESCRI(this.PDI) + "Low Point: " + TextColor.VALUE(this.PDI) + manager.point1.toString() + "\n");
			}
			catch (Exception ex) {
				output = output.concat(TextColor.VALUEDESCRI(this.PDI) + "Low Point: " + TextColor.VALUE(this.PDI) + "Not Selected\n");
			}
		}
		try {
			output = output.concat(TextColor.VALUEDESCRI(this.PDI) + "High Point: " + TextColor.VALUE(this.PDI) + manager.getCuboid().getPointMax() + "\n");
		} catch (CuboidNotCompletedException e) {
			try {
				output = output.concat(TextColor.VALUEDESCRI(this.PDI) + "High Point: " + TextColor.VALUE(this.PDI) + manager.point2.toString() + "\n");
			}
			catch (Exception ex) {
				output = output.concat(TextColor.VALUEDESCRI(this.PDI) + "High Point: " + TextColor.VALUE(this.PDI) + "Not Selected\n");
			}
		}
		output = output.concat(TextColor.VALUEDESCRI(this.PDI) + "Volume: " + TextColor.VALUE(this.PDI) + manager.getVolume() + TextColor.UNIT(this.PDI) + " Cubic Meters\n");
		output = output.concat(TextColor.VALUEDESCRI(this.PDI) + "Total/Current: " + TextColor.VALUE(this.PDI) + totalcost + "/" + currentcost + " " + TextColor.UNIT(this.PDI) +
				PDI.getCurrency());
		return output;
	}

	@Override
	public void addTabOptions() {
		
	}

	@Override
	public void addActionManagers() {
		if(gov.getSuperGovObj().getTier() != 0) {
			this.mapmanager = new MapManager<ClaimCuboid>(this, gov.getSuperGovObj().getTier(), 2);
		}
		else {
			this.mapmanager = new MapManager<ClaimCuboid>(this, 1, 2);
		}
		this.manager = new ClaimCuboidManager<ClaimCuboid>(this);
		this.getActionManager().add(mapmanager);
		this.getActionManager().add(manager);
		
	}
}
