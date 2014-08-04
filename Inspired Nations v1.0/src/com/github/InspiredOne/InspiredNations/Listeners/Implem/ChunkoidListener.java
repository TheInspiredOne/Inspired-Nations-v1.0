package com.github.InspiredOne.InspiredNations.Listeners.Implem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import com.github.InspiredOne.InspiredNations.Listeners.InspiredListener;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNations.ToolBox.Point2D;

public class ChunkoidListener<T extends ChunkoidManager<?>> extends InspiredListener<T> {

	public ChunkoidListener(T manager) {
		super(manager);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if(!this.getPlayerData().getPlayerID().equals(new PlayerID(event.getPlayer()))) {
			return;
		}
		Point2D from = this.manager.getPosition();
		Point2D newFrom = new Point2D(event.getFrom().getChunk());
		if(!from.equals(newFrom)) {
			this.manager.setPosition(newFrom);
			this.manager.Update();
		}
	}
}
