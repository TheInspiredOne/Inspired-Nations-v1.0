package com.github.InspiredOne.InspiredNationsClient.Listeners.Implem;

import com.github.InspiredOne.InspiredNations.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNations.Exceptions.InspiredGovTooStrongException;
import com.github.InspiredOne.InspiredNations.Exceptions.InsufficientRefundAccountBalanceException;
import com.github.InspiredOne.InspiredNations.Exceptions.RegionOutOfEncapsulationBoundsException;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.Point2D;
import com.github.InspiredOne.InspiredNationsClient.HUD.Implem.ClaimAndUnclaimLand.ClaimChunkoid;
import com.github.InspiredOne.InspiredNationsServer.Regions.Implem.ChunkRegion;
import com.github.InspiredOne.InspiredNationsServer.Regions.Implem.Chunkoid;

public class ClaimChunkoidManager<T extends ClaimChunkoid> extends ChunkoidManager<T> {

	private Point2D position;
	private boolean claiming = false;
	private ChunkRegion region;
	
	public ClaimChunkoidManager(T menu, Point2D initialChunk) {
		super(menu, initialChunk);
		this.position = initialChunk;
		region = new ChunkRegion(initialChunk);
	}
	
	public Point2D getPosition() {
		return position;
	}
	
	public void setClaiming(boolean claiming) {
		this.claiming = claiming;
		setPosition(position);
	}
	
	public void setPosition(Point2D position) {
		this.position = position;
		if(claiming) {
			this.getActionMenu().setError(MenuError.NO_ERROR());
			try {
				Chunkoid chunks = new Chunkoid();
				if(this.getActionMenu().gov.getRegion().getRegion() instanceof Chunkoid) {
					chunks = ((Chunkoid)this.getActionMenu().gov.getRegion().getRegion()).clone();
				}
				region = new ChunkRegion(position);
				chunks.addChunk(region);
				this.getActionMenu().gov.setLand(chunks);
				this.getActionMenu().region = (Chunkoid) this.getActionMenu().gov.getRegion().getRegion();
			} catch (BalanceOutOfBoundsException e) {
				this.getActionMenu().setError(MenuError.NOT_ENOUGH_MONEY(this.getPlayerData()));
			} catch (InspiredGovTooStrongException e) {
				this.getActionMenu().setError(MenuError.GOV_TOO_STRONG(e.gov, this.getPlayerData()));
			} catch (RegionOutOfEncapsulationBoundsException e) {
				this.getActionMenu().setError(MenuError.CLAIM_OUT_OF_BOUNDS(e.gov, this.getPlayerData()));
			} catch (InsufficientRefundAccountBalanceException e) {
				// TODO Figure out what to do here.
			};
		}
	}
}
