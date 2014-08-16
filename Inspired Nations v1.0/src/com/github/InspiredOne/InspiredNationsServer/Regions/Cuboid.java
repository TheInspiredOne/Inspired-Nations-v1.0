package com.github.InspiredOne.InspiredNationsServer.Regions;


import org.bukkit.Location;
import org.bukkit.Material;

import com.github.InspiredOne.InspiredNationsServer.Exceptions.PointsInDifferentWorldException;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Point3D;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.WorldID;


public class Cuboid extends NonCummulativeRegion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6074456272514021954L;
	
	private static final String typeName = "Cuboid";
	private static final String description = "A rectangular volume selected by two opposite corners.";
	private Point3D pointmin;
	private Point3D pointmax;
	
	public Cuboid() {
		
	}
	
	public Cuboid(Point3D one, Point3D two) throws PointsInDifferentWorldException {
		setPoints(one,two);
	}
	
	public void setPoints(Point3D pointone, Point3D pointtwo) throws PointsInDifferentWorldException {
		if(!pointone.world.equals(pointtwo.world)) {
			throw new PointsInDifferentWorldException();
		}
		int xmin = Math.min(pointone.x, pointtwo.x);
		int ymin = Math.min(pointone.y, pointtwo.y);
		int zmin = Math.min(pointone.z, pointtwo.z);
		int xmax = Math.max(pointone.x, pointtwo.x);
		int ymax = Math.max(pointone.y, pointtwo.y);
		int zmax = Math.max(pointone.z, pointtwo.z);
		
		pointmin = new Point3D(xmin, ymin, zmin, pointone.world);
		pointmax = new Point3D(xmax, ymax, zmax, pointone.world);
/*		if(pointtwo.x < pointmin.x) {
			pointmin.x = pointtwo.x;
			pointmax.x = pointone.x;
		}
		if(pointtwo.y < pointmin.y) {
			pointmin.y = pointtwo.y;
			pointmax.y = pointone.y;
		}
		if(pointtwo.z < pointmin.z) {
			pointmin.z = pointtwo.z;
			pointmax.z = pointone.z;
		}*/
	}
	
	public Point3D getPointMin() {
		return pointmin;
	}
	
	public Point3D getPointMax() {
		return pointmax;
	}
	
	@Override
	public String getTypeName() {
		return typeName;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Menu getClaimMenu(PlayerData PDI, Menu previous, InspiredGov gov) {
		return new ClaimCuboid(PDI, previous, gov);
	}

	@Override
	public Cuboid getBoundingCuboid() {
		return this;
	}

	@Override
	public int volume() {
		int length = this.pointmax.x - this.pointmin.x + 1;
		int height = this.pointmax.y - this.pointmin.y + 1;
		int width = this.pointmax.z - this.pointmin.z + 1;
		return length*height*width;
	}

	@Override
	public boolean contains(Point3D location) {
		int x = location.x;
		int y = location.y;
		int z = location.z;
		
		if (x >= this.pointmin.x && x <= this.pointmax.x && location.world.equals(this.getWorld())) {
			if (y >= this.pointmin.y && y <= this.pointmax.y) {
				if (z >= this.pointmin.z && z <= this.pointmax.z) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean Intersects(Region region) {
		Point3D point;
		for(int x = this.pointmin.x; x <= this.pointmax.x; x++) {
			for(int y = this.pointmin.y; y <= this.pointmax.y; y++) {
				for(int z = this.pointmin.z; z <= this.pointmax.z; z++) {
					point = new Point3D(x, y, z, this.pointmax.world);
					if(region.contains(point)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean Intersects(CummulativeRegion<?> region) {
		for(NonCummulativeRegion test:region.getRegions()) {
			if(this.Intersects(test)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected boolean intersects(NonCummulativeRegion region) {
		Cuboid other = region.getBoundingCuboid();
		if(this.getWorld().equals(region.getWorld())) {
			if(this.pointmax.y > other.pointmin.y && this.pointmin.y < other.pointmax.y) {
				if(this.pointmax.x > other.pointmin.x && this.pointmin.x < other.pointmax.x) {
					if(this.pointmax.z > other.pointmin.z && this.pointmin.z < other.pointmax.z) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean IsIn(Region region) {
		Point3D point;
		for(int x = this.pointmin.x; x <= this.pointmax.x; x++) {
			for(int y = this.pointmin.y; y <= this.pointmax.y; y++) {
				for(int z = this.pointmin.z; z <= this.pointmax.z; z++) {
					point = new Point3D(x, y, z, this.pointmax.world);
					if(!region.contains(point)) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean IsIn(CummulativeRegion<?> region) {
		return IsIn((Region) region);
	}
	
	@Override
	protected boolean isIn(NonCummulativeRegion region) {
		return IsIn((Region) region);
	}

	@Override
	public WorldID getWorld() {
		return this.pointmax.world;
		}

	@Override
	protected boolean instantiated() {
		return !(this.pointmax == null || this.pointmin == null);
	}
	
	@Override
	public String toString() {
		return this.pointmax + ", " + this.pointmin;
	}

	@Override
	public Point3D getCharacteristicPoint() {
		double xavg = (this.pointmax.x + this.pointmin.x)/2.0;
		double yavg = (this.pointmax.y + this.pointmin.y)/2.0;
		double zavg = (this.pointmax.z + this.pointmin.z)/2.0;
		return new Point3D((int) xavg,(int) yavg,(int) zavg, this.getWorld());
	}
}