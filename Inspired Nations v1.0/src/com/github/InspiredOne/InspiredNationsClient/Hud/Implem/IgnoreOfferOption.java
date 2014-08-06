package com.github.InspiredOne.InspiredNationsClient.Hud.Implem;

import java.util.ArrayList;

import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNations.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class IgnoreOfferOption extends Option {

	Nameable item;
	ArrayList<?> list;
	public IgnoreOfferOption(OptionMenu menu, String label, OptionUnavail reason, Nameable item, ArrayList<?> list) {
		super(menu, label, reason);
		this.list = list;
		this.item = item;
	}

	public IgnoreOfferOption(OptionMenu menu, String label, Nameable item, ArrayList<?> list) {
		super(menu, label);
		this.list = list;
		this.item = item;
	}

	public IgnoreOfferOption(OptionMenu menu, String label, String description, Nameable item, ArrayList<?> list) {
		super(menu, label, description);
		this.list = list;
		this.item = item;
	}

	@Override
	public Menu response(String input) {
		
		this.list.remove(item);
		return menu;
	}

}
