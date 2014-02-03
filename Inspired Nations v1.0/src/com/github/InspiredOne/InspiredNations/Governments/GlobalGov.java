package com.github.InspiredOne.InspiredNations.Governments;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Governments.Implem.Country;
import com.github.InspiredOne.InspiredNations.Regions.InspiredRegion;
import com.github.InspiredOne.InspiredNations.ToolBox.IndexedSet;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;

public class GlobalGov extends OwnerSubjectGov {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4539890228965981190L;
	private static final String typeName = "Global Government"; 

	public GlobalGov() {
	}
	
	@Override
	public Class<? extends InspiredRegion> getSelfRegionType() {
		return null;
	}

	@Override
	public List<Class<? extends Facility>> getGovFacilities() {
		List<Class<? extends Facility>> output = new ArrayList<Class<? extends Facility>>();
		return output;
	}

	@Override
	public List<Class<? extends OwnerGov>> getSubGovs() {
		List<Class<? extends OwnerGov>> output = new ArrayList<Class<? extends OwnerGov>>();
		output.add(Country.class);
		return output;
	}

	@Override
	public IndexedSet<PlayerID> getSubjects() {
		
		IndexedSet<PlayerID> output = new IndexedSet<PlayerID>();
		
		for(OfflinePlayer player:InspiredNations.plugin.getServer().getOfflinePlayers()) {
			Debug.print("inside Global Gov For Loop");
			output.add(new PlayerID(player));
		}
		return output;
	}
	
	@Override
	public Class<? extends OwnerGov> getSuperGov() {
		return null;
	}

	@Override
	public String getTypeName() {
		return typeName;
	}

	@Override
	public Class<? extends InspiredGov> getCommonEcon() {
		return this.getClass();
	}

	@Override
	public List<Class<? extends InspiredGov>> getSelfGovs() {
		List<Class<? extends InspiredGov>> output = new ArrayList<Class<? extends InspiredGov>>();
		output.add(this.getClass());
		return output;
	}

	@Override
	public Class<? extends InspiredGov> getCommonGov() {
		return this.getClass();
	}

	@Override
	public Class<? extends InspiredRegion> getInspiredRegion() {
		return null;
	}

	@Override
	public String getFacilityGroupName() {
		return "";
	}

	@Override
	public String getSubjectPositionName() {
		return "Server Resident";
	}

	@Override
	public String getOwnerPositionName() {
		return "Server Owner";
	}

}
