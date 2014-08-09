package com.github.InspiredOne.InspiredNationsServer.Economy;

public class LenderAccount extends Account {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7290198320823902233L;
	private static final String typeName = "Lent";
	
	@Override
	public String getTypeName() {
		return typeName;
	}
}
