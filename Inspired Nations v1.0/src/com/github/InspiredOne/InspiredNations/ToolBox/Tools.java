package com.github.InspiredOne.InspiredNations.ToolBox;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Exceptions.PlayerOfflineException;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
/**
 * Includes Text Style and color enums and a fully generalized ascii Map drawer.
 * @author Jedidiah Phillips
 *
 */
public class Tools {
	
	InspiredNations plugin;
	
	public Tools(InspiredNations instance) {
		this.plugin = instance;
	}
	/**
	 *  A method to cut off decimals greater than the hundredth place;
	 * @param input
	 * @return
	 */
	public static BigDecimal cut(BigDecimal input) {
		return input.divide(BigDecimal.ONE, 2, BigDecimal.ROUND_DOWN);
	}
	
	public static List<InspiredGov> getGovsThatContain(Location loc) {
		List<InspiredGov> output = new ArrayList<InspiredGov>();
		
		for(InspiredGov gov:InspiredNations.regiondata) {
			if(gov.contains(loc)) {
				output.add(gov);
				Debug.print(gov.getName());
			}
		}
		return output;
	}
	
	public static class TextColor {
		/**
		 * Used exclusively for the menu header.
		 */

		public static String HEADER(PlayerData PDI) {
			//ChatColor.BLUE + "" + ChatColor.BOLD
			return PDI.theme.HEADER();
		}
		/**
		 * Subheader
		 */


		public static String SUBHEADER(PlayerData PDI) {
			//ChatColor.YELLOW + "" + ChatColor.ITALIC + "" + ChatColor.BOLD
			return PDI.theme.SUBHEADER();
		}
		/**
		 * Used to say what the proceeding value is.
		 */

		public static String LABEL(PlayerData PDI) {
			//ChatColor.RED + ""
			return PDI.theme.LABEL();
		}
		/**
		 * Anything that is calculated information displayed for the viewer 
		 */
		public static String VALUE(PlayerData PDI) {
			//ChatColor.GOLD + ""
			return PDI.theme.VALUE();
		}
		/**
		 * Additional text describing the value
		 */
		public static String VALUEDESCRI(PlayerData PDI) {
			//ChatColor.RED + ""
			return PDI.theme.VALUEDESCRI();
		}
		/**
		 * Used for the divider between sections of the HUD
		 */
		public static String DIVIDER(PlayerData PDI){
			//ChatColor.DARK_AQUA + ""
			return PDI.theme.DIVIDER();
		}
		/**
		 * The color of the options
		 */
		public static String OPTION(PlayerData PDI) {
			//ChatColor.DARK_GREEN + ""
			return PDI.theme.OPTION();
		}
		/**
		 * The color of the number correlating to the option
		 */
		public static String OPTIONNUMBER(PlayerData PDI) {
			//ChatColor.YELLOW + ""
			return PDI.theme.OPTIONNUMBER();
		}
		/**
		 * Used for text that describes an option
		 */
		public static String OPTIONDESCRIP(PlayerData PDI) {
			//ChatColor.GRAY + ""
			return PDI.theme.OPTIONDESCRI();
		}
		/**
		 * Color to be used for an unavailable option
		 */
		public static String UNAVAILABLE(PlayerData PDI) {
			//ChatColor.DARK_GRAY + ""
			return PDI.theme.UNAVAILABLE();
		}
		/**
		 * Color of text next to unavailable option describing why it's unavailable
		 */
		public static String UNAVAILREASON(PlayerData PDI) {
			//ChatColor.GRAY + ""
			return PDI.theme.UNAVAILREASON();
		}
		/**
		 * Used to give instructions
		 */
		public static String INSTRUCTION(PlayerData PDI) {
			//ChatColor.YELLOW + ""
			return PDI.theme.INSTRUCTION();
		}
		/**
		 * Used for error messages through out the plugin
		 */
		public static String ERROR(PlayerData PDI) {
			//ChatColor.RED + ""
			return PDI.theme.ERROR();
		}
		/**
		 * Used for alert messages through out the plugin
		 */
		public static String ALERT(PlayerData PDI) {
			//ChatColor.YELLOW + ""
			return PDI.theme.ALERT();
		}
		/**
		 * Used for all units mentioned in the HUD
		 */
		public static String UNIT(PlayerData PDI) {
			//ChatColor.YELLOW + ""
			return PDI.theme.UNIT();
		}
		/**
		 * Used for the line of text below the HUD
		 */
		public static String ENDINSTRU(PlayerData PDI) {
			//ChatColor.AQUA + ""
			return PDI.theme.ENDINSTRUCT();
		}
		/**
		 * Used for Enemy Nations and players
		 */
		public static String ENEMY(PlayerData PDI) {
			//ChatColor.AQUA + ""
			return PDI.theme.ENEMY();
		}
		/**
		 * Used for Neutral Nations and players
		 */
		public static String NEUTRAL(PlayerData PDI) {
			//ChatColor.AQUA + ""
			return PDI.theme.NEUTRAL();
		}
		/**
		 * Used for Ally Nations and players
		 */
		public static String ALLY(PlayerData PDI) {
			//ChatColor.AQUA + ""
			return PDI.theme.ALLY();
		}
		
		private String color;
		
        private TextColor(String color) {
                this.color = color;
        }
        @Override
        public String toString() {
        	return color;
        }
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
