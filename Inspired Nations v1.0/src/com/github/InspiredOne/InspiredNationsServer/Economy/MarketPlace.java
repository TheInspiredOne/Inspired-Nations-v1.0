package com.github.InspiredOne.InspiredNationsServer.Economy;

import java.util.List;

import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.Economy.Implem.Buyer;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.SortTool;

public interface MarketPlace<E extends Sellable> extends Nameable{
	
	/**
	 * Gets all the sellables in the server. This method is responsible for checking
	 * every location that a sellable can exist and adding it to the marketplace
	 * if the sellable is for sale.
	 * @return
	 */
	public abstract List<E> getSales(Buyer viewer);

	/**
	 * Gets the name of the marketplace to be used in Menus
	 * @return
	 */
	public abstract String getName();
	
	/**
	 * All the sorting tools that can be used with this Marketplace.
	 * @return
	 */
	public abstract List<SortTool<E>> getComparators(final Buyer viewer);
}
