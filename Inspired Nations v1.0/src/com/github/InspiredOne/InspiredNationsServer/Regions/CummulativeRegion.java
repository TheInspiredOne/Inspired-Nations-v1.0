package com.github.InspiredOne.InspiredNationsServer.Regions;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Location;

import com.github.InspiredOne.InspiredNationsServer.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Point3D;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.WorldID;

public abstract class CummulativeRegion<T extends NonCummulativeRegion> extends Region {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8489617455582075095L;

	private HashSet<T> regions = new HashSet<T>();
	
	@Override
	public boolean contains(Point3D spot) {
		for(T test:regions) {
			if(test.contains(spot)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean Intersects(Region region) {
		for(T test:regions) {
			if(test.Intersects(region)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean Intersects(NonCummulativeRegion region) {
		for(T test:this.getRegions()) {
			if(test.Intersects(region)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean Intersects(CummulativeRegion<?> region) {
		for(T test:this.getRegions()) {
			if(test.Intersects(region)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean IsIn(Region region) {
		for(T test:this.getRegions()) {
			if(!test.IsIn(region)) {
				return false;
			}
		}
		return true;
	}
	@Override
	public boolean IsIn(NonCummulativeRegion region) {
		for(NonCummulativeRegion test:this.getRegions()) {
			if(!test.IsIn(region)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean IsIn(CummulativeRegion<?> region) {
		for(NonCummulativeRegion test:this.getRegions()) {
			if(!test.IsIn(region)) {
				return false;
			}
		}
		return true;
	}
	
	public HashSet<T> getRegions() {
		return regions;
	}
	
	public void setRegions(HashSet<T> regions) {
		this.regions = regions;
	}

	@Override
	public int volume() {
		int output = 0;
		for(T sum:regions) {
			output = output + sum.volume();
		}
		return output;
	}
	
	public abstract <E extends InspiredGov> Menu getUnclaimMenu(PlayerData PDI, Menu previous, E gov);

	@Override
	public Point3D getCharacteristicPoint() {
		double zsum = 0;
		double ysum = 0;
		double xsum = 0;
		HashMap<WorldID, Integer> counter = new HashMap<WorldID, Integer>();
		for(NonCummulativeRegion reg:this.regions) {
			zsum = zsum + reg.getCharacteristicPoint().z;
			ysum = ysum + reg.getCharacteristicPoint().y;
			xsum = xsum + reg.getCharacteristicPoint().x;
			if(counter.containsKey(reg.getWorld())) {
				counter.put(reg.getWorld(), counter.get(reg.getWorld()) + 1);
			}
			else {
				counter.put(reg.getWorld(), 1);
			}
		}
		double zavg = zsum/this.regions.size();
		double yavg = ysum/this.regions.size();
		double xavg = xsum/this.regions.size();
		WorldID highestFreq = null;
		int freq = 0;
		for(WorldID world:counter.keySet()) {
			if(counter.get(world) > freq) {
				highestFreq = world;
			}
		}
		return new Point3D((int) xavg,(int) yavg,(int) zavg, highestFreq);
	}

}
