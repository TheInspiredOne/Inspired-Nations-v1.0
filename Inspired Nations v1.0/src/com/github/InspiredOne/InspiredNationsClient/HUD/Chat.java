package com.github.InspiredOne.InspiredNationsClient.HUD;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.Listeners.TabManager;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataInter;

public class Chat extends InputMenu {

	Menu previous;
	int framebase;
	public Chat(PlayerDataInter PDI, Menu previous) throws RemoteException {
		super(PDI);
		this.previous = previous;
		framebase = PDI.getMsg().getMessages().size() -1;
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
	public void actionResponse() throws RemoteException {
		framebase = PDI.getMsg().getMessages().size() -1;
	}

	@Override
	public void useInput(String input) throws RemoteException {
		this.PDI.getMsg().sendChatMessage(input);
	}

	@Override
	public void addTabOptions() {

	}

	@Override
	public String getInstructions() throws RemoteException {
		this.PDI.getMsg().setMissedSize(0);;
		String output = "";
		for(int i=framebase-15;i <= framebase;i++) {
			if(PDI.getMsg().getMessages().size() >= i+1 && i >= 0) {
				output = output.concat(PDI.getMsg().getMessages().get(i).getDisplayName(PDI.getPlayerID()) + "\n");
			}
		}
		return output;
	}

	@Override
	public void addActionManagers() throws RemoteException {
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
