package com.github.InspiredOne.InspiredNationsServer.Regions.Implem;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Chunk;
import org.bukkit.Location;

import com.github.InspiredOne.InspiredNationsServer.PlayerData;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.PointsInDifferentWorldException;
import com.github.InspiredOne.InspiredNationsServer.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNationsServer.Regions.Cuboid;
import com.github.InspiredOne.InspiredNationsServer.Regions.CummulativeRegion;
import com.github.InspiredOne.InspiredNationsServer.Regions.NonCummulativeRegion;
import com.github.InspiredOne.InspiredNationsServer.Regions.Region;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Point2D;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Point3D;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.WorldID;


public class ChunkRegion extends NonCummulativeRegion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1407842833341186604L;
	private static final String typeName = "Chunk";
	private static final String description = "The 16 by 16 block column that spans from bedrock to sky.";
	
	Point2D coordinate;
	
	public ChunkRegion(Chunk chunk, ClientID client) {
		coordinate = new Point2D(chunk.getX(), chunk.getZ(), chunk.getWorld(), client);
	}
	public ChunkRegion(Point2D chunk) {
		coordinate = chunk;
	}

	@Override
	public Cuboid getBoundingCuboid() {
		Point3D one = new Point3D(coordinate.x*16, 0, coordinate.z*16, coordinate.world);
		Point3D two = new Point3D((coordinate.x+1)*16 - 1, 256, (coordinate.z+1)*16 - 1, coordinate.world);
		
		try {
			return new Cuboid(one, two);
		} catch (PointsInDifferentWorldException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	protected boolean isIn(NonCummulativeRegion region) {
		return this.getBoundingCuboid().IsIn(region);
	}
	
	@Override
	public boolean IsIn(Region region) {
		return this.getBoundingCuboid().IsIn(region);
	}
	
	@Override
	public boolean IsIn(CummulativeRegion<?> region) {
		if(region instanceof Chunkoid) {
			return region.getRegions().contains(this);
		}
		else {
			return IsIn((Region) region);
		}
	}
	
	@Override
	public boolean Intersects(Region region) {
		return this.getBoundingCuboid().Intersects(region);
	}
	
	@Override
	protected boolean intersects(NonCummulativeRegion region) {
		return this.getBoundingCuboid().Intersects(region);
	}
	
	@Override
	public boolean Intersects(CummulativeRegion<?> region) {
		if(region instanceof Chunkoid) {
			return region.getRegions().contains(this);	
		}
		else {
			return this.Intersects((Region) region);
		}
		
	}

	@Override
	public WorldID getWorld() {
		return coordinate.world;
	}

	@Override
	public int volume() {
		/*16 x 16 x 256*/
		return 65536;
	}

	@Override
	public boolean contains(Point3D location) {
		return this.equals(new ChunkRegion(new Point2D((int) Math.floor(location.x/16.),(int) Math.floor(location.z/16.), location.world)));
	}

	@Override
	public String getTypeName() {
		return typeName;
	}

	@Override
	public String getDescription() {
		return description ;
	}

	@Override
	public Menu getClaimMenu(PlayerData PDI, Menu previous, InspiredGov gov) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(coordinate).
            toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof ChunkRegion))
            return false;

        ChunkRegion rhs = (ChunkRegion) obj;
        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(coordinate, rhs.coordinate).
            isEquals();
    }
	@Override
	protected boolean instantiated() {
		return !(this.coordinate == null);
	}
	@Override
	public Point3D getCharacteristicPoint() {
		return this.getBoundingCuboid().getCharacteristicPoint();
	}
}
