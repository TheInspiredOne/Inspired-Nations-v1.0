package com.github.InspiredOne.InspiredNationsServer.Regions;

import java.io.Serializable;

import org.bukkit.Location;

import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Point3D;

public abstract class Region implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -330203131653502896L;
	
	public Region() {
		
	} 

	/**
	 * Returns true if the region is ready to be used. Use in other methods to
	 * make sure that null pointers are avoided.
	 * @return
	 */
	protected abstract boolean instantiated();
	/**
	 * Returns true if the entire region is within the input region
	 * @param region
	 * @return	
	 */
	public abstract boolean IsIn(Region region);
	/**
	 * Returns true if the entire region is within the input region
	 * @param region
	 * @return
	 */
	public abstract boolean IsIn(NonCummulativeRegion region);
	/**
	 * Returns true if the entire region is within the input region
	 * @param region
	 * @return	
	 * @throws IncorrectUnitOfTheCummulativeRegion 
	 */
	public abstract boolean IsIn(CummulativeRegion<?> region);

	/**
	 * Returns the volume in cubic meters
	 * @return
	 */
	public abstract int volume();
	/**
	 * Returns true if the location is within the region
	 * @param location	the location to test
	 * @return
	 */
	public boolean contains(Location location, ClientID client) {
		return this.contains(new Point3D(location, client));
	}
	/**
	 * Returns true if the location is within the region
	 * @param location	the location to test
	 * @return
	 */
	public abstract boolean contains(Point3D location);
	/**
	 * Returns true if this region overlaps the input region
	 * @param region
	 * @return
	 */
	public abstract boolean Intersects(Region region);
	/**
	 * Returns true if this region overlaps the input region
	 * @param region
	 * @return
	 */
	public abstract boolean Intersects(NonCummulativeRegion region);
	/**
	 * Returns true if this region overlaps the input region
	 * @param region
	 * @return
	 * @throws IncorrectUnitOfTheCummulativeRegion 
	 */
	public abstract boolean Intersects(CummulativeRegion<?> region);
	/**
	 * Returns the type name to be used in menus
	 * @return
	 */
	public abstract String getTypeName();
	/**
	 * Returns the description to be used in menus
	 * @return 
	 */
	public abstract String getDescription();
	/**
	 * Returns the first of the chain of menus used to claim it.
	 * @param PDI	The player that is claiming land.
	 * @param previous	the menu to go to after everything is done.
	 * @return
	 */
	public abstract Menu getClaimMenu(PlayerData PDI, Menu previous, InspiredGov gov);
	/**
	 * Returns a location that can be used to characterize the region.
	 * For instance, this point can be used by compassas, or be used
	 * to calculated the distances from a location.
	 * @return
	 */
	public abstract Point3D getCharacteristicPoint();

}
