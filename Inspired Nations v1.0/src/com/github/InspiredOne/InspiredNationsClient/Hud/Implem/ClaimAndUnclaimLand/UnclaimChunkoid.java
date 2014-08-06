package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ClaimAndUnclaimLand;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Exceptions.PlayerOfflineException;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Regions.CummulativeRegion;
import com.github.InspiredOne.InspiredNations.Regions.nullRegion;
import com.github.InspiredOne.InspiredNations.Regions.Implem.ChunkRegion;
import com.github.InspiredOne.InspiredNations.Regions.Implem.Chunkoid;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools.TextColor;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNations.ToolBox.Point2D;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools;
import com.github.InspiredOne.InspiredNationsClient.Hud.InputMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Listeners.Implem.MapManager;
import com.github.InspiredOne.InspiredNationsClient.Listeners.Implem.UnclaimChunkoidManager;

public class UnclaimChunkoid extends InputMenu {

	UnclaimChunkoidManager<UnclaimChunkoid> manager;
	MapManager<UnclaimChunkoid> mapmanager;
	
	Menu previous;
	public InspiredGov gov;
	
	public UnclaimChunkoid(PlayerData PDI, Menu previous, InspiredGov gov) {
		super(PDI);
		this.previous = previous;
		this.gov = gov;
	}

	@Override
	public Menu nextMenu() {
		return this;
	}

	@Override
	public String validate(String input) {
		if(input.equalsIgnoreCase("begin") || input.equalsIgnoreCase("stop")) {
			return MenuError.NO_ERROR();
		}
		else {
			return MenuError.NOT_AN_OPTION(this.PDI);
		}
	}

	@Override
	public void useInput(String input) {
		if(input.equalsIgnoreCase("begin")) {
			this.manager.setUnclaim(true);
		}
		else if(input.equalsIgnoreCase("stop")) {
			this.manager.setUnclaim(false);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getInstructions() {
		Chunkoid unit = null;
		try {
			unit = new Chunkoid(new Point2D(0,0,PDI.getPlayer().getWorld()));
		} catch (PlayerOfflineException e) {
			e.printStackTrace();
		}
		// Tax paid that is independent of chunks
		BigDecimal zero = gov.taxValue(new nullRegion(), InspiredNations.taxTimer.getFractionLeft(), gov.getProtectionlevel(), PDI.getCurrency());
		// Total tax from chunks
		BigDecimal totalcost = Tools.cut(gov.taxValue(gov.getRegion().getRegion(), 1, gov.getProtectionlevel(), PDI.getCurrency()).subtract(zero));
		// Total cost of one chunk
		BigDecimal unitcost = Tools.cut(gov.taxValue(unit, 1, gov.getProtectionlevel(), PDI.getCurrency()).subtract(zero));
		// Current cost of one chunk at the current moment
		BigDecimal currenccost = Tools.cut(gov.taxValue(unit, InspiredNations.taxTimer.getFractionLeft()
				, gov.getProtectionlevel(), PDI.getCurrency()).subtract(gov.taxValue(new nullRegion(), InspiredNations.taxTimer.getFractionLeft()
						, gov.getProtectionlevel(), PDI.getCurrency())));
		
		String output = this.mapmanager.drawMap(4);
		output = MenuTools.addDivider(output, this.PDI);
		output = output.concat(TextColor.INSTRUCTION(this.PDI) + "Type '" + TextColor.VALUE(this.PDI) + "begin" + TextColor.INSTRUCTION(this.PDI)
				+ "' and walk around to unclaim. Type '" + TextColor.VALUE(this.PDI) + "stop" + TextColor.INSTRUCTION(this.PDI) + "' to stop.\n");
		output = MenuTools.oneLineWallet(output, PDI, gov.getAccounts());
		output = output.concat(TextColor.VALUEDESCRI(this.PDI) + "Total Chunks: " + TextColor.VALUE(this.PDI) + ((CummulativeRegion<ChunkRegion>) gov.getRegion().getRegion()).getRegions().size()
				+ TextColor.UNIT(this.PDI) + " Chunks\n");
		output = output.concat(TextColor.VALUEDESCRI(this.PDI) + "Total/Unit/Current: " + TextColor.VALUE(this.PDI) +
				totalcost + "/" + unitcost + "/" + currenccost + TextColor.UNIT(this.PDI) + " " + PDI.getCurrency() + "\n");
		return output;
	}

	@Override
	public String getHeader() {
		return "Unclaim Chunkoid";
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
		if(gov.getSuperGovObj().getTier() != 0) {
			this.mapmanager = new MapManager<UnclaimChunkoid>(this, gov.getSuperGovObj().getTier(), 4);
		}
		else {
			this.mapmanager = new MapManager<UnclaimChunkoid>(this, 1, 4);
		}
		
		try {
			manager = new UnclaimChunkoidManager<UnclaimChunkoid>(this, new Point2D(PDI.getPlayer().getLocation().getChunk()));
		} catch (PlayerOfflineException e) {
			e.printStackTrace();
		}
		this.getActionManager().add(mapmanager);
		this.getActionManager().add(manager);
		
	}

}
