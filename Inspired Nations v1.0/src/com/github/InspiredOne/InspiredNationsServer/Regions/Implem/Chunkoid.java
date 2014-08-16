package com.github.InspiredOne.InspiredNationsServer.Regions.Implem;

import java.util.HashSet;

import org.bukkit.Chunk;

import com.github.InspiredOne.InspiredNationsServer.Regions.CummulativeRegion;
import com.github.InspiredOne.InspiredNationsServer.Regions.NonCummulativeRegion;
import com.github.InspiredOne.InspiredNationsServer.Regions.Region;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Point2D;


public class Chunkoid extends CummulativeRegion<ChunkRegion> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4821199291297874395L;
	private static final String typeName = "Chunkoid";
	private static final String description = "A region made up by chunks going from bedrock to world height.";

	public Chunkoid() {
		
	}
	public Chunkoid(Point2D point) {
		this.addChunk(point);
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
		return new ClaimChunkoid(PDI, previous, gov);
	}
	
	@Override
	public boolean IsIn(Region region) {
		if(region instanceof NonCummulativeRegion){
			return this.IsIn((NonCummulativeRegion) region);
		}
		else {
			return this.IsIn((CummulativeRegion<?>) region);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Chunkoid clone() {
		Chunkoid output = new Chunkoid();
		output.setRegions((HashSet<ChunkRegion>) this.getRegions().clone());
		return output;
	}
	public void addChunk(Chunk chunk, ClientID client) {
		Point2D position = new Point2D(chunk, client);
		addChunk(position);
	}
	
	public void addChunk(Point2D position) {
		ChunkRegion chunk = new ChunkRegion(position);
		addChunk(chunk);
	}
	
	public void addChunk(ChunkRegion region) {
		this.getRegions().add(region);	
	}
	
	public void removeChunk(Chunk chunk, ClientID client) {
		Point2D position = new Point2D(chunk, client);
		removeChunk(position);
	}
	public void removeChunk(Point2D position) {
		ChunkRegion chunk = new ChunkRegion(position);
		removeChunk(chunk);
	}
	public void removeChunk(ChunkRegion chunk) {
		this.getRegions().remove(chunk);
	}

	@Override
	protected boolean instantiated() {
		return true;
	}

	@Override
	public Menu getUnclaimMenu(PlayerData PDI, Menu previous, InspiredGov gov) {
		return new UnclaimChunkoid(PDI, previous, gov);
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

	@Override
	public boolean Intersects(CummulativeRegion<?> region) {
		for(NonCummulativeRegion test:this.getRegions()) {
			if(test.Intersects(region)) {
				return true;
			}
		}
		return false;
	}
}
