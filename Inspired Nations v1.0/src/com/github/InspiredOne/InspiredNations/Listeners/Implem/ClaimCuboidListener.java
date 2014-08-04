package com.github.InspiredOne.InspiredNations.Listeners.Implem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.InspiredOne.InspiredNations.Listeners.InspiredListener;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;

public class ClaimCuboidListener<T extends ClaimCuboidManager<?>> extends InspiredListener<T> {

	public ClaimCuboidListener(T manager) {
		super(manager);
	}
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event) {
		if(!this.getPlayerData().getPlayerID().equals(new PlayerID(event.getPlayer()))) {
			return;
		}
		if(event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			this.manager.setPoint1(event.getClickedBlock().getLocation());
			if(!event.isBlockInHand()) {
				event.setCancelled(true);
			}
		}
		else if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			this.manager.setPoint2(event.getClickedBlock().getLocation());
			if(!event.isBlockInHand()) {
				event.setCancelled(true);
			}
		}
		this.manager.Update();
	}

}
