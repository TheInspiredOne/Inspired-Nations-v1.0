package com.github.InspiredOne.InspiredNationsServer.Regions.Implem;

import java.util.ArrayList;
import java.util.List;

import com.github.InspiredOne.InspiredNationsServer.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNationsServer.Regions.Cuboid;
import com.github.InspiredOne.InspiredNationsServer.Regions.InspiredRegion;
import com.github.InspiredOne.InspiredNationsServer.Regions.Region;

public class GoodBusinessLand extends InspiredRegion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7256618677030098681L;
	private static final String name = "Good Business";

	@Override
	public List<Class<? extends Region>> getAllowedForms() {
		List<Class<? extends Region>> output = new ArrayList<Class<? extends Region>>();
		output.add(Cuboid.class);
		output.add(PolygonPrism.class);
		return output;
	}

	@Override
	public List<Class<? extends InspiredRegion>> getEncapsulatingRegions() {
		List<Class<? extends InspiredRegion>> output = new ArrayList<Class<? extends InspiredRegion>>();
		output.add(TownLand.class);
		return output;
	}

	@Override
	public List<Class<? extends InspiredRegion>> getAllowedOverlap() {
		List<Class<? extends InspiredRegion>> output = new ArrayList<Class<? extends InspiredRegion>>();
		output.add(LocalParkLand.class);
		return output;
	}

	@Override
	public String getTypeName() {
		return name;
	}

	@Override
	public Class<? extends InspiredGov> getRelatedGov() {
		return GoodBusiness.class;
	}

}
