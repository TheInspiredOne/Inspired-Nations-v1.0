package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Player;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.Option;
import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.HUD.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.HUD.Implem.PlayerProfile;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Relation;

public class RelationList extends TabSelectOptionMenu<PlayerDataPortal> {
	
	Relation status;
	PlayerDataPortal target;

	public RelationList(PlayerDataPortal PDI, PlayerDataPortal target, Relation r) throws RemoteException {
		super(PDI);
		status = r;
		this.target = target;
		
	}

	@Override
	public Menu getPreviousPrompt() throws RemoteException {
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
	public String getHeader() throws RemoteException {
		// TODO Auto-generated method stub
		return "List of " + target.getName() + "'s " + status.toString() ;
	}
	
	
	public class CustomAllyOption extends Option {
		
		PlayerDataPortal targetPDI;
		
		public CustomAllyOption(OptionMenu menu, String label, PlayerDataPortal playerData) {
			super(menu, label);
			targetPDI = playerData;
		}

		@Override
		public Menu response(String input) throws RemoteException {
			return new PlayerProfile(PDI, targetPDI);
		}
		
	}

}
