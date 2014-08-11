package com.github.InspiredOne.InspiredNationsServer.Regions;

import com.github.InspiredOne.InspiredNationsServer.ToolBox.WorldID;



public abstract class NonCummulativeRegion extends Region {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3055862542770765137L;

	/**
	 * Gets the bounding cuboid for the purposes of making selection
	 * verification speedy. 
	 * @return
	 */
	public abstract Cuboid getBoundingCuboid();
	
	/**
	 * Gets the world that this region resides in. Because it is non-cummulative
	 * the region exists entirely in one world.
	 * @return
	 */
	public abstract WorldID getWorld();

	
	protected abstract boolean isIn(NonCummulativeRegion region);

	/**
	 * Returns true if the entire region is within the input region
	 * @param region
	 * @return	
	 */
	@Override
	public final boolean IsIn(NonCummulativeRegion region) {
		Cuboid cube = this.getBoundingCuboid();
		if(region.contains(cube.getPointMax()) && region.contains(cube.getPointMin())) {
			return isIn(region);
		}
		else {
			return false;
		}
	}

	protected abstract boolean intersects(NonCummulativeRegion region);

	@Override
	public final boolean Intersects(NonCummulativeRegion region) {
		
		Cuboid current = this.getBoundingCuboid();
		if(current.intersects(region)) {
			return this.intersects(region);
		}
		else {
			return false;
		}
	}
}
