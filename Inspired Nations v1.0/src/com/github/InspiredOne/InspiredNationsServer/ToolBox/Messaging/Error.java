package com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging;


public abstract class Error extends Alert {

	/**
	 * 
	 */
	private static final long serialVersionUID = -872782351117265641L;

	public Error() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean menuPersistent() {
		return false;
	}

}
