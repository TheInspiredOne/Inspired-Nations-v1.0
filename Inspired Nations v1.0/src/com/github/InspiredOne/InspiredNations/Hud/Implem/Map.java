package com.github.InspiredOne.InspiredNations.Hud.Implem;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Governments.GovFactory;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Hud.HelpMenu;
import com.github.InspiredOne.InspiredNations.Hud.InputMenu;
import com.github.InspiredOne.InspiredNations.Hud.Menu;
import com.github.InspiredOne.InspiredNations.Listeners.Implem.MapManager;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools.TextColor;

public class Map extends InputMenu {

	private MapManager<Map> manager;
	private int initialtier = 1;
	private int initialzoom = 4;
	
	public Map(PlayerData PDI) {
		super(PDI);
		help = new HelpMenu(PDI, this).addPage(
				"Welcome to the Map. \n"
				+ "This map displays the different countries and towns near you,"
				+ " as well as the land that they have claimed. Each symbol represents a chunk of land."
				+ "A colored '/' symbol shows land claimed by a country, with the color corresponding"
				+ "to the key below the map. A colored '#' symbol shows land claimed by a town, and it will be"
				+ "the same color as the country it belongs to."
				);
	}

	@Override
	public String getHeader() {
		return "Map 1:" + (int) Math.pow(2, manager.zoom)+ " Viewing Tier " + manager.tier + " and " + (manager.tier+1) + " governments.";
	}

	@Override
	public Menu getPreviousMenu() {
		return new MainHud(PDI);
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
	public void actionResponse() {
	}

	@Override
	public Menu nextMenu() {
		//this.setError(MenuError.NOT_AN_OPTION());
		return this.getSelfPersist();
	}

	@Override
	public String validate(String input) {
		
		try{
			int answer = Integer.parseInt(input);
			this.manager.tier = answer;
			return MenuError.NO_ERROR();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return MenuError.INVALID_NUMBER_INPUT(this.getPlayerData());
		}
/*		for(String test:this.getTabOptions()) {
			if(test.equalsIgnoreCase(input)) {
				for(Class<? extends InspiredGov> govType:InspiredNations.regiondata.keySet()) {
					InspiredGov gov = GovFactory.getGovInstance(govType);
					if(input.equalsIgnoreCase(gov.getTypeName())) {
						this.selection = govType;
						return MenuError.NO_ERROR();
					}
				}
			}
		}

		return MenuError.NOT_AN_OPTION();*/
	}

	@Override
	public void useInput(String input) {
		this.Update();
	}
	
	@Override
	public String getInstructions() {
		return manager.drawMap(6);
	}

	@Override
	public void addTabOptions() {
		// Trying to use some kind of tab complete thing for selecting the scope of the map?
		for(Class<? extends InspiredGov> govType:InspiredNations.regiondata.keySet()) {
			InspiredGov gov = GovFactory.getGovInstance(govType);
			if(gov.getSubGovs().size() !=0) {
				tabOptions.add(gov.getTypeName());
			}
		}
		tabOptions.clear();  // this looks counter productive. Fill the list then clear it?
		
	}

	@Override
	public void addActionManagers() {
		manager = new MapManager<Map>(this, initialtier, initialzoom);
		this.getActionManager().add(manager);
		
	}
}
