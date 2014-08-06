package com.github.InspiredOne.InspiredNationsClient.Hud;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;

/**
 * A <code>PromptOption</code> provides the basic structure of an option that simply
 * leads to another prompt with no input args. It has a single prompt that it leads to.
 * @author Jedidiah E. Phillips
 *
 */
public class PromptOption extends Option {

	Menu nextPrompt;
	PlayerData PDI;
	
	public PromptOption(OptionMenu menu, String lable, Menu nextPrompt, OptionUnavail reason) {
		super(menu, lable, reason);
		this.nextPrompt = nextPrompt;
		this.PDI = this.menu.PDI;
	}
	public PromptOption(OptionMenu menu, String lable, Menu nextPrompt) {
		super(menu, lable);
		this.nextPrompt = nextPrompt;
		this.PDI = this.menu.PDI;
	}
	public PromptOption(OptionMenu menu, String lable, Menu nextPrompt, String description) {
		super(menu, lable, description);
		this.nextPrompt = nextPrompt;
		this.PDI = this.menu.PDI;
	}

	@Override
	public final Menu response(String input) {
		if(this.isAvailable()) {
			this.doStuff();
			return this.nextPrompt;
		}
		else {
			this.menu.setError(MenuError.NOT_AN_OPTION(menu.getPlayerData()));
			return menu.getSelfPersist();
		}
	}

	/**
	 * A function to allow options to do something if the option has been selected.
	 */
	public void doStuff() {
		
	}

}
