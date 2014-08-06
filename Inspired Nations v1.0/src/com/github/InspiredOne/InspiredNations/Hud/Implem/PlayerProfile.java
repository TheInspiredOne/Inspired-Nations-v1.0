package com.github.InspiredOne.InspiredNations.Hud.Implem;


import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.Hud.HelpMenu;
import com.github.InspiredOne.InspiredNations.Hud.Menu;
import com.github.InspiredOne.InspiredNations.Hud.OptionMenu;
import com.github.InspiredOne.InspiredNations.Hud.PromptOption;
import com.github.InspiredOne.InspiredNations.Hud.Implem.Player.PlayerID;
import com.github.InspiredOne.InspiredNations.Hud.Implem.Player.RelationList;
import com.github.InspiredOne.InspiredNations.Hud.Implem.Player.SettingsMenu;
import com.github.InspiredOne.InspiredNations.ToolBox.Datable;
import com.github.InspiredOne.InspiredNations.ToolBox.Relation;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools.TextColor;

public class PlayerProfile extends OptionMenu {
	
	PlayerData PDITarget;
	
	public <T extends Datable<PlayerID>> PlayerProfile(PlayerData PDI, T PDITarget) {
		super(PDI);
		help = new HelpMenu(PDI, this).addPage(
				"Welcome to " + PDITarget.getData().getDisplayName(PDITarget.getData().getPDI()) + "'s profile page. \n"
				);
		
		this.PDITarget = PDITarget.getData().getPDI();
	}
	public <T extends Datable<PlayerID>> PlayerProfile(PlayerData PDI, PlayerData PDITarget) {
		super(PDI);
		
		this.PDITarget = PDITarget;
	}

	@Override
	public String getHeader() {
		return "Profile: " + PDITarget.getName();
	}

	@Override
	public Menu getPreviousMenu() {
		return new PlayerDirectory(PDI);
	}


	@Override
	public boolean getPassBy() {
		return false;
	}

	@Override
	public Menu getPassTo() {
		return this.getSelfPersist();
	}

	@Override
	public void actionResponse() {
		
	}

	@Override
	public void addActionManagers() {
		
	}

	@Override
	public String getPreOptionText() {
		String output = TextColor.LABEL(PDI) + "Citizenship: ";
		for(OwnerGov gov:this.PDITarget.getCitizenship()) {
			output = output.concat(gov.getDisplayName(PDI) + ", ");
		}
		if(output.length() > 0) {
			output = output.substring(0,output.length() - 2) + "\n";
		}
		
		return output;
	}

	@Override
	public void addOptions() {
		// TODO Auto-generated method stub
		this.options.add(new PromptOption(this, "Ally List", new RelationList(PDI, PDITarget, Relation.ALLY)));
		this.options.add(new PromptOption(this, "Enemy List", new RelationList(PDI, PDITarget, Relation.ENEMY)));
		
		if (this.PDITarget.equals(PDI)) {
			this.options.add(new PromptOption(this, "Settings", new SettingsMenu(PDI)));
		}
		
	}

}
