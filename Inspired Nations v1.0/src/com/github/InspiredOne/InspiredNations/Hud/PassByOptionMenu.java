package com.github.InspiredOne.InspiredNations.Hud;

import java.util.ArrayList;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Listeners.ActionManager;
import com.github.InspiredOne.InspiredNations.Listeners.Implem.MenuUpdateManager;

/**
 * Used to allow option menus to be bypassed if there is only one option.
 * @author Jedidiah E. Phillips
 *
 */
public abstract class PassByOptionMenu extends OptionMenu{

	public PassByOptionMenu(PlayerData PDI) {
		super(PDI);
	}
	
	@Override
	public boolean getPassBy() {
		this.Initialize();
		if (this.options.size() == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public Menu getPassTo() {
		return this.getNextMenu("1");
	}
	
	// These methods are overridden by all the super classes. I wish there were a better
	// way I could do this. Until then, ctrl-c and ctrl-v.

}
