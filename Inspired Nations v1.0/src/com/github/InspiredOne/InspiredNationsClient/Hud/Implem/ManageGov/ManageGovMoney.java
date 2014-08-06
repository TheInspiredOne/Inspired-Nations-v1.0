package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools.TextColor;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.Money.ManageAccounts;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.Money.PayNav;

public class ManageGovMoney extends OptionMenu {

	InspiredGov gov;
	Menu previous;
	public ManageGovMoney(PlayerData PDI, Menu previous, InspiredGov gov) {
		super(PDI);
		this.gov = gov;
		this.previous = previous;
	}

	@Override
	public String getPreOptionText() {
		String output = "";
		Debug.print(1);
		BigDecimal total = BigDecimal.ZERO;
		String revtemp = "";
		total = gov.getTotalMoney(PDI.getCurrency(), InspiredNations.Exchange.mcdown);
		Debug.print(2);
		output = MenuTools.oneLineWallet("", PDI, gov.getAccounts());
		Debug.print(3);
		for(Class<? extends InspiredGov> govtype:gov.getTaxrates().keySet()) {
			InspiredGov govtemp = GovFactory.getGovInstance(govtype);
			BigDecimal taxrevenue = BigDecimal.ZERO;
			for(InspiredGov govtax:gov.getAllSubGovs(govtype)) {
				taxrevenue = taxrevenue.add(govtax.taxValue(govtax.getRegion().getRegion(), 1, govtax.getProtectionlevel(),
						govtax.getAdditionalCost(PDI.getCurrency()), gov.getTaxrates().get(govtype), PDI.getCurrency()));
			}
			revtemp = revtemp.concat(TextColor.LABEL(PDI) + govtemp.getTypeName() + " (" + TextColor.VALUE(PDI) +
					gov.getTaxrates().get(govtype) + TextColor.LABEL(PDI) + ") : "
					+ TextColor.VALUE(PDI) + Tools.cut(taxrevenue) + " " + TextColor.UNIT(PDI)
					+ PDI.getCurrency()+ "\n");
		}
		Debug.print(4);
		if(!gov.getTaxrates().isEmpty()) {
			output = output.concat(TextColor.SUBHEADER(PDI) + "Tax Revenue\n");
			output = output.concat(revtemp);
		}
		Debug.print(5);
		output = output.concat(TextColor.SUBHEADER(PDI) + "Tax Expenditures\n");
		Debug.print("region is null: " + (gov.getRegion().getRegion() == null));
		Debug.print("Money Name: " + PDI.getCurrency().getDisplayName(PDI));
		Debug.print("additional cost: " + gov.getAdditionalCost(PDI.getCurrency()));
		Debug.print("Protection Level: " + gov.getProtectionlevel());
		Debug.print("tax rate: " + gov.getSuperTaxRate());
		Debug.print("Previous is null: " + (previous == null));
		Debug.print("gov is null: " + (gov == null));
		Debug.print(gov.taxValue(gov.getRegion().getRegion(), 1, gov.getProtectionlevel(), gov.getAdditionalCost(PDI.getCurrency())
				, gov.getSuperTaxRate(), PDI.getCurrency()));
		Debug.print(6);
		output = output.concat(TextColor.LABEL(PDI) + "Expenditure: " + TextColor.VALUE(PDI) + 
				Tools.cut(gov.taxValue(gov.getRegion().getRegion(), 1, gov.getProtectionlevel(), gov.getAdditionalCost(PDI.getCurrency())
						, gov.getSuperTaxRate(), PDI.getCurrency()))) + " " + TextColor.UNIT(PDI) + PDI.getCurrency() + "\n";
		Debug.print(7);
		return output;
		
	}

	@Override
	public Menu getPreviousMenu() {
		return previous;
	}

	@Override
	public String getHeader() {
		return "Manage Gov Money";
	}

	@Override
	public boolean getPassBy() {
		return false;
	}

	@Override
	public Menu getPassTo() {
		return this;
	}

	@Override
	public void addOptions() {
		this.options.add(new PromptOption(this, "Pay", new PayNav(PDI, this, gov.getAccounts())));
		this.options.add(new PromptOption(this, "Manage Accounts", new ManageAccounts(PDI, this, gov.getAccounts())));
		if(this.gov.getAccounts() == PDI.getAccounts()) {
			this.options.add(new SplitAccountOption(this, "Split " + gov.getTypeName() + " Account", "Separates this account from yours", gov));
		}
		else {
			this.options.add(new JoinAccountOption(this, "Join " + gov.getTypeName() + " Account", "Joins this account to yours", gov));
		}
		if(gov.getCommonEcon().equals(gov.getClass())) {
			this.options.add(new RenameMoneyOption(new ManageGovMoney(PDI, previous, gov), "Rename Money <name>", gov));
		}
		if(!gov.getTaxrates().isEmpty() && gov instanceof OwnerGov) {
			this.options.add(new PromptOption(this, "Change Tax Rates", new ChangeTaxRateMenu(PDI, this, (OwnerGov) gov)));
		}
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
