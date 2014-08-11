package com.github.InspiredOne.InspiredNationsServer.Regions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import com.github.InspiredOne.InspiredNationsServer.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNationsServer.Regions.Implem.ChunkRegion;
import com.github.InspiredOne.InspiredNationsServer.Regions.Implem.Chunkoid;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;
/**
 * This is the interface between the social construct of a government and the physical construct
 * of the world. 
 * @author Jedidiah Phillips
 *
 */
public abstract class InspiredRegion implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3476603574063579787L;
	
	private Region region = new nullRegion();
	
	public Region getRegion() {
		if(!region.instantiated()) {
			return new nullRegion();
		}
		else {
			return region;
		}
	}
	public void setRegion(Region region) {
		if(region == null) {
			this.region = new nullRegion();
		}
		this.region = region;
	}
	
	public abstract Class<? extends InspiredGov> getRelatedGov();
	/**
	 * Returns all the SelectionModes that this InspiredRegion is allowed to be made of
	 * @return
	 */
	public abstract List<Class<? extends Region>> getAllowedForms();
	
	/**
	 * Gets all the regions that this region must be completely within. Only considers the immediate
	 * regions that encapsulate it. Not the region that encapsulates the encapsulating region.
	 * @return
	 */
	public abstract List<Class<? extends InspiredRegion>> getEncapsulatingRegions();

	/**
	 * Returns all the InspiredRegions that this can overlap
	 * @return
	 */
	public abstract List<Class<? extends InspiredRegion>> getAllowedOverlap();
	/**
	 * Returns the type name to be used in menus
	 * @return
	 */
	public abstract String getTypeName();
	/**
	 * Gets all the overlap regions for this region that includes all the Encapsulating regions
	 * and the encapsulating regions overlap regions... and so on.
	 * @return
	 */
	public List<Class<? extends InspiredRegion>> getOverlap() {
		List<Class<? extends InspiredRegion>> output = new ArrayList<Class<? extends InspiredRegion>>();
		output.addAll(this.getAllowedOverlap());
		output.addAll(this.getEncapsulatingRegions());
		try {
			for(Class<? extends InspiredRegion> region:this.getEncapsulatingRegions()) {
				output.addAll(region.newInstance().getOverlap());
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return output;
	}
	@Override
	public boolean contains(Location location, ClientID id) {
		if (region == null) {
			return false;
		}
		else {
			return region.contains(location, id);
		}
	}
	/**
	 * The method used to update the land with this new land. For NonCummulativeRegions it will
	 * replace the whole thing, if CummulativeRegion then it will add it to the existing.
	 * @param select
	 */
	public void addLand(Region select) {
		if((this.region instanceof Chunkoid) && (select instanceof ChunkRegion)) {
			((Chunkoid) region).addChunk((ChunkRegion) select);
		}
		else {
			region = select;
		}
	}
}
