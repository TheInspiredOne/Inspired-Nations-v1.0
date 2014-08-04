package com.github.InspiredOne.InspiredNations.Hud.Implem.Player;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.Hud.Menu;
import com.github.InspiredOne.InspiredNations.Hud.Option;
import com.github.InspiredOne.InspiredNations.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNations.Hud.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNations.Hud.Implem.PlayerDirectory;
import com.github.InspiredOne.InspiredNations.Hud.Implem.PlayerProfile;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNations.ToolBox.Relation;

public class RelationList extends TabSelectOptionMenu<PlayerData> {
	
	Relation status;
	PlayerData target;

	public RelationList(PlayerData PDI, PlayerData target, Relation r) {
		super(PDI);
		status = r;
		this.target = target;
		
	}

	@Override
	public Menu getPreviousPrompt() {
		// TODO can't figure out the line below
		return new PlayerProfile(PDI, target);
	}

	@Override
	public String postTabListPreOptionsText() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void addTabOptions() {
		// TODO this has repeated tabs
		for (OwnerGov gov: target.getCitizenship()) {
			for (OwnerGov allygov: gov.getRelations().keySet()) {
				if (gov.getRelations().get(allygov) == status) {
					for (PlayerID ally: allygov.getSubjects()) {
						this.taboptions.add(ally.getPDI());
					}
				}
				
				
			};
		};
		
	}

	@Override
	public void addOptions() {

		this.options.add(new CustomAllyOption(this, "View Profile", this.getData()));
		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getHeader() {
		// TODO Auto-generated method stub
		return "List of " + target.getName() + "'s " + status.toString() ;
	}
	
	
	public class CustomAllyOption extends Option {
		
		PlayerData targetPDI;
		
		public CustomAllyOption(OptionMenu menu, String label, PlayerData playerData) {
			super(menu, label);
			targetPDI = playerData;
		}

		@Override
		public Menu response(String input) {

			return new PlayerProfile(PDI, targetPDI);
		}
		
	}

}
