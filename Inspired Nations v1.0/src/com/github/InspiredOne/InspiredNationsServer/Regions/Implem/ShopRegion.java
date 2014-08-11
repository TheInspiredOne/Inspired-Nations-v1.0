package com.github.InspiredOne.InspiredNationsServer.Regions.Implem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Exceptions.BlockNotChestException;
import com.github.InspiredOne.InspiredNations.Exceptions.PointsInDifferentWorldException;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Hud.Menu;
import com.github.InspiredOne.InspiredNations.Hud.Implem.ClaimAndUnclaimLand.ClaimChestShop;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.CummulativeRegion;
import com.github.InspiredOne.InspiredNations.Regions.NonCummulativeRegion;
import com.github.InspiredOne.InspiredNations.Regions.Region;
import com.github.InspiredOne.InspiredNations.ToolBox.Point3D;
import com.github.InspiredOne.InspiredNations.ToolBox.WorldID;

public class ShopRegion extends NonCummulativeRegion {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2195353839150455099L;

	public Point3D one;
	public Point3D two;
	List<Material> allowed = new ArrayList<Material>();
	
	public ShopRegion() {
		allowed.add(Material.CHEST);
		allowed.add(Material.TRAPPED_CHEST);
	}
	
	public void addChest(Block block) throws BlockNotChestException {
		one = null;
		two = null;
		Material type = block.getType();
		Point3D location = new Point3D(block.getLocation());
		if(allowed.contains(type)) {
			one = location;
			for(int x = -1; x <= 2; x = x+2) {
				Point3D test = new Point3D(location.x + x, location.y, location.z, location.world);
				if(test.getLocation().getBlock().getType().equals(type)) {
					two = test;
				}
			}
			for(int z = -1; z<=2; z = z+2) {
				Point3D test = new Point3D(location.x, location.y, location.z + z, location.world);
				if(test.getLocation().getBlock().getType().equals(type)) {
					two = test;
				}
			}
		}
		else {
			throw new BlockNotChestException();
		}
	}
	public void addChest(Point3D point) throws BlockNotChestException {
		this.addChest(point.getLocation().getBlock());
	}
	
	@Override
	public String getTypeName() {
		return "Shop";
	}

	@Override
	public String getDescription() {
		return "A facility to sell items from";
	}

	@Override
	public Menu getClaimMenu(PlayerData PDI, Menu previous, InspiredGov gov) {
		return new ClaimChestShop(PDI, previous, gov);
	}

	@Override
	public boolean IsIn(CummulativeRegion<?> region) {
		return this.IsIn((Region) region);
	}

	@Override
	public int volume() {
		int output = 0;
		if(one != null) {
			output++;
		}
		if(two != null) {
			output ++;
		}
		return output;
	}

	@Override
	public boolean contains(Point3D location) {
		if(one != null) {
			if(one.equals(location)) {
				return true;
			}
		}
		if(two != null) {
			if(two.equals(location)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean Intersects(CummulativeRegion<?> region) {
		return Intersects((Region) region);
	}

	@Override
	public boolean IsIn(Region region) {
		if(one == null) {
			if(two == null) {
				return true;
			}
			else {
				return region.contains(two);
			}
		}
		else {
			if(two ==null) {
				return region.contains(one);
			}
			else {
				return(region.contains(one) && region.contains(two));
			}
		}
	}

	@Override
	public boolean Intersects(Region region) {
		if(one != null) {
			if(region.contains(one)) return true;
		}
		if(two != null) {
			if(region.contains(two)) return true;
		}
		return false;
	}

	@Override
	protected boolean instantiated() {
		if(one != null || two != null) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Cuboid getBoundingCuboid() {
		if(this.volume() == 2) {
			try {
				return new Cuboid(one,two);
			} catch (PointsInDifferentWorldException e) {
				e.printStackTrace();
			}
		}
		else if(this.volume() == 1) {
			try {
				return new Cuboid(one,one);
			} catch (PointsInDifferentWorldException e) {
				e.printStackTrace();
			}
		}
		return new Cuboid();
	}

	@Override
	public WorldID getWorld() {
		if(this.instantiated()) {
			return this.one.world;
		}
		else return null;
	}

	@Override
	protected boolean isIn(NonCummulativeRegion region) {
		return this.IsIn((Region) region);
	}

	@Override
	protected boolean intersects(NonCummulativeRegion region) {
		return this.Intersects((Region) region);
	}

	@Override
	public Location getCharacteristicPoint() {
		return one.getLocation();
	}

}
