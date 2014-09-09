package com.github.InspiredOne.InspiredNationsServer.Exceptions;

import com.github.InspiredOne.InspiredNationsServer.Governments.InspiredGov;


/**
 * Use when one nation tries to claim over the land of a nation that is too strong with
 * military and protection levels.
 * @author Jedidiah Phillips
 *
 */
public class InspiredGovTooStrongException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 470824707949125068L;
	public InspiredGov gov;
	
	public InspiredGovTooStrongException(InspiredGov gov) {
		this.gov = gov;
	}
}
