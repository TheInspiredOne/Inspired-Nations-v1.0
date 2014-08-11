package com.github.InspiredOne.InspiredNationsServer.Regions;

import org.bukkit.Location;

import com.github.InspiredOne.InspiredNationsServer.Debug;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Point3D;

/**
 * Region to be used when no land is actually claimed.
 * @author Jedidiah Phillips
 *
 */
public class nullRegion extends Region {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3247449813190347666L;

	@Override
	public boolean IsIn(Region region) {
		return false;
	}

	@Override
	public boolean IsIn(NonCummulativeRegion region) {
		return false;
	}

	@Override
	public boolean IsIn(CummulativeRegion<?> region) {
		return false;
	}

	@Override
	public int volume() {
		return 0;
	}

	@Override
	public boolean contains(Point3D location) {
		return false;
	}

	@Override
	public boolean Intersects(Region region) {
		return false;
	}

	@Override
	public boolean Intersects(NonCummulativeRegion region) {
		return false;
	}

	@Override
	public boolean Intersects(CummulativeRegion<?> region) {
		return false;
	}

	@Override
	public String getTypeName() {
		
		Debug.InformPluginDev();
		return null;
	}

	@Override
	public String getDescription() {
		Debug.InformPluginDev();
		return null;
	}

	@Override
	public Menu getClaimMenu(PlayerData PDI, Menu previous, InspiredGov gov) {
		Debug.InformPluginDev();
		return null;
	}

	@Override
	protected boolean instantiated() {
		return false;
	}

	@Override
	public Point3D getCharacteristicPoint() {
		return null;
	}

}
