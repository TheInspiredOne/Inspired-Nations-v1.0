package com.github.InspiredOne.InspiredNationsClient.Hud;

import com.github.InspiredOne.InspiredNations.Exceptions.NameAlreadyTakenException;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNations.ToolBox.Nameable;

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
	public Menu response(String input) {
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
