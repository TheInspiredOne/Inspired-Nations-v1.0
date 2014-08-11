package com.github.InspiredOne.InspiredNationsClient.HUD;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;


/**
 * Used to allow option menus to be bypassed if there is only one option.
 * @author Jedidiah E. Phillips
 *
 */
public abstract class PassByOptionMenu extends OptionMenu{

	public PassByOptionMenu(PlayerDataPortal PDI) throws RemoteException {
		super(PDI);
	}
	
	@Override
	public boolean getPassBy() throws RemoteException {
		this.Initialize();
		if (this.options.size() == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public Menu getPassTo() throws RemoteException {
		return this.getNextMenu("1");
	}
	
	// These methods are overridden by all the super classes. I wish there were a better
	// way I could do this. Until then, ctrl-c and ctrl-v.


}
