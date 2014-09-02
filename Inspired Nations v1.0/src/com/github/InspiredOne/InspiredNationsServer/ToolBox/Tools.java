package com.github.InspiredOne.InspiredNationsServer.ToolBox;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import com.github.InspiredOne.InspiredNationsServer.Debug;
import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;
import com.github.InspiredOne.InspiredNationsServer.Governments.InspiredGov;

/**
 * Includes Text Style and color enums and a fully generalized ascii Map drawer.
 * @author Jedidiah Phillips
 *
 */
public class Tools {
	
	/**
	 *  A method to cut off decimals greater than the hundredth place;
	 * @param input
	 * @return
	 */
	public static BigDecimal cut(BigDecimal input) {
		return input.divide(BigDecimal.ONE, 2, BigDecimal.ROUND_DOWN);
	}
	
	/**
	 * Finds the new position to be used when changing the order of the options
	 * in a tab select menu.
	 * @param position original position
	 * @param size size of the array
	 * @return
	 */
	public static int newPosition(int position, int size) {
		return (size + position) % size;
	}
	
	public static List<InspiredGov> getGovsThatContain(Location loc) {
		List<InspiredGov> output = new ArrayList<InspiredGov>();
		
		for(InspiredGov gov:InspiredNationsServer.regiondata) {
			if(gov.contains(loc)) {
				output.add(gov);
				Debug.info(gov.getName());
			}
		}
		return output;
	}
	
	public static <T> T getInstance(Class<T> gov) {
		try {
			return gov.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

}
