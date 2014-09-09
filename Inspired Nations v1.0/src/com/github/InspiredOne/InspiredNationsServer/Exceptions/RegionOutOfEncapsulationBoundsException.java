package com.github.InspiredOne.InspiredNationsServer.Exceptions;

import com.github.InspiredOne.InspiredNationsServer.Governments.InspiredGov;


public class RegionOutOfEncapsulationBoundsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1092574775622912486L;
	public InspiredGov gov;
	
	public RegionOutOfEncapsulationBoundsException(InspiredGov gov) {
		this.gov = gov;
	}
}
