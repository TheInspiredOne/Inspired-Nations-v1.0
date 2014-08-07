package com.github.InspiredOne.InspiredNations.ToolBox;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.World;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNationsClient.RemoteInterfaces.WorldClientInter;

/**
 * This is a class to encapsulate worlds so that I have an easy method of comparing worlds without
 * having to fuss about with strings and world names.
 * @author Jedidiah Phillips
 *
 */
public class WorldID implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -2025799081947713318L;
	private String worldname;
	private ServerID server;
	
	public WorldID(World world, ServerID server) {
		worldname = world.getName();
		this.server = server;
	}
	
	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(worldname).
            append(server).
            toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof WorldID))
            return false;

        WorldID rhs = (WorldID) obj;
        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(worldname, rhs.worldname).
            append(server, rhs.server).
            isEquals();
    }
    
    @Override
    public String toString() {
    	return this.worldname;
    }

    public WorldClientInter getWorld() throws RemoteException, NotBoundException {
		return this.server.getPortal().getWorld(this);
    }
    
    public ServerID getServer() {
    	return this.server;
    }
}
