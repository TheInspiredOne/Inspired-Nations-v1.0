package com.github.InspiredOne.InspiredNations.Listeners.Implem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import com.github.InspiredOne.InspiredNations.Listeners.ActionManager;
import com.github.InspiredOne.InspiredNations.Listeners.InspiredListener;

public class MapListener extends InspiredListener {

	public MapListener(ActionManager manager) {
		super(manager);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if(this.getPlayerData().getPlayer() != event.getPlayer()) {
			return;
		}
		else {
			if(event.getTo().getBlock() != event.getFrom().getBlock()) {
				this.getManager().Update();
			}
			else if(event.getTo().getYaw()/45 != event.getFrom().getYaw()/45) {
				this.getManager().Update();
			}
		}
	}
}
