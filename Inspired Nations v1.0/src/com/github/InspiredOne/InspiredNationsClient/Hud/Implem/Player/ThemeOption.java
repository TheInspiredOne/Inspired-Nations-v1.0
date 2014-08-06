package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.Player;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public abstract class ThemeOption extends Option {
	
	PlayerData PDI;
	
	public ThemeOption(OptionMenu menu, String label, OptionUnavail reason) {
		super(menu, label, reason);
		PDI = menu.getPlayerData();
		// TODO Auto-generated constructor stub
	}
	
	public ThemeOption(OptionMenu menu, String label) {
		super(menu, label);
		PDI = menu.getPlayerData();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Menu response(String input) {

		PDI.theme.setHEADER(HEADER());
		PDI.theme.setSUBHEADER(SUBHEADER());
		PDI.theme.setLABEL(LABEL());
		PDI.theme.setVALUE(VALUE());
		PDI.theme.setVALUEDESCRI(VALUEDESCRI());
		PDI.theme.setDIVIDER(DIVIDER());
		PDI.theme.setOPTION(OPTION());
		PDI.theme.setOPTIONNUMBER(OPTIONNUMBER());
		PDI.theme.setOPTIONDESCRI(OPTIONDESCRIP());
		PDI.theme.setUNAVAILABLE(UNAVAILABLE());
		PDI.theme.setUNAVAILREASON((UNAVAILREASON()));
		PDI.theme.setINSTRUCTION(INSTRUCTION());
		PDI.theme.setERROR(ERROR());
		PDI.theme.setUNIT(UNIT());
		PDI.theme.setALERT(ALERT());
		PDI.theme.setENDINSTRUCT(ENDINSTRU());
		return menu;
	}
	
	/**
	 * Header
	 */
	public abstract String HEADER();
	/**
	 * Subheader
	 */
	public abstract String SUBHEADER();
	/**
	 * Used to say what the proceeding value is.
	 */
	public abstract String LABEL();
	/**
	 * Anything that is calculated information displayed for the viewer 
	 */
	public abstract String VALUE();
	/**
	 * Additional text describing the value
	 */
	public abstract String VALUEDESCRI();
	/**
	 * Used for the divider between sections of the HUD
	 */
	public abstract String DIVIDER();

	/**
	 * The color of the options
	 */
	public abstract String OPTION();
	/**
	 * The color of the number correlating to the option
	 */
	public abstract String OPTIONNUMBER();
	/**
	 * Used for text that describes an option
	 */
	public abstract String OPTIONDESCRIP();
	/**
	 * Color to be used for an unavailable option
	 */
	public abstract String UNAVAILABLE();
	/**
	 * Color of text next to unavailable option describing why it's unavailable
	 */
	public abstract String UNAVAILREASON();
	/**
	 * Used to give instructions
	 */
	public abstract String INSTRUCTION();
	/**
	 * Used for error messages through out the plugin
	 */
	public abstract String ERROR();
	/**
	 * Used for alert messages through out the plugin
	 */
	public abstract String ALERT();
	/**
	 * Used for all units mentioned in the HUD
	 */
	public abstract String UNIT();
	/**
	 * Used for the line of text below the HUD
	 */
	public abstract String ENDINSTRU();
}
