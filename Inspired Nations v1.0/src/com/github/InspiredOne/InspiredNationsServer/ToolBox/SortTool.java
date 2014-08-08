package com.github.InspiredOne.InspiredNationsServer.ToolBox;

import java.rmi.RemoteException;
import java.util.Comparator;

import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;

public abstract class SortTool<T> implements Nameable {
	
	public abstract Comparator<T> getComparator();
	
	@Override
	public String getDisplayName(PlayerID viewer) throws RemoteException {
		return this.getName();
	}
	
	@Override
	public void setName(String name) {
		
	}
}
