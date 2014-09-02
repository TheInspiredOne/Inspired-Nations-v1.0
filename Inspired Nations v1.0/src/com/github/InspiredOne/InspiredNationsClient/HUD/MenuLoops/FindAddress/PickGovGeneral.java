package com.github.InspiredOne.InspiredNationsClient.HUD.MenuLoops.FindAddress;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.Datable;
import com.github.InspiredOne.InspiredNationsServer.Remotes.InspiredGovPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;

/**
 * Generalized find address menu system.
 * @author Jedidiah E. Phillips
 *
 */
public abstract class PickGovGeneral extends TabSelectOptionMenu<InspiredGovPortal> {

	public Menu previous;
	public Menu next;
	protected Datable<InspiredGovPortal> superGov;
	
	/**
	 * 
	 * @param PDI
	 * @param previous	Menu to go back to
	 * @param next		Final menu to end on
	 * @param govTargetType
	 * @param superGov
	 * @throws RemoteException 
	 */
	public PickGovGeneral(PlayerDataPortal PDI, Menu previous, Menu next, Datable<InspiredGovPortal> superGov) throws RemoteException {
		super(PDI);
		this.previous = previous;
		this.next = next;
		this.superGov = superGov;
	}
	public PickGovGeneral(PlayerDataPortal PDI, Menu previous, Menu next) {
		super(PDI);
		this.previous = previous;
		this.next = next;
		this.superGov = InspiredNations.global;
	}

	@Override
	public Menu getPreviousPrompt() {
		return previous;
		/*
		 * Not sure if I want it to work this way
		 * if(superGov != InspiredNations.global) {
			return new PickGovGeneral(PDI, previous, next, superGov);
		}
		else {
			return previous;
		}
		*/
	}

	/**
	 * if it returns true, then the gov it checked is added to the tab-options list.
	 * @param gov
	 * @return
	 */
	public abstract boolean check(InspiredGovPortal gov);

	@Override
	public void addTabOptions() {
		for(InspiredGovPortal govToTest: superGov.getData().getAllSubGovsAndFacilitiesJustBelow()) {
			if(check(govToTest)) {
				this.taboptions.add(govToTest);
			}
		}
		
	}

}
