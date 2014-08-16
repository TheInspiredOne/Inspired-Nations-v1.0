package com.github.InspiredOne.InspiredNationsServer.Regions.Implem;

import java.util.ArrayList;
import java.util.List;

import com.github.InspiredOne.InspiredNationsServer.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNationsServer.Regions.InspiredRegion;
import com.github.InspiredOne.InspiredNationsServer.Regions.Region;


public class SignShopLand extends InspiredRegion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4597720539579321158L;
	private static final String name = "Sign Shop";
	
	public SignShopLand() {

	}

	@Override
	public Class<? extends InspiredGov> getRelatedGov() {

		return SignShop.class;
	}

	@Override
	public List<Class<? extends Region>> getAllowedForms() {
		List<Class<? extends Region>> output = new ArrayList<Class<? extends Region>>();
		output.add(SignShopRegion.class);
		return output;
	}

	@Override
	public List<Class<? extends InspiredRegion>> getEncapsulatingRegions() {
		List<Class<? extends InspiredRegion>> output = new ArrayList<Class<? extends InspiredRegion>>();
		output.add(GoodBusinessLand.class);
		return output;
	}

	@Override
	public List<Class<? extends InspiredRegion>> getAllowedOverlap() {
		List<Class<? extends InspiredRegion>> output = new ArrayList<Class<? extends InspiredRegion>>();
		return output;
	}

	@Override
	public String getTypeName() {
		return name;
	}

}
