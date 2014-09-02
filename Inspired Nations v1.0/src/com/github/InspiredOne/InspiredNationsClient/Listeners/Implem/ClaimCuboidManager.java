package com.github.InspiredOne.InspiredNationsClient.Listeners.Implem;

import org.bukkit.Location;

import com.github.InspiredOne.InspiredNations.Exceptions.CuboidNotCompletedException;
import com.github.InspiredOne.InspiredNations.Exceptions.PointsInDifferentWorldException;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuAlert;
import com.github.InspiredOne.InspiredNations.ToolBox.Point3D;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools.TextColor;
import com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ClaimAndUnclaimLand.ClaimCuboid;
import com.github.InspiredOne.InspiredNationsClient.Listeners.ActionManager;

public class ClaimCuboidManager<T extends ClaimCuboid> extends ActionManager<T> {

	public Point3D point1;
	public Point3D point2;
	private Cuboid temp;
	public ClaimCuboidManager(T menu) {
		super(menu);
		this.listeners.add(new ClaimCuboidListener<ClaimCuboidManager<?>>(this));
	}
	
	public void setPoint1(Location local) {
		point1 = new Point3D(local);
		this.getActionMenu().setAlert(MenuAlert.MESSAGE_ALERT("Point 1 selected at " + TextColor.VALUE(this.getPlayerData()) + point1 + 
				TextColor.ALERT(this.getPlayerData()) + "."));
		if(point2 != null) {
			try {
				temp = new Cuboid(point1, point2);
			} catch (PointsInDifferentWorldException e) {
				this.reset();
				this.getActionMenu().setError(MenuError.POINTS_IN_DIFFERENT_WORLDS(this.getPlayerData()));
			}
		}
	}
	
	public void setPoint2(Location local) {
		point2 = new Point3D(local);
		this.getActionMenu().setAlert(MenuAlert.MESSAGE_ALERT("Point 2 selected at " + TextColor.VALUE(this.getPlayerData()) + point1 + 
				TextColor.ALERT(this.getPlayerData()) + "."));
		if(point1 != null) {
			try {
				temp = new Cuboid(point1, point2);
			} catch (PointsInDifferentWorldException e) {
				this.reset();
				this.getActionMenu().setError(MenuError.POINTS_IN_DIFFERENT_WORLDS(this.getPlayerData()));
			}
		}
	}
	
	public int getVolume() {
		if(temp == null) {
			return 0;
		}
		else {
			return temp.volume();
		}
	}
	
	public Cuboid getCuboid() throws CuboidNotCompletedException {
		if(temp == null) {
			throw new CuboidNotCompletedException();
		}
		else {

			return temp;
		}
	}
	
	@Override
	public void Update() {
		this.getActionMenu().setError(MenuError.NO_ERROR());
		this.getActionMenu().Update();
	}
	/**
	 * Used to reset this manager, putting all variables back to null
	 */
	public void reset() {
		point1 = null;
		point2 = null;
		temp = null;
	}

	
}
