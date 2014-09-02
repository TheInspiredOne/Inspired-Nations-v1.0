package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ClaimAndUnclaimLand;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNations.Exceptions.InspiredGovTooStrongException;
import com.github.InspiredOne.InspiredNations.Exceptions.InsufficientRefundAccountBalanceException;
import com.github.InspiredOne.InspiredNations.Exceptions.RegionOutOfEncapsulationBoundsException;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Regions.nullRegion;
import com.github.InspiredOne.InspiredNations.Regions.Implem.PolygonPrism;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools.TextColor;
import com.github.InspiredOne.InspiredNationsClient.Hud.InputMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Listeners.Implem.ClaimPolygonPrismManager;
import com.github.InspiredOne.InspiredNationsClient.Listeners.Implem.MapManager;

public class ClaimPolygonPrism extends InputMenu {

	public InspiredGov gov;
	public Menu previous;
	public ClaimPolygonPrismManager<ClaimPolygonPrism> manager;
	public MapManager<ClaimPolygonPrism> mapmanager;
	public ClaimPolygonPrism(PlayerData PDI, Menu previous, InspiredGov gov) {
		super(PDI);
		this.gov = gov;
		this.previous = previous;
	}

	@Override
	public Menu nextMenu() {
		return previous;
	}
	
	@Override
	public String validate(String input) {
		PolygonPrism prism = (PolygonPrism) manager.prism.clone();
		if(input.equalsIgnoreCase("finish")) {
			try {
				if(prism.isSimple()) {
					gov.setLand(prism);
					return MenuError.NO_ERROR();
				}
				else {
					manager.prism = new PolygonPrism();
					return (MenuError.POLYGON_NOT_SIMPLE_SHAPE(this.PDI));
				}
			} catch (BalanceOutOfBoundsException e) {
				manager.prism = new PolygonPrism();
				return (MenuError.NOT_ENOUGH_MONEY(this.PDI));
			} catch (InspiredGovTooStrongException e) {
				manager.prism = new PolygonPrism();
				return (MenuError.GOV_TOO_STRONG(e.gov, this.PDI));
			} catch (RegionOutOfEncapsulationBoundsException e) {
				manager.prism = new PolygonPrism();
				return (MenuError.CLAIM_OUT_OF_BOUNDS(e.gov, this.PDI));
			} catch (InsufficientRefundAccountBalanceException e) {
				manager.prism = new PolygonPrism();
				//TODO do something with this.
			}
		}
		return MenuError.NOT_AN_OPTION(this.PDI);
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
		totalcost = Tools.cut(gov.taxValue(this.manager.prism, 1, gov.getProtectionlevel(), PDI.getCurrency()).subtract(zero));
		// Current cost of selection at the current moment
		BigDecimal currentcost = Tools.cut(BigDecimal.ZERO);
		currentcost = Tools.cut(gov.taxValue(this.manager.prism, InspiredNations.taxTimer.getFractionLeft()
				, gov.getProtectionlevel(), PDI.getCurrency()).subtract(gov.taxValue(new nullRegion(), InspiredNations.taxTimer.getFractionLeft()
						, gov.getProtectionlevel(), PDI.getCurrency())));
		
		String output = this.mapmanager.drawMap(3);
		output = MenuTools.addDivider(output, this.PDI);
		output = output.concat(TextColor.INSTRUCTION(this.PDI) + "Left click each corner of the polygon. The highest and lowest points"
				+ " are top and bottom. Type "+TextColor.VALUE(this.PDI) + "'Finish'"
				+ TextColor.INSTRUCTION(this.PDI) + " when you are done.\n");
		output = MenuTools.oneLineWallet(output, PDI, gov.getAccounts());
		output = output.concat(TextColor.VALUEDESCRI(this.PDI) + "Top: " + TextColor.VALUE(this.PDI) + manager.prism.getMaxHieght() + "\n");
		output = output.concat(TextColor.VALUEDESCRI(this.PDI) + "Bottom: " + TextColor.VALUE(this.PDI) + manager.prism.getMinHieght() + "\n");
		output = output.concat(TextColor.VALUEDESCRI(this.PDI) + "Volume: " + TextColor.VALUE(this.PDI) + manager.prism.volume()+ TextColor.UNIT(this.PDI) + " Cubic Meters\n");
		output = output.concat(TextColor.VALUEDESCRI(this.PDI) + "Total/Current: " + TextColor.VALUE(this.PDI) + totalcost + "/" + currentcost + " " + TextColor.UNIT(this.PDI) +
				PDI.getCurrency());
		return output;
	}

	@Override
	public String getHeader() {
		return "Claim Polygon Prism 1:" + (int) Math.pow(2, mapmanager.zoom);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addActionManagers() {
		manager = new ClaimPolygonPrismManager<ClaimPolygonPrism>(this);
		if(gov.getSuperGovObj().getTier() != 0) {
			this.mapmanager = new MapManager<ClaimPolygonPrism>(this, gov.getSuperGovObj().getTier(), 2);
		}
		else {
			this.mapmanager = new MapManager<ClaimPolygonPrism>(this, 1, 2);
		}
		this.getActionManager().add(manager);
		this.getActionManager().add(mapmanager);
		
	}

}
