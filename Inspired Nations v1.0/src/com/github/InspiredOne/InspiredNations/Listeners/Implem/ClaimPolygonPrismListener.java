package com.github.InspiredOne.InspiredNations.Listeners.Implem;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.Listeners.InspiredListener;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNations.ToolBox.Point3D;

public class ClaimPolygonPrismListener<T extends ClaimPolygonPrismManager<?>> extends InspiredListener<T> {

	Point3D position;
	
	public ClaimPolygonPrismListener(T manager) {
		super(manager);
		// TODO Auto-generated constructor stub
	}
	
	@EventHandler
	public void onPlayerClickBlock(PlayerInteractEvent event) {
		if(!this.getPlayerData().getPlayerID().equals(new PlayerID(event.getPlayer()))) {
			return;
		}
		if(event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			Location local = event.getClickedBlock().getLocation();
			Debug.print(local.getBlockX() + ", " + local.getBlockY() + ", " + local.getBlockZ());
			local.setY(local.getY());
			if(!event.isBlockInHand()) {
				event.setCancelled(true);
			}
			position = new Point3D(local);
			local.setX(local.getX() + .5);
			local.setZ(local.getZ() + .5);
			local.setY(local.getY() + 1);

			position.world.getWorld().spawn(local, Arrow.class);
			this.manager.Update();
		}

	}
	
}
