package com.github.InspiredOne.InspiredNationsServer.Economy;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.Economy.Implem.Buyer;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Point3D;


public interface Sellable extends Nameable {

	/**
	 * Handles the physical transfer of the item ownership. For items with multiple
	 * owners (i.e. governments or accounts) all other owners are removed and the 
	 * new owner is added making him/her the sole owner. For items, they are placed
	 * in the inventory of the buyer. If there is no space, then they are ejected
	 * on the ground around the buyer.
	 * @param player
	 */
	public void transferOwnership(Buyer buyer, Payable account);
	
	/**
	 * Returns true if the item is for sale
	 * @return
	 */
	public boolean isForSale();
	
	/**
	 * Returns the Location of the item.
	 * @return
	 */
	public Point3D getLocation();
	
	/**
	 * Gets the asked price for the item.
	 * @param curren
	 * @return
	 */
	public BigDecimal getPrice(Currency curren, Point3D locale);
	
}
