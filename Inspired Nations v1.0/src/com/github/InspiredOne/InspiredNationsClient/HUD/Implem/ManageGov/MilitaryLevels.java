package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.OwnerSubjectGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools.TextColor;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class MilitaryLevels extends OptionMenu {

	Menu previous;
	OwnerSubjectGov gov;
	
	public MilitaryLevels(PlayerData PDI, Menu previous, OwnerSubjectGov gov) {
		super(PDI);
		this.previous = previous;
		this.gov = gov;
	}

	@Override
	public String getPreOptionText() {
		BigDecimal taxvalue = gov.currentTaxCycleValue(PDI.getCurrency());
		BigDecimal reimmburs = gov.taxValue(gov.getRegion().getRegion(), InspiredNations.taxTimer.getFractionLeft()
				, gov.getProtectionlevel(), gov.getAdditionalCost(PDI.getCurrency()),
				gov.getSuperTaxRate(), PDI.getCurrency());
		
		BigDecimal oneLevelUp = gov.taxValue(gov.getRegion().getRegion(), 1,
				gov.getProtectionlevel(), gov.getAdditionalCost(gov.getMilitaryLevel() + 1, PDI.getCurrency()), gov.getSuperTaxRate(),PDI.getCurrency());
		
		BigDecimal oneLevelUpCurrentCost = gov.taxValue(gov.getRegion().getRegion(), InspiredNations.taxTimer.getFractionLeft(),
				gov.getProtectionlevel(), gov.getAdditionalCost(gov.getMilitaryLevel() + 1, PDI.getCurrency()),
				gov.getSuperTaxRate(), PDI.getCurrency());
		
		BigDecimal oneLevelDown = gov.taxValue(gov.getRegion().getRegion(), 1,
				gov.getProtectionlevel(), gov.getAdditionalCost(gov.getMilitaryLevel() - 1, PDI.getCurrency()),
				gov.getSuperTaxRate(), PDI.getCurrency());
		
		BigDecimal oneLevelDownCurrentCost = gov.taxValue(gov.getRegion().getRegion(), InspiredNations.taxTimer.getFractionLeft(),
				gov.getProtectionlevel(), gov.getAdditionalCost(gov.getMilitaryLevel() - 1, PDI.getCurrency()),
				gov.getSuperTaxRate(), PDI.getCurrency());
		
		String output = TextColor.SUBHEADER(PDI) + "Pricing:\n";
		output = output.concat(TextColor.LABEL(PDI) + "+1 Level Cost: " + TextColor.VALUE(PDI) + Tools.cut(oneLevelUp) + " "+ TextColor.UNIT(PDI) + PDI.getCurrency() + "\n");
		if(gov.getMilitaryLevel() >= 1) {
			output = output.concat(TextColor.LABEL(PDI) + "-1 Level Cost: " +TextColor.VALUE(PDI) + 
					Tools.cut(oneLevelDown) + TextColor.UNIT(PDI) + " " +
					PDI.getCurrency() + "\n");
		}
		output = output.concat(TextColor.SUBHEADER(PDI) + "Stats:\n");
		output = output.concat(TextColor.LABEL(PDI) + "Current Military Level: " + TextColor.VALUE(PDI) + gov.getMilitaryLevel() + "\n");
		output = output.concat(TextColor.LABEL(PDI) + "Current Total Cost: " + TextColor.VALUE(PDI) + Tools.cut(taxvalue) +
				TextColor.UNIT(PDI) + " " + PDI.getCurrency() + "\n");
		output = MenuTools.oneLineWallet(output, PDI, gov.getAccounts());
		return output;
	}

	@Override
	public String getHeader() {
		return "Military Levels";
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
	public void addOptions() {
		this.options.add(new SetMilitaryLevelOption(this, "Set Military Level <level>", gov));
		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
