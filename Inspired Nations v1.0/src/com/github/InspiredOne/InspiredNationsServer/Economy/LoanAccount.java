package com.github.InspiredOne.InspiredNationsServer.Economy;

public class LoanAccount extends Account {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1333770809713625342L;
	private static final String typeName = "Loans";
	private LenderAccount lender;
	
	public LoanAccount(LenderAccount lender) {
		super();
		this.lender = lender;
	}
	
	@Override
	public String getTypeName() {
		return typeName;
	}
	
	
}
