package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools.TextColor;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class ProtectionLevels extends OptionMenu {

	private InspiredGov gov;
	private Menu previous;
	
	public ProtectionLevels(PlayerData PDI, Menu previous, InspiredGov gov) {
		super(PDI);
		this.gov = gov;
		this.previous = previous;
		//this.help.addPage("");
		}

	@Override
	public String getPreOptionText() {
		BigDecimal taxvalue = gov.currentTaxCycleValue(PDI.getCurrency());
		BigDecimal reimmburs = gov.taxValue(gov.getRegion().getRegion(), InspiredNations.taxTimer.getFractionLeft(), gov.getProtectionlevel(), PDI.getCurrency());
		BigDecimal oneLevelUp = gov.taxValue(gov.getRegion().getRegion(), 1, gov.getProtectionlevel()+1, PDI.getCurrency());
		BigDecimal oneLevelUpCurrentCost = gov.taxValue(gov.getRegion().getRegion(), InspiredNations.taxTimer.getFractionLeft(),
				gov.getProtectionlevel() + 1, PDI.getCurrency());
		BigDecimal oneLevelDown = gov.taxValue(gov.getRegion().getRegion(), 1, gov.getProtectionlevel()-1, PDI.getCurrency());
		BigDecimal oneLevelDownCurrentCost = gov.taxValue(gov.getRegion().getRegion(), InspiredNations.taxTimer.getFractionLeft(),
				gov.getProtectionlevel() - 1, PDI.getCurrency());
		
		String output = TextColor.SUBHEADER(PDI) + "Pricing:\n";
		output = output.concat(TextColor.LABEL(PDI) + "Current Full Reimbursement: " + TextColor.VALUE(PDI) + Tools.cut(reimmburs) + TextColor.UNIT(PDI) + " " + PDI.getCurrency() + "\n");
		output = output.concat(TextColor.LABEL(PDI) + "+1 Level Cost/Net Cost: " + TextColor.VALUE(PDI) + Tools.cut(oneLevelUp) +
				"/" + Tools.cut(oneLevelUpCurrentCost.subtract(reimmburs)) + " "+ TextColor.UNIT(PDI) + PDI.getCurrency() + "\n");
		if(gov.getProtectionlevel() >= 1) {
			output = output.concat(TextColor.LABEL(PDI) + "-1 Level Cost/Net Reimburs: " +TextColor.VALUE(PDI) + 
					Tools.cut(oneLevelDown) + "/" + Tools.cut(reimmburs.subtract(oneLevelDownCurrentCost)) + TextColor.UNIT(PDI) + " " +
					PDI.getCurrency() + "\n");
		}
		output = output.concat(TextColor.SUBHEADER(PDI) + "Stats:\n");
		output = output.concat(TextColor.LABEL(PDI) + "Current Protection Level: " + TextColor.VALUE(PDI) + gov.getProtectionlevel() + "\n");
		output = output.concat(TextColor.LABEL(PDI) + "Current Total Cost: " + TextColor.VALUE(PDI) + Tools.cut(taxvalue) +
				TextColor.UNIT(PDI) + " " + PDI.getCurrency() + "\n");
		output = MenuTools.oneLineWallet(output, PDI, gov.getAccounts());
		return output;
	}

	@Override
	public String getHeader() {
		return "Protection Levels";
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
		this.options.add(new SetProtectionLevelOption(this, "Set Protection Level <level>", gov));
		
	}

	@Override
	public void addActionManagers() {
		
	}

}
