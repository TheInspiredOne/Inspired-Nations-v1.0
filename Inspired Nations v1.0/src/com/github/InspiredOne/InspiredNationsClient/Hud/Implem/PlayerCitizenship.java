package com.github.InspiredOne.InspiredNationsClient.Hud.Implem;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools.TextColor;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;

public class PlayerCitizenship extends TabSelectOptionMenu<OwnerGov> {

	public PlayerCitizenship(PlayerData PDI) {
		super(PDI);
	}

	@Override
	public Menu getPreviousPrompt() {
		// TODO Auto-generated method stub
		return new MainHud(PDI);
	}

	@Override
	public String postTabListPreOptionsText() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void addTabOptions() {
		this.taboptions.addAll(PDI.getCitizenship());
		this.taboptions.remove(InspiredNations.global);
	}

	@Override
	public void addOptions() {
		this.options.add(new PromptOption(this, "Ownership Requests and Offers", new OwnerOffers(PDI)));
		this.options.add(new PromptOption(this, "Citizenship Requests and Offers", new SubjectOffers(PDI)));
		
		//If you are an owner, you may completely delete the government
		if(this.getTabOptions().size() > 0) {
			
			if (this.getData().isOwner(PDI.getPlayerID())) {
				this.options.add(new PromptOption(this, "Delete " + this.getData().getName(), new DeleteGovWarning(PDI, this.getData())));
				
			}
			if (this.getData().isSubject(PDI.getPlayerID())) {
				this.options.add(new LeaveGovOption(new PlayerCitizenship(PDI), "Leave " + this.getData().getName(), this.getData()));
			}
		}
		
	}
	

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getHeader() {
		// TODO Auto-generated method stub
		return "Manage Citizenship";
	}


	public class LeaveGovOption extends Option {
		
		OwnerGov gov;
		//Menu citizenship;

		public LeaveGovOption(OptionMenu menu, String label, OwnerGov ownerGov) {
			super(menu, label);
			gov = ownerGov;
			//citizenship = menu;
			
		}

		@Override
		public Menu response(String input) {
			
			gov.removePlayer(PDI.getPlayerID());
			return menu;
		}
		
	}

}

