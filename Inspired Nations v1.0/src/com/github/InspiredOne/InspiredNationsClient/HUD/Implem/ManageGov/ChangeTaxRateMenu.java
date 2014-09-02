package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ManageGov;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.Remotes.InspiredGovPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.OwnerGovPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;


public class ChangeTaxRateMenu extends TabSelectOptionMenu<TaxRateOption> {

	OwnerGovPortal gov;
	Menu previous;
	
	public ChangeTaxRateMenu(PlayerDataPortal PDI, Menu previous, OwnerGovPortal gov) {
		super(PDI);
		this.gov = gov;
		this.previous = previous;
	}

	@Override
	public Menu getPreviousPrompt() {
		return previous;
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}

	@Override
	public void addTabOptions() {
		for(Class<? extends InspiredGovPortal> govtype:gov.getTaxrates()) {
			this.taboptions.add(new TaxRateOption(gov.getTaxrates().get(govtype), govtype));
		}
	}

	@Override
	public void addOptions() {
		if(this.taboptions.size() > 0) {
			this.options.add(new ChangeTaxOption(this, "Change Tax Rate <rate>", this.getData(), gov));
		}
		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getHeader() {
		// TODO Auto-generated method stub
		return "Change Tax Rate";
	}
	
	public class TaxRateOption implements Nameable {

		Class<? extends InspiredGovPortal> gov;
		double rate;
		public TaxRateOption(double rate, Class<? extends InspiredGovPortal> type) {
			this.rate = rate;
			this.gov = type;
		}
		
		@Override
		public String getName() {
			return "";
		}

		@Override
		public void setName(String name) throws NameAlreadyTakenException {
			
		}

		@Override
		public String getDisplayName(PlayerData viewer) {
			InspiredGovPortal govtemp = GovFactory.getGovInstance(gov);
			return PDI.LABEL() + govtemp.getTypeName() + PDI.VALUE() + " " + rate;
		}
	}
}
