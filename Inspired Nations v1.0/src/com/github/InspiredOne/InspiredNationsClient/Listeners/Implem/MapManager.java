package com.github.InspiredOne.InspiredNationsClient.Listeners.Implem;

import com.github.InspiredOne.InspiredNations.ToolBox.Tools;
import com.github.InspiredOne.InspiredNationsClient.Hud.ActionMenu;
import com.github.InspiredOne.InspiredNationsClient.Listeners.TabManager;

public class MapManager<T extends ActionMenu> extends TabManager<T> {

	public int zoom;
	public int tier;
	MapListener<MapManager<T>> maplis;
	public MapManager(T menu, int InitialTier, int InitialZoom) {
		super(menu);
		tier = InitialTier;
		zoom = InitialZoom;
		maplis = new MapListener<MapManager<T>>(this);
		listeners.add(maplis);
		listeners.add(new TabListener<MapManager<T>>(this));
	}
	
/*	public String drawMap(int tier, int size) {
		if(gov.getSuperGov().equals(GlobalGov.class)) {
			
			return drawMap(gov.getClass(), size);
		}
		else {
			return drawMap(gov.getSuperGov(), size);
		}
	}*/
	
	public String drawMap(int size) {
		return Tools.drawMap(this.getPlayerData(), (int) Math.pow(2, zoom), tier, size);
	}
	
	@Override
	public void Update() {
		if(this.preTabEntry.equalsIgnoreCase("+") && zoom > 0) {
			zoom--;
		}
		else if(this.preTabEntry.equalsIgnoreCase("-") && zoom < 8) {
			zoom++;
		}
		else {
			try {
				this.tier = Integer.parseInt(preTabEntry);
			}
			catch (Exception ex) {
				
			}
		}
		this.getActionMenu().Update();
		this.preTabEntry = "";
	}
	
	@Override
	public void textChange() {
		maplis.rotacount = maplis.getRotaCount(maplis.yaw);
	}
	
}
