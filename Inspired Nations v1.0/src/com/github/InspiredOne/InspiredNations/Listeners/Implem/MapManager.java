package com.github.InspiredOne.InspiredNations.Listeners.Implem;

import com.github.InspiredOne.InspiredNations.Hud.ActionMenu;
import com.github.InspiredOne.InspiredNations.Listeners.ActionManager;

public class MapManager extends ActionManager {

	public MapManager(ActionMenu menu) {
		super(menu);
		listeners.add(new MapListener(this));
	}
}
