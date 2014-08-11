package com.github.InspiredOne.InspiredNationsServer.Regions.Implem;

import java.util.ArrayList;
import java.util.List;

import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Governments.Implem.ChestShop;
import com.github.InspiredOne.InspiredNations.Regions.InspiredRegion;
import com.github.InspiredOne.InspiredNations.Regions.Region;

public class ShopLand extends InspiredRegion {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5705714393247235087L;

	public ShopLand() {

	}

	@Override
	public Class<? extends InspiredGov> getRelatedGov() {
		return ChestShop.class;
	}

	@Override
	public List<Class<? extends Region>> getAllowedForms() {
		List<Class<? extends Region>> output = new ArrayList<Class<? extends Region>>();
		output.add(ShopRegion.class);
		return output;
	}

	@Override
	public List<Class<? extends InspiredRegion>> getEncapsulatingRegions() {
		List<Class<? extends InspiredRegion>>output = new ArrayList<Class<? extends InspiredRegion>>();
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
		return "Shop";
	}

}
