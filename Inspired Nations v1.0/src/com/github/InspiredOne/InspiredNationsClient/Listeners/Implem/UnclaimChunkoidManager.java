package com.github.InspiredOne.InspiredNationsClient.Listeners.Implem;

import com.github.InspiredOne.InspiredNationsServer.ToolBox.Point2D;


public class UnclaimChunkoidManager<T extends UnclaimChunkoid> extends ChunkoidManager<T> {

	public boolean unclaim = false;
	ChunkRegion region;
	
	public UnclaimChunkoidManager(T menu, Point2D initialChunk) {
		super(menu, initialChunk);
	}

	@Override
	public void setPosition(Point2D chunk) {
		this.position = chunk;
		if(unclaim) {
			this.getActionMenu().setError(MenuError.NO_ERROR());
			region = new ChunkRegion(position);
			this.getActionMenu().gov.removeLand(region, true);
		}
	}

	public void setUnclaim(boolean unclaim) {
		this.unclaim = unclaim;
		this.setPosition(this.getPosition());
	}
}
