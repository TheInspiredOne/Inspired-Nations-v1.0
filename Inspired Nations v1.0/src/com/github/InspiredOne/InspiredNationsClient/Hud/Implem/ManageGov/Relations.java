package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.ManageGov;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNations.ToolBox.Relation;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;

public class Relations extends TabSelectOptionMenu<OwnerGov> {

	Menu previous;
	OwnerGov gov;
	
	public Relations(Menu previous, PlayerData PDI, OwnerGov gov) {
		super(PDI);
		this.previous = previous;
		this.gov = gov;
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
		for(OwnerGov govrel:this.gov.getRelations()) {
			this.taboptions.add(govrel);
		}
	}

	@Override
	public void addOptions() {
		this.options.add(new PromptOption(this, "Add Relation", new PickRelationMenu(PDI, this, this, gov)));
		if(!this.taboptions.isEmpty()) {
			if(this.gov.getRelations().get(this.getData()) == Relation.ENEMY) {
				this.options.add(new ChangeRelationOption(this, "Ally " + this.getData().getName(),
						Relation.ALLY, this.getData(), this.gov));
			}
			else {
				this.options.add(new ChangeRelationOption(this, "Enemy " + this.getData().getName(), 
						Relation.ENEMY, this.getData(), this.gov));
			}
			this.options.add(new ChangeRelationOption(this, "Neutral " + this.getData().getName(),
					Relation.NEUTRAL, this.getData(), this.gov));
		}
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getHeader() {
		// TODO Auto-generated method stub
		return "Government Relations";
	}

}
