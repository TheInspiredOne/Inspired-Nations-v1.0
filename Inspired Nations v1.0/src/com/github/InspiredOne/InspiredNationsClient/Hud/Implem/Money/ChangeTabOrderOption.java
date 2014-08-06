package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.Money;

import java.util.ArrayList;

import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNations.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;

public class ChangeTabOrderOption<T extends Nameable> extends Option {

	ArrayList<T> options;
	T theOption;
	TabSelectOptionMenu<T> menu;
	
	public ChangeTabOrderOption(TabSelectOptionMenu<T> menu, String label,
			OptionUnavail reason, ArrayList<T> options, T theOption) {
		super(menu, label, reason);
		this.options = options;
		this.theOption = theOption;
		this.menu = menu;
	}

	public ChangeTabOrderOption(TabSelectOptionMenu<T> menu, String label, ArrayList<T> options, T theOption) {
		super(menu, label);
		this.options = options;
		this.theOption = theOption;
		this.menu = menu;
	}

	public ChangeTabOrderOption(TabSelectOptionMenu<T> menu, String label,
			String description, ArrayList<T> options, T theOption) {
		super(menu, label, description);
		this.options = options;
		this.theOption = theOption;
		this.menu = menu;
	}

	@Override
	public Menu response(String input) {
		int position = options.indexOf(theOption);
		int size = options.size();
		if(size == 0) {
			return menu;
		}
		else {
			if(input.equalsIgnoreCase("-")) {
				int newpos = newPosition(position + 1, size);
				options.remove(position);
				options.add(newpos,  theOption);
				menu.setTabcnt(newpos);
			}
			else if(input.equalsIgnoreCase("+"))  {
				int newpos = newPosition(position - 1, size);
				options.remove(position);
				options.add(newpos, theOption);
				menu.setTabcnt(newpos);
				
			}
		}

		
		return menu;
	}

	private int newPosition(int position, int size) {
		return (size + position) % size;
	}
}
