package com.github.InspiredOne.InspiredNationsServer.Exceptions;
/**
 * Used when trying to take more money out of an account than is available
 * @author Jedidiah Phillips
 *
 */
public class BalanceOutOfBoundsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3775607627964975657L;
	
	public BalanceOutOfBoundsException() {
		
	}

}
