package com.github.InspiredOne.InspiredNationsClient.HUD.Implem;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.InspiredNationsClient;
import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.HUD.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Player.RelationList;
import com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Player.SettingsMenu;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.Datable;
import com.github.InspiredOne.InspiredNationsServer.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataInter;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Relation;



public class PlayerProfile extends OptionMenu {
	
	PlayerDataInter PDITarget;
	
	public <T extends Datable<PlayerID>> PlayerProfile(PlayerDataInter PDI, T PDITarget) throws RemoteException {
		super(PDI);
		
		this.PDITarget = InspiredNationsClient.server.getPlayer(PDITarget.getData());
	}
	public <T extends Datable<PlayerID>> PlayerProfile(PlayerDataInter PDI, PlayerDataInter PDITarget) throws RemoteException {
		super(PDI);
		
		this.PDITarget = PDITarget;
	}

	@Override
	public String getHeader() throws RemoteException {
		return "Profile: " + PDITarget.getName();
	}

	@Override
	public Menu getPreviousMenu() throws RemoteException {
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
	public String getPreOptionText() throws RemoteException {
		String output = PDI.LABEL() + "Citizenship: ";
		for(OwnerGov gov:this.PDITarget.getCitizenship()) {
			output = output.concat(gov.getDisplayName(PDI.getPlayerID()) + ", ");
		}
		if(output.length() > 0) {
			output = output.substring(0,output.length() - 2) + "\n";
		}
		
		return output;
	}

	@Override
	public void addOptions() throws RemoteException {
		// TODO Auto-generated method stub
		this.options.add(new PromptOption(this, "Ally List", new RelationList(PDI, PDITarget, Relation.ALLY)));
		this.options.add(new PromptOption(this, "Enemy List", new RelationList(PDI, PDITarget, Relation.ENEMY)));
		
		if (this.PDITarget.equals(PDI)) {
			this.options.add(new PromptOption(this, "Settings", new SettingsMenu(PDI)));
		}
		
	}

}
