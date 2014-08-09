package com.github.InspiredOne.InspiredNationsServer.ToolBox;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;

public class Point2D extends Point3D {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2941615375716870644L;

	public Point2D(int x, int z, World world, ClientID client) {
		super(x, 0, z, world, client);
	}
	
	public Point2D(int x, int z, WorldID world) {
		super(x, 0, z, world);
	}
	
	public Point2D(Location location, ClientID client) {
		super(location, client);
		this.y = 0;
	}

	public Point2D(Chunk chunk, ClientID client) {
		this(chunk.getX(), chunk.getZ(), chunk.getWorld(), client);
	}

}
