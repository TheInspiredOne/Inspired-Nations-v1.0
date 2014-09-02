package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.rmi.RemoteException;
import java.util.ArrayList;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.Option;
import com.github.InspiredOne.InspiredNationsClient.HUD.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.Remotes.Reorderable;

public class ChangeTabOrderOption<T extends Nameable> extends Option {

	Reorderable options;
	T theOption;
	TabSelectOptionMenu<T> menu;
	
	public ChangeTabOrderOption(TabSelectOptionMenu<T> menu, String label,
			OptionUnavail reason, Reorderable options, T theOption) {
		super(menu, label, reason);
		this.options = options;
		this.theOption = theOption;
		this.menu = menu;
	}

	public ChangeTabOrderOption(TabSelectOptionMenu<T> menu, String label, Reorderable options, T theOption) {
		super(menu, label);
		this.options = options;
		this.theOption = theOption;
		this.menu = menu;
	}

	public ChangeTabOrderOption(TabSelectOptionMenu<T> menu, String label,
			String description, Reorderable options, T theOption) {
		super(menu, label, description);
		this.options = options;
		this.theOption = theOption;
		this.menu = menu;
	}

	@Override
	public Menu response(String input) throws RemoteException {
		if(menu.getTabOptions().size() == 0) {
			return menu;
		}
		if(input.equalsIgnoreCase("-")) {
			int newpos = options.moveDown(menu.getTabcnt());
			menu.setTabcnt(newpos);
		}
		else if(input.equalsIgnoreCase("+"))  {
			int newpos = options.moveUp(menu.getTabcnt());
			menu.setTabcnt(newpos);
			
		}
		return menu;
	}
}
