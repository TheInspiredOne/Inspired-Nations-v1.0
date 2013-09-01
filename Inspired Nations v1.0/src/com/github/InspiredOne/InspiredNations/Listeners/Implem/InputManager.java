package com.github.InspiredOne.InspiredNations.Listeners.Implem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.InspiredOne.InspiredNations.Hud.ActionMenu;
import com.github.InspiredOne.InspiredNations.Listeners.ActionManager;
import com.github.InspiredOne.InspiredNations.Listeners.InspiredListener;

public class InputManager extends ActionManager {

	private Collection<String> tabOptions;
	List<InspiredListener> listeners = new ArrayList<InspiredListener>();
	
	public InputManager(ActionMenu menu, Collection<String> tabOptions) {
		super(menu);
		this.setTabOptions(tabOptions);
		this.listeners.add(new InputListener(this));
	}

	@Override
	public List<InspiredListener> getPlayerListener() {
		return listeners;
	}

	public Collection<String> getTabOptions() {
		return tabOptions;
	}

	public void setTabOptions(Collection<String> tabOptions) {
		this.tabOptions = tabOptions;
	}

}
