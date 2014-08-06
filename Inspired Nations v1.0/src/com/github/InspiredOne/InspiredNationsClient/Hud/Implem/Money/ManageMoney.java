package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.Money;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.ToolBox.IndexedMap;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools.TextColor;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.MainHud;

public class ManageMoney extends OptionMenu {
	//private MathContext mcup = new MathContext(5, RoundingMode.UP);//this is temporary

	public ManageMoney(PlayerData PDI) {
		super(PDI);
	}

	@Override
	public String getPreOptionText() {
		BigDecimal total = BigDecimal.ZERO;
		//TODO get rid of this line eventually
		total = Tools.cut(PDI.getAccounts().getTotalMoney(PDI.getCurrency(), InspiredNations.Exchange.mcdown));
		String output = MenuTools.oneLineWallet("", PDI, PDI.getAccounts());
		IndexedMap<Class<? extends InspiredGov>, BigDecimal> taxmap = PDI.getAccounts().getTaxes(PDI.getCurrency());
		if(!taxmap.isEmpty()) {
			output = output.concat(TextColor.SUBHEADER(this.getPlayerData()) + "Taxes\n");
		}
		for(Class<? extends InspiredGov> govtype:taxmap) {
			InspiredGov gov = GovFactory.getGovInstance(govtype);
			output = output.concat(TextColor.VALUEDESCRI(this.getPlayerData()) + gov.getTypeName() + ": " + TextColor.VALUE(this.getPlayerData()) +
					Tools.cut(taxmap.get(govtype))) + TextColor.UNIT(this.getPlayerData()) + " " + PDI.getCurrency() +"\n";
		}
		
		
		return output; 
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
	public String getHeader() {
		return "Manage Money";
	}

	@Override
	public Menu getPreviousMenu() {
		return new MainHud(PDI);
	}

	@Override
	public void addOptions() {
		this.options.add(new PromptOption(this, "Pay", new PayNav(PDI, this, PDI)));
		this.options.add(new PromptOption(this, "Manage Accounts", new ManageAccounts(PDI, this, PDI.getAccounts())));
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
