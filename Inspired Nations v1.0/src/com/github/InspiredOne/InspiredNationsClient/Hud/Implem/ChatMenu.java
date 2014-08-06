package com.github.InspiredOne.InspiredNationsClient.Hud.Implem;


import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNations.ToolBox.Messaging.Alert;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;

public class ChatMenu extends TabSelectOptionMenu<Alert> {

	private Menu previous;
	
	public ChatMenu(PlayerData PDI, Menu previous) {
		super(PDI);
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
		this.PDI.getMsg().missedSize = 0;
		this.getTabOptions().addAll(PDI.getMsg().messages);
	}

	@Override
	public void addOptions() {
		
	}

	@Override
	public void addActionManagers() {
		
	}

	@Override
	public String getHeader() {
		return "Chat";
	}
	
	@Override
	public final String getPreOptionText() {
		String output = "";
		if(!tabOptionsToText(taboptions, tabcnt).isEmpty()) {
			output = output.concat(tabOptionsToText(taboptions, tabcnt));
		}
		if(!this.postTabListPreOptionsText().isEmpty()) {
			output = MenuTools.addDivider(output.concat("\n"),this.getPlayerData());
			output = output.concat(this.postTabListPreOptionsText() + "\n");
		}
		return output;
	}

}
