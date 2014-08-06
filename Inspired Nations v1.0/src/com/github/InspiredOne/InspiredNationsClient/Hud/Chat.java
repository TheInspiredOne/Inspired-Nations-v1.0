package com.github.InspiredOne.InspiredNationsClient.Hud;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNationsClient.Listeners.TabManager;

public class Chat extends InputMenu {

	Menu previous;
	int framebase;
	public Chat(PlayerData PDI, Menu previous) {
		super(PDI);
		this.previous = previous;
		framebase = PDI.getMsg().messages.size() -1;
	}

	@Override
	public Menu nextMenu() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String validate(String input) {
		return MenuError.NO_ERROR();
	}
	
	@Override
	public void actionResponse() {
		framebase = PDI.getMsg().messages.size() -1;
	}

	@Override
	public void useInput(String input) {
		this.PDI.getMsg().sendChatMessage(input);
	}

	@Override
	public void addTabOptions() {

	}

	@Override
	public String getInstructions() {
		this.PDI.getMsg().missedSize = 0;
		String output = "";
		for(int i=framebase-15;i <= framebase;i++) {
			if(PDI.getMsg().messages.size() >= i+1 && i >= 0) {
				output = output.concat(PDI.getMsg().messages.get(i).getDisplayName(PDI) + "\n");
			}
		}
		return output;
	}

	@Override
	public void addActionManagers() {
		this.getActionManager().add(new TabManager<Chat>(this));
	}

	@Override
	public Menu getPreviousMenu() {
		// TODO Auto-generated method stub
		return previous;
	}

	@Override
	public boolean getPassBy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getHeader() {
		// TODO Auto-generated method stub
		return "Chat";
	}

}
