package com.github.InspiredOne.InspiredNationsClient.HUD;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NameAlreadyTakenException;

public abstract class RenameNameableOption extends Option {

	protected Nameable nameholder;
	
	public RenameNameableOption(OptionMenu menu, Nameable nameholder, String label,
			OptionUnavail reason) {
		super(menu, label, reason);
		this.nameholder = nameholder;
	}

	public RenameNameableOption(OptionMenu menu, Nameable nameholder, String label) {
		super(menu, label);
		this.nameholder = nameholder;
	}

	public RenameNameableOption(OptionMenu menu, Nameable nameholder, String label,
			String description) {
		super(menu, label, description);
		this.nameholder = nameholder;
	}

	@Override
	public Menu response(String input) throws RemoteException {
		String error = validate(input);
		this.menu.setError(error);
		if(error.equals(MenuError.NO_ERROR())) {
			try {
				this.nameholder.setName(input);
			} catch (NameAlreadyTakenException e) {
				//TODO Possibly have to move this to the validate method
			}
		}
		return menu;
	}
	/**
	 * Looks at the input and checks to make sure it is correct.
	 * @param input
	 * @return
	 */
	public abstract String validate(String input);
}
